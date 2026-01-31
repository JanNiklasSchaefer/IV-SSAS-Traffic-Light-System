package de.tub.aot.trafficLightDevice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.time.Instant;

import de.tub.aot.common.models.GPSCoordinate;
import de.tub.aot.common.models.TrafficLightId;
import de.tub.aot.common.models.TrafficStatus;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Consumes;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import java.util.UUID;

import org.eclipse.microprofile.openapi.models.parameters.Parameter.In;

import java.nio.charset.StandardCharsets;

import jakarta.annotation.PostConstruct;

import org.jboss.logging.Logger;


@Path("/api/device")
@ApplicationScoped
@DenyAll
public class TrafficLightService {
    //Activate Loggin
    private static final Logger LOG = Logger.getLogger(TrafficLightService.class);


    ArrayList<TrafficLightId> trafficLightIdArray = new ArrayList<TrafficLightId>();
    ArrayList<TrafficStatus> TrafficStatus = new ArrayList<TrafficStatus>();

    TrafficLightId northTrafficLight;
    TrafficLightId southTrafficLight;
    TrafficLightId eastTrafficLight;
    TrafficLightId westTrafficLight;

    UUID northId;
    UUID southId;
    UUID westId;
    UUID eastId;

    TrafficStatus northStatus;
    TrafficStatus southStatus;
    TrafficStatus westStatus;
    TrafficStatus eastStatus;

    Map<UUID,TrafficStatus> trafficLightMap = new HashMap<UUID,TrafficStatus>();

