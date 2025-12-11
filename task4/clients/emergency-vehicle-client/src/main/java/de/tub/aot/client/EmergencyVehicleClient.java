package de.tub.aot.client;

import de.tub.aot.client.api.PriorityRequest;
import de.tub.aot.client.api.PriorityResponse;
import de.tub.aot.client.api.RequestStatus;
import de.tub.aot.client.api.TccApiClient;
import de.tub.aot.client.api.TrafficStatus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 * Simple demo client for the Emergency Vehicle.
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
public class EmergencyVehicleClient implements QuarkusApplication {

    // Dummy requestId for GET /api/priority/requests/{requestId}
    private static final String DUMMY_REQUEST_ID = "123e4567-e89b-12d3-a456-426614174000";

    @Inject
    @RestClient
    TccApiClient apiClient;

    @Override
    public int run(String... args) throws Exception {
        runDemo();
        return 0;
    }

    private void runDemo() throws Exception {
        System.out.println("=== Emergency Vehicle Client ===");
        System.out.println("Using Quarkus REST Client");

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
