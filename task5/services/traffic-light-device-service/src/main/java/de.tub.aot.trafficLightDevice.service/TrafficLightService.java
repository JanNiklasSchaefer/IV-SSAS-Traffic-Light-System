package de.tub.aot.trafficLightDevice.service;

import de.tub.aot.common.models.TrafficStatus;

import java.util.ArrayList;

import de.tub.aot.common.models.GPSCoordinate;
import de.tub.aot.common.models.TrafficLightId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Consumes;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import java.util.UUID;
import java.nio.charset.StandardCharsets;
import jakarta.annotation.PostConstruct;

@Path("/api/device")
@ApplicationScoped
@DenyAll
public class TrafficLightService {

    ArrayList<TrafficLightId> trafficLights = new ArrayList<TrafficLightId>();


    @PostConstruct
    void init(){
    // Create 4 Traffic Lights with UUID 
    /* Set Coordinates of Traffic Light so 
    North is at: Lattitude: (east - west) 37.30 / longitude(north-south)121.45
    South is at: Lattitude: (east - west) 37.30 / longitude(north-south)121.15
    
    West is at: Lattitude: (east - west)37.15 / longitude(north-south) 121.30
    East is at: Lattitude: (east - west)37.45 / longitude(north-south) 121.30
    
    So East - west Traffic light is in middle of longitude wise of South - North 
    And North-south is in middle lattitude wise of east-west
    */ 
    GPSCoordinate lattitudeNorth = new GPSCoordinate(37, 30, 0.0, true);
    GPSCoordinate longitudeNorth = new GPSCoordinate(-121, 15, 0.0, false);
    String key = "north"; 
    UUID id = UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8));
    TrafficLightId trafficLightNorth = new TrafficLightId(lattitudeNorth, longitudeNorth, id, "north-south");

    GPSCoordinate lattitudeSouth = new GPSCoordinate(37, 30, 0.0, true);
    GPSCoordinate longitudeSouth = new GPSCoordinate(-121, 45, 0.0, false);
    key = "south"; 
    id = UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8));
    TrafficLightId trafficLightSouth = new TrafficLightId(lattitudeSouth, longitudeSouth, id, "south-north");

    GPSCoordinate lattitudeWest = new GPSCoordinate(37, 15, 0.0, true);
    GPSCoordinate longitudeWest = new GPSCoordinate(-121, 30, 0.0, false);
    key = "west"; 
    id = UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8));
    TrafficLightId trafficLightWest = new TrafficLightId(lattitudeWest, longitudeWest,id,"west-east");

    GPSCoordinate lattitudeEast = new GPSCoordinate(37, 45, 0.0, true);
    GPSCoordinate longitudeEast = new GPSCoordinate(-121, 30, 0.0, false);
    key = "east"; 
    id = UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8));
    TrafficLightId trafficLightEast = new TrafficLightId(lattitudeEast, longitudeEast,id, "east-west");

    trafficLights.add(trafficLightNorth);
    trafficLights.add(trafficLightSouth);
    trafficLights.add(trafficLightWest);
    trafficLights.add(trafficLightEast);
    }



    @GET
    @Path("/traffic-state")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"tcc-state-controller-light-service","tcc-status-service-light-service"})
    public TrafficStatus getTrafficState(@QueryParam("vehicle") Boolean vehicle) {
        TrafficStatus status = new TrafficStatus();
        if(vehicle){
            status.setState("green");
            status.setTimestamp("2024-01-01T12:00:00Z");
        }
        else{
            status.setState("red");
            status.setTimestamp("2024-01-01T12:00:00Z");
        }
        return status;
    }

    @POST
    @Path("/change-state")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "tcc-state-controller-light-service", "tcc-status-service-light-service" })
    public Response changeState(@QueryParam("state") String currentState) {

        TrafficStatus newStatus = new TrafficStatus();
        if ("green".equals(currentState)) {
            newStatus.setState("green");
        } else
            newStatus.setState("red");

        newStatus.setTimestamp("2024-01-02T12:00:00Z");
        return Response.ok(newStatus).build();
    }

    @GET
    @Path("/traffic-lights")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"tcc-state-controller-light-service","tcc-status-service-light-service"})
    public ArrayList<TrafficLightId> trafficLights() {
        return trafficLights;
    }
}