    @PostConstruct
    void init(){
    // Create 4 Traffic Lights with UUID 
    /* Set Coordinates of Traffic Light so 
    North is at: Lattitude: (east - west) 37.30.30 / longitude(north-south)-121.30.31
    South is at: Lattitude: (east - west) 37.30.30 / longitude(north-south)-121.30.29
    
    West is at: Lattitude: (east - west)37.30.29 / longitude(north-south) -121.30.30
    East is at: Lattitude: (east - west)37.30.31 / longitude(north-south) -121.30.30
    
    So East - west Traffic light is in middle of longitude wise of South - North 
    And North-south is in middle lattitude wise of east-west
    */ 
    GPSCoordinate lattitudeNorth = new GPSCoordinate(37, 30, 30.0, true);
    GPSCoordinate longitudeNorth = new GPSCoordinate(-121, 30, 29.0, false);
    String key = "north"; 
    this.northId = UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8));
    this.northTrafficLight = new TrafficLightId(lattitudeNorth, longitudeNorth, this.northId, "north-south");

    GPSCoordinate lattitudeSouth = new GPSCoordinate(37, 30, 30.0, true);
    GPSCoordinate longitudeSouth = new GPSCoordinate(-121, 30, 31.0, false);
    key = "south"; 
    this.southId = UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8));
    this.southTrafficLight = new TrafficLightId(lattitudeSouth, longitudeSouth, this.southId, "south-north");

    GPSCoordinate lattitudeWest = new GPSCoordinate(37, 30, 29.0, true);
    GPSCoordinate longitudeWest = new GPSCoordinate(-121, 30, 30.0, false);
    key = "west"; 
    this.westId = UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8));
    this.westTrafficLight = new TrafficLightId(lattitudeWest, longitudeWest, this.westId,"west-east");

    GPSCoordinate lattitudeEast = new GPSCoordinate(37, 30, 31.0, true);
    GPSCoordinate longitudeEast = new GPSCoordinate(-121, 30, 30.0, false);
    key = "east"; 
    this.eastId = UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8));
    this.eastTrafficLight = new TrafficLightId(lattitudeEast, longitudeEast, this.eastId, "east-west");

    this.trafficLightIdArray.add(northTrafficLight);
    this.trafficLightIdArray.add(southTrafficLight);
    this.trafficLightIdArray.add(westTrafficLight);
    this.trafficLightIdArray.add(eastTrafficLight);
    
    Instant initializingtTimer = Instant.now();

    //Create array for ease of use in the api endpoint. More storage space but makes calls faster.

    this.northStatus = new TrafficStatus(northTrafficLight, initializingtTimer,"red");
    this.southStatus = new TrafficStatus(southTrafficLight, initializingtTimer,"red");
    this.westStatus = new TrafficStatus(westTrafficLight, initializingtTimer, "green");
    this.eastStatus = new TrafficStatus(eastTrafficLight, initializingtTimer, "green");

    // Create map for ease of use while implementing. Array would probably be faster in that size due to big O(1) but ease of implementation is prefered.

    this.trafficLightMap.put(this.northTrafficLight.getUuid(),this.northStatus);
    this.trafficLightMap.put(this.southTrafficLight.getUuid(),this.southStatus);
    this.trafficLightMap.put(this.eastTrafficLight.getUuid(),this.eastStatus);
    this.trafficLightMap.put(this.westTrafficLight.getUuid(),this.westStatus);
    }
    // TODO. Add Audit Functionality

    @GET
    @Path("/traffic-state")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"tcc-state-controller-light-service","tcc-status-service-light-service"})
    public TrafficStatus getTrafficState(@QueryParam("traffic-light-id") UUID trafficLightId){
        TrafficStatus status = this.trafficLightMap.get(trafficLightId);
        return status;
    }

    @POST
    @Path("/change-state")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "tcc-state-controller-light-service"})
    public Response changeState() {

        Map<UUID,String> previousStatesMap = new HashMap<UUID,String>();
        // First set intersection to yellow for a few seconds and wait for traffic to clear 
        for(TrafficStatus status : this.trafficLightMap.values()){
            // Check for illegal states before starting a swap. 
            if(!status.getState().equals("green") && !status.getState().equals("red")){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
               .entity("Illegal Traffic Light State or State Change Request requested while changing already.")
               .type("text/plain")
               .build();
            }
            Instant timer = Instant.now();
            previousStatesMap.put(status.getTrafficLightId().getUuid(), status.getState());
            status.setTimestamp(timer);
            status.setState("yellow");
        }

        // Wait for traffic intersection to clear
        try {
        Thread.sleep(3000); // 3 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore interrupt flag
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();        
            }
        
        //Now go over all states again and swap to opposite of previous state
        for(TrafficStatus status : this.trafficLightMap.values()){
            UUID id = status.getTrafficLightId().getUuid();
            String state = previousStatesMap.get(id);
            if(state.equals("green")){
                status.setState("red");
            }
            if(state.equals("red")){
                status.setState("green");
            }
            //Set Timestamp
            Instant timer = Instant.now();
            status.setTimestamp(timer);
        }
        

        return Response.ok().build();
    }

    @PUT
    @Path("/management/change-state")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "tcc-state-controller-light-service"})
    public Response changeManagementCenterState(@QueryParam("traffic-light-id") UUID id, String state) {

        if(!state.equals("red") && !state.equals("green")){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
               .entity("Illegal Traffic Light Goal State. Management Center can only change to `green` or `red`. State was : " + state)
               .type("text/plain")
               .build();
        }

        LOG.info("Iterating over Traffic Status Field and setting to yellow");
        // First set intersection to yellow for a few seconds and wait for traffic to clear 
        for(TrafficStatus status : this.trafficLightMap.values()){
            // Check for illegal states before starting a swap. 
            Instant timer = Instant.now();
            status.setTimestamp(timer);
            status.setState("yellow");
        }

        LOG.info("Sleeping for 3 Seconds");
        // Wait for traffic intersection to clear
        try {
        Thread.sleep(3000); // 3 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore interrupt flag
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();        
            }
        
        //Depending on Location of Device. We decide how to change traffic state
        
        //If we want to swap north or south traffic light do this tree
        if(id.equals(this.northId) || id.equals(this.southId)){
            LOG.info("Swapping north + south to goal state");
            //Set North + south to goal status 
            TrafficStatus status = this.trafficLightMap.get(this.northId);
            status.setState(state);
            status.setTimestamp(Instant.now());

            status = this.trafficLightMap.get(this.southId);
            status.setState(state);
            status.setTimestamp(Instant.now());

            if(state.equals("red")){
                status = this.trafficLightMap.get(this.eastId);
                status.setState("green");
                status.setTimestamp(Instant.now());

                status = this.trafficLightMap.get(this.westId);
                status.setState("green");
                status.setTimestamp(Instant.now());
            }
            if(state.equals("green")){
                status = this.trafficLightMap.get(this.eastId);
                status.setState("red");
                status.setTimestamp(Instant.now());

                status = this.trafficLightMap.get(this.westId);
                status.setState("red");
                status.setTimestamp(Instant.now());
            }
        }
        //If we want to swap east or west traffic light do this tree
        if(id.equals(this.eastId) || id.equals(this.westId)){
            //Set WEst + east to goal status 
            LOG.info("Swapping east + west to goal state");
            TrafficStatus status = this.trafficLightMap.get(this.eastId);
            status.setState(state);
            status.setTimestamp(Instant.now());

            status = this.trafficLightMap.get(this.westId);
            status.setState(state);
            status.setTimestamp(Instant.now());

            if(state.equals("red")){
                status = this.trafficLightMap.get(this.northId);
                status.setState("green");
                status.setTimestamp(Instant.now());

                status = this.trafficLightMap.get(this.southId);
                status.setState("green");
                status.setTimestamp(Instant.now());
            }
            if(state.equals("green")){
                status = this.trafficLightMap.get(this.northId);
                status.setState("red");
                status.setTimestamp(Instant.now());

                status = this.trafficLightMap.get(this.southId);
                status.setState("red");
                status.setTimestamp(Instant.now());
            }
        }
        
        return Response.ok().build();
    }

    @GET
    @Path("/traffic-lights")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"tcc-status-service-light-service","tcc-state-controller-light-service"})
    public ArrayList<TrafficLightId> trafficLights() {
        return trafficLightIdArray;
    }
}
