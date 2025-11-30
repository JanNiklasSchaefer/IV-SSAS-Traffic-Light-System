## Task 3 – Microservices, Clients, and Deployment Guide

This folder contains the working artifacts for Task 3 of the SAM traffic-light assignment.  
Goal: deliver runnable microservices, Kubernetes manifests, and basic clients (even with stubbed logic) that exercise the REST API defined in Task 2, plus documentation and a release package.

### Repository Layout

```
task3/
├── services/                # Source & Docker context for each microservice
│   ├── tcc-priority-service/
│   ├── tcc-state-controller/
│   ├── tcc-status-service/
│   ├── tcc-auth-service/
│   ├── tcc-audit-service/
│   ├── traffic-light-device-service/
│   ├── time-service/
│   └── location-validator/
├── clients/                 # Client apps hitting the REST endpoints
│   ├── emergency-vehicle-client/
│   ├── mayor-vehicle-client/
│   ├── other-vehicle-client/
│   └── pedestrian-client/
├── kubernetes/
│   └──namespaces/
│
└── README.md                # You are here
```

### REST API Specification (Team Agreement)

Complete overview of all REST endpoints organized by service.

| Service                          | Type     | Method | Path                                 | Purpose                               | Parameters / Request Body                                                                | Response                               | Communication                                                 |
| -------------------------------- | -------- | ------ | ------------------------------------ | ------------------------------------- | ---------------------------------------------------------------------------------------- | -------------------------------------- | ------------------------------------------------------------- |
| **Clients (outside cluster)**    | External | `GET`  | `/api/status/traffic`                | Get Traffic Status                    | Query: `vehicle` (Boolean)                                                               | Traffic Status object                  | Vehicle Clients → Ingress → TCC Status Service                |
|                                  | External | `POST` | `/api/priority/requests`             | Post Priority Request                 | Body: `vehicleType` (String)                                                             | `{ "status": "accepted" \| "denied" }` | Vehicle Clients → Ingress → TCC Priority Service              |
|                                  | External | `GET`  | `/api/priority/requests/{requestId}` | Get Request Status (maybe)            | Path: `requestId`                                                                        | Request status object                  | Vehicle Clients → Ingress → TCC Priority Service              |
|                                  |          |        |                                      |                                       | Note: Each client wraps user input and calls these public endpoints via Ingress hostname |                                        |                                                               |
| **Ingress Controller**           | Internal | `GET`  | `/api/status/traffic`                | Route to TCC Status Service           | Query: `vehicle` (Boolean) - Logic needed to decide which traffic devices to request     | Traffic Status object                  | Receives from Vehicle Clients, routes to TCC Status Service   |
|                                  | Internal | `POST` | `/api/priority/requests`             | Route to TCC Priority Service         | Body: `vehicleType` (String)                                                             | `{ "status": "accepted" \| "denied" }` | Receives from Vehicle Clients, routes to TCC Priority Service |
|                                  | Internal | `GET`  | `/api/priority/requests/{requestId}` | Route to TCC Priority Service         | Path: `requestId`                                                                        | Request status object                  | Receives from Vehicle Clients, routes to TCC Priority Service |
| **TCC Priority Service**         | Internal | `POST` | `/api/auth/token`                    | Request access token                  | Credentials                                                                              | Access token                           | Calls TCC Auth Service                                        |
|                                  | Internal | `POST` | `/api/auth/refresh`                  | Refresh access token                  | Refresh token                                                                            | Refreshed token                        | Calls TCC Auth Service                                        |
|                                  | Internal | `POST` | `/api/auth/session`                  | Request session token                 | Session data                                                                             | Session token                          | Calls TCC Auth Service                                        |
|                                  | Internal | `POST` | `/api/auth/authenticate`             | Authenticate request                  | Auth data                                                                                | Auth result                            | Calls TCC Auth Service                                        |
|                                  | Internal | `POST` | `/api/state/change`                  | Trigger state change                  | State command                                                                            | HTTP status code                       | Calls TCC State Controller                                    |
|                                  | Internal | `POST` | `/api/audit/events`                  | Log priority request                  | `{ "request": {...}, "timestamp": "..." }`                                               | Success confirmation                   | Calls TCC Audit Service                                       |
|                                  | Internal | `POST` | `/api/location/vehicle`              | Validate vehicle location             | `{ "vehicleId": "...", "coordinates": {...} }`                                           | Validation result                      | Calls Location Validator                                      |
|                                  | Internal | `POST` | `/api/time/validate`                 | Validate timestamp                    | `{ "timestamp": "..." }`                                                                 | Validation result                      | Calls Time Service                                            |
| **TCC Status Service**           | Internal | `GET`  | `/api/device/traffic-state`          | Retrieve current state                | -                                                                                        | Traffic state                          | Calls Traffic Light Device Service                            |
| **TCC State Controller**         | Internal | `POST` | `/api/state/change`                  | Execute state change command          | State command                                                                            | HTTP status code                       | Called by TCC Priority Service                                |
|                                  | Internal | `POST` | `/api/device/change-state`           | Apply state change to device          | `{ "state": "..." }`                                                                     | Success confirmation                   | Calls Traffic Light Device Service                            |
|                                  | Internal | `POST` | `/api/audit/events`                  | Log state change                      | `{ "request": {...}, "timestamp": "..." }`                                               | Success confirmation                   | Calls TCC Audit Service                                       |
| **TCC Auth Service**             | Internal | `POST` | `/api/auth/token`                    | Issue access token                    | Credentials                                                                              | Access token                           | Called by TCC Priority Service                                |
|                                  | Internal | `POST` | `/api/auth/refresh`                  | Refresh access token                  | Refresh token                                                                            | Refreshed token                        | Called by TCC Priority Service                                |
|                                  | Internal | `POST` | `/api/auth/session`                  | Issue session token                   | Session data                                                                             | Session token                          | Called by TCC Priority Service                                |
|                                  | Internal | `POST` | `/api/auth/authenticate`             | Authenticate request                  | Auth data                                                                                | Auth result                            | Called by TCC Priority Service                                |
|                                  | External | -      | OIDC/OAuth2                          | Proxy to Keycloak                     | OAuth2/OIDC protocol                                                                     | Keycloak tokens                        | Calls Keycloak (Task 5)                                       |
| **TCC Audit Service**            | Internal | `POST` | `/api/audit/events`                  | Log audit event                       | `{ "request": {...}, "timestamp": "..." }`                                               | Success confirmation                   | Called by TCC Priority Service, TCC State Controller          |
|                                  | Internal | `GET`  | `/api/audit/logs`                    | Retrieve audit logs (stub for Task 3) | Query: `from`, `to` (optional)                                                           | Audit log entries                      | TODO: Admin Service (Task 5)                                  |
| **Traffic Light Device Service** | Internal | `GET`  | `/api/device/traffic-state`          | Get current traffic light state       | -                                                                                        | Traffic state                          | Called by TCC Status Service                                  |
|                                  | Internal | `POST` | `/api/device/change-state`           | Apply state change                    | `{ "state": "..." }`                                                                     | Success confirmation                   | Called by TCC State Controller                                |
| **Location Validator Service**   | Internal | `POST` | `/api/location/vehicle`              | Validate vehicle location             | `{ "vehicleId": "...", "coordinates": {...} }`                                           | Validation result                      | Called by TCC Priority Service                                |
| **Time Service**                 | Internal | `POST` | `/api/time/validate`                 | Validate timestamp                    | `{ "timestamp": "..." }`                                                                 | Validation result                      | Called by TCC Priority Service                                |

