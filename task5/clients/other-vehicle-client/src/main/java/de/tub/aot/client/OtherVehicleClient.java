package de.tub.aot.client;

import de.tub.aot.client.api.PriorityRequest;
import de.tub.aot.client.api.PriorityResponse;
import de.tub.aot.client.api.RequestStatus;
import de.tub.aot.client.api.TccApiClient;
import de.tub.aot.client.api.TrafficStatus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 * Simple demo client for a generic Other Vehicle.
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
public class OtherVehicleClient implements QuarkusApplication {

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
        System.out.println("=== Other Vehicle Client ===");
        System.out.println("Using Quarkus REST Client");

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

        PriorityRequest request = new PriorityRequest("other");
        System.out.println("Request: " + request.getVehicleType());

        try {
            PriorityResponse response = apiClient.createPriorityRequest(request);
            System.out.println("Status: 200 OK");
            System.out.println("Response status: " + response.getStatus());
            System.out.println("Request ID: " + response.getRequestId());
        } catch (jakarta.ws.rs.WebApplicationException e) {
            if (e.getResponse() != null && e.getResponse().getStatus() == 403) {
                System.out.println(
                        "Status: 403 Forbidden (expected - other-vehicle does not have permission for priority requests)");
            } else {
                System.err.println("Error calling priority request endpoint: " + e.getMessage());
                throw e;
            }
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
        } catch (jakarta.ws.rs.WebApplicationException e) {
            if (e.getResponse() != null && e.getResponse().getStatus() == 403) {
                System.out.println(
                        "Status: 403 Forbidden (expected - other-vehicle does not have permission for priority requests)");
            } else {
                System.err.println("Error calling priority request endpoint: " + e.getMessage());
                throw e;
            }
        } catch (Exception e) {
            System.err.println("Error calling priority request endpoint: " + e.getMessage());
            throw e;
        }
    }
}
