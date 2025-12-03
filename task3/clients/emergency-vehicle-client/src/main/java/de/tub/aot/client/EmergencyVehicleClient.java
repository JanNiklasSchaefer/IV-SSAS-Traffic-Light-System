package de.tub.aot.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Simple demo client for the Emergency Vehicle.
 *
 * According to your REST API spec, clients only call the EXTERNAL endpoints:
 * - GET /api/status/traffic
 * - POST /api/priority/requests
 *
 * This client:
 * 1. Requests the current traffic status for a vehicle
 * 2. Sends a priority request for an emergency vehicle
 *
 * Default base URL is http://localhost:8080 (for Quarkus dev mode),
 * but you can override it with:
 * -Dbase.url=http://some-host:some-port
 */
public class EmergencyVehicleClient {

    // Base URL of the public API (Ingress or directly the service for local dev)
    private static final String BASE_URL = System.getProperty("base.url", "http://localhost:8080");

    // Dummy requestId for GET /api/priority/requests/{requestId}
    private static final String DUMMY_REQUEST_ID = "123e4567-e89b-12d3-a456-426614174000";

    private final HttpClient httpClient;

    public EmergencyVehicleClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    public static void main(String[] args) {
        EmergencyVehicleClient client = new EmergencyVehicleClient();
        try {
            client.runDemo();
        } catch (Exception e) {
            System.err.println("Error while calling services:");
            e.printStackTrace();
        }
    }

    private void runDemo() throws Exception {
        System.out.println("=== Emergency Vehicle Client ===");
        System.out.println("Using base URL: " + BASE_URL);

        // EXTERNAL endpoints only (what the client is allowed to call)
        callTrafficStatusEndpoint();
        callPriorityRequestEndpoint();
        callRequestStatusEndpoint();

        System.out.println("=== Demo finished ===");
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
    private void callTrafficStatusEndpoint() throws Exception {
        System.out.println("\n--- GET /api/status/traffic?vehicle=true ---");

        URI uri = URI.create(BASE_URL + "/api/status/traffic?vehicle=true");

        System.out.println("Sending request to: " + uri);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();

        System.out.println("Sending request...");

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
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
     * For Task 3 we just send hard-coded JSON and print the response.
     */
    private void callPriorityRequestEndpoint() throws Exception {
        System.out.println("\n--- POST /api/priority/requests ---");

        URI uri = URI.create(BASE_URL + "/api/priority/requests");

        String jsonBody = """
                {
                  "vehicleType": "emergency"
                }
                """;

        System.out.println("Sending request to: " + uri);
        System.out.println("Request body: " + jsonBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.ofSeconds(5))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        System.out.println("Sending request...");

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println("Body:   " + response.body());
    }

    /**
     * Calls:
     * GET /api/priority/requests/{requestId}
     *
     * Laut Spec:
     * - Type: External
     * - Purpose: Get Request Status
     * - Path-Parameter: requestId
     *
     * Für Task 3 verwenden wir einfach eine Dummy-ID und drucken die Antwort.
     */
    private void callRequestStatusEndpoint() throws Exception {
        System.out.println("\n--- GET /api/priority/requests/{requestId} ---");
        System.out.println("Using dummy requestId: " + DUMMY_REQUEST_ID);

        String path = "/api/priority/requests/" + DUMMY_REQUEST_ID;
        URI uri = URI.create(BASE_URL + path);

        System.out.println("Sending request to: " + uri);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();

        System.out.println("Sending request...");

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status: " + response.statusCode());
        System.out.println("Body:   " + response.body());
    }

}
