package de.tub.aot.client;

import de.tub.aot.client.api.PriorityRequest;
import de.tub.aot.client.api.PriorityResponse;
import de.tub.aot.client.api.RequestStatus;
import de.tub.aot.client.api.TccApiClient;
import de.tub.aot.client.api.TrafficStatus;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.URI;

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
public class MayorVehicleClient {

    // Base URL of the public API (Ingress or directly the service for local dev)
    private static final String BASE_URL = System.getProperty("base.url", "http://localhost:8080");

    // Dummy requestId for GET /api/priority/requests/{requestId}
    private static final String DUMMY_REQUEST_ID = "123e4567-e89b-12d3-a456-426614174000";

    private final TccApiClient apiClient;

    public MayorVehicleClient() {
        // Build REST client with base URL
        this.apiClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(BASE_URL))
                .build(TccApiClient.class);
    }

    public static void main(String[] args) {
        MayorVehicleClient client = new MayorVehicleClient();
        try {
            client.runDemo();
        } catch (Exception e) {
            System.err.println("Error while calling services:");
            e.printStackTrace();
        }
    }

    private void runDemo() throws Exception {
        System.out.println("=== Mayor Vehicle Client ===");
        System.out.println("Using base URL: " + BASE_URL);

        // EXTERNAL endpoints only (what the client is allowed to call)
        callTrafficStatusEndpoint();
        callPriorityRequestEndpoint();
        callRequestStatusEndpoint();

        System.out.println("=== Demo finished ===");
    }

    private void callTrafficStatusEndpoint() throws Exception {
        System.out.println("\n--- GET /api/status/traffic?vehicle=true ---");

        try {
            TrafficStatus status = apiClient.getTrafficStatus(true);
            System.out.println("Status: 200 OK");
            System.out.println("State: " + status.getState());
            System.out.println("Timestamp: " + status.getTimestamp());
        } catch (Exception e) {
            System.err.println("Error calling traffic status endpoint: " + e.getMessage());
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

    private void callRequestStatusEndpoint() throws Exception {
        System.out.println("\n--- GET /api/priority/requests/{requestId} ---");
        System.out.println("Using dummy requestId: " + DUMMY_REQUEST_ID);

        try {
            RequestStatus status = apiClient.getRequestStatus(DUMMY_REQUEST_ID);
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