**Legend:**

- **External:** Communication from clients outside the cluster
- **Internal:** Service-to-service communication within the cluster
- **Calls:** This service initiates the call to another service
- **Called by:** This service receives calls from another service

### Testing Java Files

There are two ways to test your Java files locally:

#### Option 1: Quarkus Dev Mode (Recommended)

Start the service in development mode with hot reload:

```bash
cd task3/services/tcc-priority-service
../../mvnw compile quarkus:dev
```

The service will start on `http://localhost:8080`. You can:

- Test endpoints via `curl` or browser
- Access OpenAPI UI at `http://localhost:8080/q/swagger-ui`
- Access OpenAPI spec at `http://localhost:8080/q/openapi`
- Changes to Java files will automatically reload

**Example:**

```bash
# Test Priority Request endpoint
curl -X POST http://localhost:8080/api/priority/requests \
  -H "Content-Type: application/json" \
  -d '{"vehicleType": "emergency"}'
```

**Stop the service:** Press `Ctrl+C` in the terminal

#### Option 2: Main Method (Simple Testing)

For simple logic testing without REST endpoints, you can create a main method:

```java
public class TestPriorityResource {
    public static void main(String[] args) {
        PriorityResource resource = new PriorityResource();
        PriorityRequest request = new PriorityRequest("emergency");
        // Test your logic here
    }
}
```

**Note:** REST endpoints (`@Path`, `@GET`, `@POST`) require Quarkus/JAX-RS framework and won't work with a simple main method. Use Quarkus Dev Mode for testing REST APIs.
