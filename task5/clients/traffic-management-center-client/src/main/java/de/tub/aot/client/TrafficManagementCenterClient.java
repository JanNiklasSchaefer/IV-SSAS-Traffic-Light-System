package de.tub.aot.client;

import de.tub.aot.client.api.EmergencyModeRequest;
import de.tub.aot.client.api.IntersectionState;
import de.tub.aot.client.api.PriorityRequest;
import de.tub.aot.client.api.PriorityResponse;
import de.tub.aot.client.api.RequestStatus;
import de.tub.aot.client.api.TccApiClient;
import de.tub.aot.common.models.TrafficStatus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.Scanner;

/**
 * Traffic Management Center Client for TCC Operators.
 *
 * This client allows TCC operators to manually control traffic intersections:
 * - Monitor traffic status
 * - Set intersection states manually
 * - Trigger emergency modes
 * - Override automated traffic control
 *
 * This client uses Quarkus REST Client for type-safe API calls.
 * Default base URL is http://localhost:8080 (for Quarkus dev mode),
 * but you can override it with:
 * -Dbase.url=http://some-host:some-port
 */
@QuarkusMain
public class TrafficManagementCenterClient implements QuarkusApplication {

    // Sample intersection ID for demo purposes
    private static final String SAMPLE_INTERSECTION_ID = "intersection-001";
    private static final String OPERATOR_NAME = "TCC-Operator";

    @Inject
    @RestClient
    TccApiClient apiClient;

