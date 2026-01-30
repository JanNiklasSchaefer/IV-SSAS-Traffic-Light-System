package de.tub.aot.client;

import de.tub.aot.client.api.TccApiClient;
import de.tub.aot.common.models.TrafficStatus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

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

    @Inject
    @RestClient
    TccApiClient apiClient;

    @Override
    public int run(String... args) {
        System.out.println("=== Traffic Management Center Client ===");
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
                        case "1" -> callGetIntersectionState();
                        case "2" -> {
                            if (parts.length != 3) {
                                System.out.println(
                                        "ERROR: Missing Parameters or too many Input Parameters. Correct Pattern: 2 <UUID> <String>. \n Accepted Values for String: red, green. \n Example Call: \n> 2 8d8d1437-907b-3a79-900a-c5f0ea1f5c73 green");
                            } else {
                                callSetIntersectionState(UUID.fromString(parts[1]), parts[2]);
                            }
                        }
                        case "3" -> runTccDemo();
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
                1                    → GET  /api/state/manual/state
                2 <UUID> <String>    → PUT  /api/state/manual/change-state?traffic-light-id={UUID} Body: {String}. Accepted Values for {String} = red or green.
                3                    → Run a demo of all TCC operator endpoints with Dummy Data.
                q                    → Quit
                """);
    }

    private void runTccDemo() throws Exception {
        System.out.println("=== Traffic Management Center Client Demo ===");
        System.out.println("Using Quarkus REST Client");
        UUID northId = UUID.fromString("8d8d1437-907b-3a79-900a-c5f0ea1f5c73");
        String goalState = "red";
        // TCC operator operations
        callGetIntersectionState();
        callSetIntersectionState(northId,goalState);

        System.out.println("=== TCC Demo finished ===");
    }

    /**
     * Calls:
     * GET /api/state/manual/{intersectionId}
     *
     * TCC Operator: Get current state of an intersection
     */
    private void callGetIntersectionState() throws Exception {
        System.out.println("\n--- GET /api/state/manual/state");
        try {
            ArrayList<TrafficStatus> state = apiClient.getIntersectionState();
                for(TrafficStatus status : state){
                            System.out.println("Traffic Light Direction: " + status.getTrafficLightId().getDirection());
                            System.out.println("UUID: " + status.getTrafficLightId().getUuid());
                            System.out.println("Vehicle State: " + status.getState());
                            System.out.println("Pedestrian State: " + status.getPedestrianState());
                            System.out.println("-----");
                        }
        } catch (Exception e) {
            System.err.println("Error calling get intersection state endpoint: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Calls:
     * PUT /api/state/manual/change-state
     *
     * TCC Operator: Set intersection state manually
     */
    private void callSetIntersectionState(UUID trafficLightId, String newState) throws Exception {
        System.out.println("\n--- PUT /api/state/manual/change-state?traffic-light-id" + trafficLightId.toString() + "---");
        try {
            Response response = apiClient.setIntersectionState(trafficLightId, newState);
            System.out.println("Status: " + response.getStatus());
            System.out.println("Intersection state updated successfully");
        } catch (Exception e) {
            System.err.println("Error calling set intersection state endpoint: " + e.getMessage());
            throw e;
        }
    }
}
