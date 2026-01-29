package de.tub.aot.client;

import de.tub.aot.client.api.PriorityRequest;
import de.tub.aot.client.api.PriorityResponse;
import de.tub.aot.client.api.RequestStatus;
import de.tub.aot.client.api.TccApiClient;
import de.tub.aot.common.models.TrafficStatus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.Scanner;
import java.util.UUID;
import java.util.ArrayList;

import de.tub.aot.common.models.TrafficStatus;
import de.tub.aot.common.models.TrafficLightId;

/**
 * Simple demo client for the Mayor Vehicle.
 *
 * According to your REST API spec, clients only call the EXTERNAL endpoints:
 * - GET /api/status/traffic
 * - POST /api/priority/requests
 * - GET /api/priority/requests/{requestId}
 *
 * This client uses Quarkus REST Client for type-safe API calls.
 * Default base URL is http://localhost:8080 (for Quarkus dev mode),
 * but you can override it with:
 * -Dbase.url=http://some-host:some-port
 */
@QuarkusMain
public class MayorVehicleClient implements QuarkusApplication {

    // Dummy requestId for GET /api/priority/requests/{requestId}
    private static final String DUMMY_REQUEST_ID = "123e4567-e89b-12d3-a456-426614174000";

    private static final UUID DUMMY_UUID = UUID.fromString("8d8d1437-907b-3a79-900a-c5f0ea1f5c73");


    @Inject
    @RestClient
    TccApiClient apiClient;

    @Override
    public int run(String... args) {
        System.out.println("=== Pedestrian Client ===");
        runInteractive();
        return 0;
    }

    private void runInteractive() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                printMenu();
                System.out.print("> ");
                String line = scanner.nextLine().trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\s+");
                String command = parts[0];

                try {
                    switch (command) {
                        case "1" -> callTrafficLightsEndpoint();
                        case "2" -> {
                            if (parts.length != 2) {
                                System.out.println(
                                        "ERROR: Missing Vehicle Boolean Input or too many Input Parameters. Correct Pattern: 1 <Boolean>. Accepted Values: true, false. Example Call: \n> 1 true");
                            } else {
                                callTrafficStatusEndpoint(UUID.fromString(parts[1]));
                            }
                        }
                        case "3" -> callPriorityRequestEndpoint();
                        case "4" -> {
                            if (parts.length != 2) {
                                System.out.println(
                                        "ERROR: Wrong Input. Correct Pattern: 3 <requestId>. Example Call: \n> 3 123e4567-e89b-12d3-a456-426614174000");
                            } else {
                                callRequestStatusEndpoint(parts[1]);
                            }
                        }
                        case "5" -> callIntersectionStatus();
                        case "6" -> runDemo();
                        case "q", "quit", "exit" -> running = false;
                        default -> System.out.println("Unknown command: " + command);
                    }
                } catch (Exception e) {
                    System.err.println("Call failed: " + e.getMessage());
                }
            }
        }
    }

    private void printMenu() {
        System.out.println("""

                === Menu ===
                1                  → GET  /api/status/traffic-lights
                2 <UUID>           → GET  /api/status/traffic?traffic-light-id={UUID}
                3                  → POST /api/priority/requests
                4 <requestId>      → GET  /api/priority/requests/{requestId}
                5                  → View full Intersection Status (combines options 1 + 2; no separate endpoint)
                6                  → Run a demo of all endpoints with dummy data.
                q                  → Quit
                """);
    }

    private void runDemo() throws Exception {
        System.out.println("=== Emergency Vehicle Client ===");
        System.out.println("Using Quarkus REST Client");

        // EXTERNAL endpoints only (what the client is allowed to call)
        callTrafficLightsEndpoint();
        callTrafficStatusEndpoint(DUMMY_UUID);
        callPriorityRequestEndpoint();
        callRequestStatusEndpoint(DUMMY_REQUEST_ID);
        callIntersectionStatus();

        System.out.println("=== Demo finished ===");
    }

    private void callIntersectionStatus() throws Exception {
        ArrayList<TrafficLightId> trafficLights = apiClient.getTrafficLights();
        System.out.println("\n This Function is not a direct Endpoint, but a combination of Endpoint Calls.\n");
        System.out.println("=== Intersection State: ===");
        System.out.println("-----");
        for(TrafficLightId id : trafficLights){
            UUID uuid = id.getUuid();
            TrafficStatus status = apiClient.getTrafficStatus(uuid);  
            System.out.println("Traffic Light Direction: " + id.getDirection());
            System.out.println("UUID: " + id.getUuid());
            System.out.println("Vehicle State: " + status.getState());
            System.out.println("Pedestrian State: " + status.getPedestrianState());
            System.out.println("-----");
        }
    }

    /**
     * Calls:
     * GET /api/status/traffic?vehicle=true
     *
     * According to your spec:
     * - Type: External
     * - Purpose: Get Traffic Status
     * - Parameters: Query: vehicle (Boolean)
     */
    private void callTrafficStatusEndpoint(UUID vehicle) throws Exception {
        System.out.println("\n--- GET /api/status/traffic?traffic-light-id=" + vehicle.toString() + "---");

        try {
            TrafficStatus status = apiClient.getTrafficStatus(vehicle);
            System.out.println("Status: 200 OK");
            System.out.println("State: " + status.getState());
            System.out.println("Timestamp: " + status.getTimestamp());
        } catch (Exception e) {
            System.err.println("Error calling traffic status endpoint: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Calls:
     * GET /api/status/traffic-lights
     *
     * According to your spec:
     * - Type: External
     * - Purpose: Get Traffic Status
     * - Parameters: Query: vehicle (Boolean)
     */
    private void callTrafficLightsEndpoint() throws Exception {
        System.out.println("\n--- GET /api/status/traffic-lights");

        try {
            ArrayList<TrafficLightId> statusList = apiClient.getTrafficLights();
            System.out.println("Status: 200 OK");
            System.out.println("---------");
            for(TrafficLightId id : statusList){
                System.out.println("Traffic Light UUID: " + id.getUuid());
                System.out.println("Direction: " + id.getDirection());
                System.out.println("Longitude Coordinate: " + id.getLongitude());
                System.out.println("Lattitude Coordinate: " + id.getLatitude());
                System.out.println("---------");
            }
        } catch (Exception e) {
            System.err.println("Error calling traffic light devices endpoint: " + e.getMessage());
            throw e;
        }
    }

    private void callPriorityRequestEndpoint() throws Exception {
        System.out.println("\n--- POST /api/priority/requests ---");

        PriorityRequest request = new PriorityRequest("mayor");
        System.out.println("Request: " + request.getVehicleType());

        try {
            PriorityResponse response = apiClient.createPriorityRequest(request);
            System.out.println("Status: 200 OK");
            System.out.println("Response status: " + response.getStatus());
            System.out.println("Request ID: " + response.getRequestId());
        } catch (Exception e) {
            System.err.println("Error calling priority request endpoint: " + e.getMessage());
            throw e;
        }
    }

    private void callRequestStatusEndpoint(String request_id) throws Exception {
        System.out.println("\n--- GET /api/priority/requests/{requestId} ---");
        System.out.println("Using requestId: " + request_id);

        try {
            RequestStatus status = apiClient.getRequestStatus(request_id);
            System.out.println("Status: 200 OK");
            System.out.println("Request ID: " + status.getRequestId());
            System.out.println("Status: " + status.getStatus());
            System.out.println("Vehicle Type: " + status.getVehicleType());
        } catch (Exception e) {
            System.err.println("Error calling request status endpoint: " + e.getMessage());
            throw e;
        }
    }
}