    @Override
    public int run(String... args) {
        System.out.println("=== Traffic Management Center Client ===");

        // Check if demo mode is requested
        if (args.length > 0 && "demo".equals(args[0])) {
            try {
                runTccDemo();
            } catch (Exception e) {
                System.err.println("Demo failed: " + e.getMessage());
                return 1;
            }
        } else {
            runInteractive();
        }
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
                        case "1" -> {
                            if (parts.length != 2) {
                                System.out.println(
                                        "ERROR: Missing Vehicle Boolean Input or too many Input Parameters. Correct Pattern: 1 <Boolean>. Accepted Values: true, false. Example Call: \n> 1 true");
                            } else if ((!parts[1].equals("true") && !parts[1].equals("false"))) {
                                System.out.println(parts[1]);
                                System.out.println(
                                        "ERROR: Wrong Vehicle Boolean Input value. Correct Pattern: 1 <Boolean>. Accepted Values: true, false. Example Call: \n> 1 true");
                            } else {
                                callTrafficStatusEndpoint(Boolean.valueOf(parts[1]));
                            }
                        }
                        case "2" -> callPriorityRequestEndpoint();
                        case "3" -> {
                            if (parts.length != 2) {
                                System.out.println(
                                        "ERROR: Wrong Input. Correct Pattern: 3 <requestId>. Example Call: \n> 3 123e4567-e89b-12d3-a456-426614174000");
                            } else {
                                callRequestStatusEndpoint(parts[1]);
                            }
                        }
                        case "4" -> {
                            if (parts.length != 2) {
                                System.out.println(
                                        "ERROR: Wrong Input. Correct Pattern: 4 <intersectionId>. Example Call: \n> 4 intersection-001");
                            } else {
                                callGetIntersectionState(parts[1]);
                            }
                        }
                        case "5" -> {
                            if (parts.length != 3) {
                                System.out.println(
                                        "ERROR: Wrong Input. Correct Pattern: 5 <intersectionId> <state>. Example Call: \n> 5 intersection-001 red");
                                System.out.println("Valid states: green, yellow, red, emergency, maintenance");
                            } else {
                                callSetIntersectionState(parts[1], parts[2]);
                            }
                        }
                        case "6" -> {
                            if (parts.length != 3) {
                                System.out.println(
                                        "ERROR: Wrong Input. Correct Pattern: 6 <reason> <affectedArea>. Example Call: \n> 6 accident main-street");
                                System.out.println("Valid reasons: accident, protest, maintenance, fire");
                            } else {
                                callTriggerEmergencyMode(parts[1], parts[2]);
                            }
                        }
                        case "7" -> runTccDemo();
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
                === Traffic Management Center Menu ===
                1 <Boolean>        → GET  /api/status/traffic?vehicle={Boolean}
                2                  → POST /api/priority/requests
                3 <requestId>      → GET  /api/priority/requests/{requestId}
                4 <intersectionId> → GET  /api/state/manual/{intersectionId}
                5 <intersectionId> <state> → PUT  /api/state/manual/{intersectionId}
                6 <reason> <area>  → POST /api/state/emergency
                7                  → Run a demo of TCC operator endpoints
                q                  → Quit
                """);
    }

    private void runTccDemo() throws Exception {
        System.out.println("=== Traffic Management Center Client Demo ===");
        System.out.println("Using Quarkus REST Client");

        // Standard monitoring
        callTrafficStatusEndpoint(true);

        // TCC operator operations
        callGetIntersectionState(SAMPLE_INTERSECTION_ID);
        callSetIntersectionState(SAMPLE_INTERSECTION_ID, "yellow");
        callTriggerEmergencyMode("maintenance", "downtown-area");

        System.out.println("=== TCC Demo finished ===");
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
    private void callTrafficStatusEndpoint(Boolean vehicle) throws Exception {
        System.out.println("\n--- GET /api/status/traffic?vehicle=" + vehicle + "---");

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
     * POST /api/priority/requests
     *
     * According to your spec:
     * - Type: External
     * - Purpose: Post Priority Request
     * - Body: vehicleType (String)
     * - Response: { "status": "accepted" | "denied" }
     *
     * For Task 4 we use Quarkus REST Client for type-safe calls.
     */
    private void callPriorityRequestEndpoint() throws Exception {
        System.out.println("\n--- POST /api/priority/requests ---");

        PriorityRequest request = new PriorityRequest("emergency");
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

    /**
     * Calls:
     * GET /api/priority/requests/{requestId}
     *
     * According to your spec:
     * - Type: External
     * - Purpose: Get Request Status
     * - Path-Parameter: requestId
     *
     * For Task 4 we use Quarkus REST Client for type-safe calls.
     */
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

    /**
     * Calls:
     * GET /api/state/manual/{intersectionId}
     *
     * TCC Operator: Get current state of an intersection
     */
    private void callGetIntersectionState(String intersectionId) throws Exception {
        System.out.println("\n--- GET /api/state/manual/" + intersectionId + " ---");

        try {
            IntersectionState state = apiClient.getIntersectionState(intersectionId);
            System.out.println("Status: 200 OK");
            System.out.println("Intersection ID: " + state.getIntersectionId());
            System.out.println("State: " + state.getState());
            System.out.println("Direction: " + state.getDirection());
            System.out.println("Operator: " + state.getOperator());
            System.out.println("Reason: " + state.getReason());
            System.out.println("Timestamp: " + state.getTimestamp());
        } catch (Exception e) {
            System.err.println("Error calling get intersection state endpoint: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Calls:
     * PUT /api/state/manual/{intersectionId}
     *
     * TCC Operator: Set intersection state manually
     */
    private void callSetIntersectionState(String intersectionId, String newState) throws Exception {
        System.out.println("\n--- PUT /api/state/manual/" + intersectionId + " ---");

        IntersectionState state = new IntersectionState(intersectionId, newState, "all", OPERATOR_NAME,
                "manual_override");
        System.out.println("Setting intersection " + intersectionId + " to state: " + newState);

        try {
            Response response = apiClient.setIntersectionState(intersectionId, state);
            System.out.println("Status: " + response.getStatus());
            System.out.println("Intersection state updated successfully");
        } catch (Exception e) {
            System.err.println("Error calling set intersection state endpoint: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Calls:
     * POST /api/state/emergency
     *
     * TCC Operator: Trigger emergency mode for an area
     */
    private void callTriggerEmergencyMode(String reason, String affectedArea) throws Exception {
        System.out.println("\n--- POST /api/state/emergency ---");

        EmergencyModeRequest request = new EmergencyModeRequest(OPERATOR_NAME, reason, affectedArea, true, 30);
        System.out.println("Triggering emergency mode:");
        System.out.println("Reason: " + reason);
        System.out.println("Affected Area: " + affectedArea);
        System.out.println("All Red: " + request.isAllRed());
        System.out.println("Duration: " + request.getDurationMinutes() + " minutes");

        try {
            Response response = apiClient.triggerEmergencyMode(request);
            System.out.println("Status: " + response.getStatus());
            System.out.println("Emergency mode activated successfully");
        } catch (Exception e) {
            System.err.println("Error calling trigger emergency mode endpoint: " + e.getMessage());
            throw e;
        }
    }
}
