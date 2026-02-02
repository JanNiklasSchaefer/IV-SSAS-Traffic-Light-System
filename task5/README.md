## Task 6: Functionality and tests

### Public Endpoints Overview

The TCC system provides the following public endpoints for client applications:

#### Priority Service (Vehicle Clients)

- `POST /api/priority/requests` - Submit priority request
  - **Body:** `PriorityRequest` {vehicleType, vehicleId, trafficLightId}
  - **Response:** `PriorityResponse` {status, requestId}
- `GET /api/priority/requests/{requestId}` - Check priority request status
  - **Path:** requestId (UUID)
  - **Response:** `PriorityResponse` {status, requestId}

#### Status Service (All Vehicle Clients)

- `GET /api/status/traffic?traffic-light-id={uuid}` - Get specific traffic light status
  - **Query:** trafficLightId (UUID)
  - **Response:** `TrafficStatus` {trafficLightId, timestamp, state, pedestrianState}
- `GET /api/status/traffic-lights` - Get all traffic lights
  - **Response:** `Array<TrafficLightId>` [{uuid, latitude, longitude, direction}]

#### State Controller (TCC Operator)

- `GET /api/state/management/state` - Get complete intersection status
  - **Response:** `Array<TrafficStatus>` - Status of all lights at intersection
- `PUT /api/state/management/change-state` - Manually change traffic light
  - **Query:** trafficLightId (UUID)
  - **Body:** goalState (String: "red", "yellow", "green")
  - **Response:** Success/Error message

All endpoints require JWT authentication and implement role-based access control.

### Prerequisites

Download the IV-SSAS-Kind-Cluster. Follow the Instructions there to install and start it : https://git.tu-berlin.de/aot-security/iv-software-security-for-autonomous-systems/examples/iv-ssas-kind-cluster

The Cluster must be running on your device before you execute the next steps.

Additionally you need:

- Docker Desktop or Docker Engine running
- Local Docker registry (optional, for pushing images)
- **PKI from Task 1**: The Intermediate CA (`ha1-gruppe8-krzysztoflagowski-clusterissuer`) must be deployed in the cluster. If not already deployed, see "Deploy to Kubernetes" → Step 3 for instructions.
- **cert-manager** and **trust-manager** must be installed in the cluster

### Kubernetes Deployment

#### Prerequisites

- Kubernetes cluster running (e.g., kind, minikube, or cloud provider)
- `kubectl` configured to connect to your cluster
- Docker registry accessible from the cluster (or use local registry with kind)

#### Deploy to Kubernetes

**Namespace Structure**

Services are organized into multiple namespaces for better separation:

- **`gruppe8-tcc`**: TCC core services (Priority, State Controller, Status, Audit)
- **`gruppe8-traffic-light-devices`**: Traffic Light Device Service
- **`gruppe8-shared-services`**: Shared services (Time Service, Location Validator)
- **`ingress-nginx`**: Ingress controller (managed by cluster admin)

**Deployment Steps**

Follow these steps in order:

```bash
# Step 1: Build and test (optional, but recommended)
cd task5
mvn clean install

# Step 2: Create namespaces
# Important: Quarkus does NOT automatically create namespaces for security reasons.
kubectl apply -f kubernetes/namespaces/gruppe8-tcc.yaml

# Step 3: Deploy TLS certificates (Task 4)
# Certificates are created by cert-manager using the PKI from Task 1

# Deploy PKI from Task 1 (if not already deployed - safe to run multiple times)
kubectl apply -f kubernetes/pki/ca-certificate.yaml
kubectl wait --for=condition=Ready certificate ha1-gruppe8-krzysztoflagowski-ca -n cert-manager --timeout=60s
kubectl apply -f kubernetes/pki/ca-issuer.yaml
kubectl wait --for=condition=Ready clusterissuer ha1-gruppe8-krzysztoflagowski-clusterissuer --timeout=60s

# Deploy Task 4 certificates
kubectl apply -f kubernetes/certificates/

# Wait for all certificates to be ready
kubectl wait --for=condition=Ready certificate \
    task4-gruppe8-tcc-priority-service-cert \
    task4-gruppe8-tcc-status-service-cert \
    task4-gruppe8-tcc-ingress-cert \
    task4-gruppe8-emergency-vehicle-client-cert \
    -n gruppe8-tcc --timeout=120s

# Step 4: Deploy Ingress (before building to avoid conflicts)
kubectl apply -f kubernetes/ingress.yaml

# Step 5: Deploy updated Keycloak.yaml and wait for deployment
# This is necessary to guarantee the Security Policy. Changes are explained in later Section.
kubectl apply -f kubernetes/keycloak.yaml -n keycloak
kubectl rollout status statefulset/keycloak --timeout=60s

# Step 6: Apply Network Policies
kubectl apply -f kubernetes/network-policies
```

Before we can finally deploy all our services, we need to first import the keycloak realm. Otherwise the services will not be able to start.

#### Preconditions

- Kubernetes cluster is running
- Namespace keycloak already exists
- A Keycloak pod is already running in that namespace
- kubectl access to the cluster
- File group8-task5-realm-import.json is present locally

1. Verify Keycloak pod

```bash
kubectl get pods -n keycloak
```

You should see something similar to:

```bash
keycloak-0   1/1   Running
```

If the pod name differs, replace keycloak-0 in the commands below.

2. Copy the realm import file into the Keycloak pod

```bash
kubectl exec -n keycloak -i keycloak-0 -- sh -c \
  'cat > /tmp/group8-task5-realm-import.json' \
  < group8-task5-realm-import.json
```

Verify the file exists:

```bash
kubectl exec -n keycloak keycloak-0 -- ls -lah /tmp | grep group8-task5
```

3. Import the realm into Keycloak (The http management port needs to be changed when keycloak is still turned on. Alternatively turn off keycloak.)

```bash
kubectl exec -n keycloak keycloak-0 -- \
  /opt/keycloak/bin/kc.sh import --optimized \
  --http-management-port=9002 \
  --file /tmp/group8-task5-realm-import.json
```

Expected output includes lines indicating successful realm import.

You can verify that the keycloak import worked by visiting the keycloak-admin ui. For this you need to setup the Host URL's or port forwarding. (See further down) You should see the realm `group8-task5`

**URL** `keycloak.test`
**Username** : `temp-admin`
**Password** : `923b2ce326014a22b99766c7c5ab98d2`

Then in a final step, deploy the Quarkus Services:

```bash
# Step 7: Build and deploy all services
mvn clean package -Dquarkus.kubernetes.deploy=true

kubectl apply -f kubernetes/secrets/

# Step 8: Verify deployment (optional)
# See "Verify Deployment" section below for commands
```

**Ingress Configuration**

The shared Ingress exposes:

- `https://tcc.test/api/priority/*` → Priority Service (TLS enabled)
- `https://tcc.test/api/status/*` → Status Service (TLS enabled)

**Note:** All communication is now encrypted with TLS.

**Verify Deployment**

```bash
# Check all pods across all namespaces
kubectl get pods -n gruppe8-tcc
kubectl get pods -n gruppe8-auth-services
kubectl get pods -n gruppe8-traffic-light-devices
kubectl get pods -n gruppe8-shared-services

# Check services
kubectl get services -A | grep gruppe8

# Check ingress
kubectl get ingress -n gruppe8-tcc
```

## Run Clients

Follow the next steps to run the external clients locally.

## Setup Client Certificates {#setup-client-certificates}

**IMPORTANT: Do this BEFORE running clients locally!**

To run clients locally, extract and configure certificates:

```bash
# Extract CA certificate and convert to PKCS12 for all clients
./setup-certs-now.sh
```

This script:

- Extracts the CA certificate from Kubernetes
- Converts it to PKCS12 format
- Copies certificates to all client resources directories
- Extracts client certificate for Emergency Vehicle Client (mTLS)

## Setup Host URL's

###### On macOS/Linux, edit /etc/hosts (requires sudo)

```bash
sudo nano /etc/hosts
# Add these lines:
127.0.0.1 tcc.test
127.0.0.1 keycloak.test
```

**Note:** On Windows, edit `C:\Windows\System32\drivers\etc\hosts` as Administrator.

**Alternative: Port-Forward (if Ingress is not available)**

If you prefer port-forwarding instead of Ingress:

###### Use different localhost ports for each service to avoid conflicts

```bash
# In a separate terminal, forward the Priority Service port
kubectl port-forward -n gruppe8-tcc service/gruppe8-tcc-priority-service 8443:443

# Open another terminal and forward the Keycloak service
kubectl port-forward -n keycloak svc/keycloak-service 8444:8443
```

**Note:** Port-forwarding only allows access to one service at a time. Ingress (recommended) allows access to all services via a single hostname.

## Starting and using the interactive External Clients

**Prerequisites:**

1. Ensure you have a `.env` file in the `clients/` directory with the required client secrets.
2. The `.env` file contains all client secrets

With this release, the clients are now interactive and provide a GUI via terminal where the endpoints of our cluster can be reached. To open it do the following:

**Option 1: Use run.sh Script (Recommended)**

Each client directory has a `run.sh` script that automatically loads secrets and runs the client:

```bash
# Pedestrian Client
cd clients/pedestrian-client
./run.sh

# Emergency Vehicle Client
cd clients/emergency-vehicle-client
./run.sh

# Traffic Management Center Client
cd clients/traffic-management-center-client
./run.sh

and so on....
```

**Option 2: Manual Commands**

**Example (Pedestrian Client):**

```bash
cd clients/pedestrian-client
source ../.env
export CLIENT_SECRET="${PEDESTRIAN_CLIENT_SECRET}"
mvn clean package -DskipTests
java -Dbase.url=https://tcc.test -jar target/quarkus-app/quarkus-run.jar
```

**Analog for other clients:** Replace `pedestrian-client` with the respective client directory (`emergency-vehicle-client`, `mayor-vehicle-client`, `other-vehicle-client`, `traffic-management-center-client`) and use the corresponding environment variable (`EMERGENCY_VEHICLE_CLIENT_SECRET`, `MAYOR_VEHICLE_CLIENT_SECRET`, `OTHER_VEHICLE_CLIENT_SECRET`, `TRAFFIC_MANAGEMENT_CENTER_CLIENT_SECRET`).

**Note:** No secrets are stored in the code. `.env` file contains all client secrets.

This opens an interactive Quarkus Client, from which the public API Endpoints can be called. You are presented 5 Options:

```
=== Menu ===
1                                 → GET  /api/status/traffic-lights
2 <trafficLightId>                → GET  /api/status/traffic?traffic-light-id={UUID}
3 <trafficLightId> <vehicleId>    → POST /api/priority/requests Body: {"trafficLightId" : {UUID}, "vehicleId" : {UUID} , "vehicleType" : "emergency-vehicle"}
4 <requestId>                     → GET  /api/priority/requests/{requestId}
5                                 → View full Intersection Status (combines options 1 + 2; no separate endpoint)
6                                 → Run a demo of all endpoints with dummy data.
q                                 → Quit
```

An example call for the endpoint https://tcc.test/api/status/traffic?traffic-light-id={UUID} would be:

```
2 8d8d1437-907b-3a79-900a-c5f0ea1f5c73
```

**Note:** This calls the endpoint /api/status/traffic?traffic-light-id={UUID} with the UUID value `8d8d1437-907b-3a79-900a-c5f0ea1f5c73`

- The first 2 options are calls for the `tcc-status-service`
- The 3rd and 4th options are the endpoints of the `tcc-priority-service`
- The 5th option combines the first 2 endpoints to query the whole intersection state
- The 6th option runs a demo of all endpoints. Depending on the internal state, the output will differ
- The 7th option quits the client

## Why common-models?

The `common-models` module was created to centralize all data structures used across multiple microservices. This ensures consistency, reusability, and maintainability of core domain objects like GPS coordinates, traffic light states, and intersection representations. All microservices depend on this shared module to access validated and tested data structures.

## Data Structures

### TrafficLightId Class

This class identifies traffic lights uniquely. It uses GPS coordinates and validates locations within Duckburg city.

**Main Parts:**

- `GPSCoordinate latitude` - North/South position (degrees, minutes, seconds)
- `GPSCoordinate longitude` - East/West position (degrees, minutes, seconds)
- `UUID uuid` - Unique ID for each traffic light
- `String direction` - Direction like "north-south" or "east-west"

**GPS Rules:**

- **Duckburg Area:** Only allows coordinates within the city (latitude 37-38°N, longitude 120-122°W)
- **No Pacific Ocean:** Avoids 180°E/W coordinates as specified: "Please try to avoid placing Duckburg somewhere in the Pacific Ocean; this may cause problems at 180°E/W"
- **Valid Format:** Checks degrees/minutes/seconds are correct
- **Type Safety:** Makes sure latitude and longitude are not mixed up

**Important Methods:**

- Constructor checks GPS coordinates are in Duckburg
- `validateLocation()` - Re-checks coordinates after changes
- `equals()`/`hashCode()` - Uses UUID for comparisons

### TrafficStatus Class

This class shows the current state of a traffic light with time and pedestrian signals.

**Main Parts:**

- `TrafficLightId trafficLightId` - Which traffic light this is
- `Instant timestamp` - When this status was recorded
- `String state` - Light color ("red", "yellow", "green")
- `String pedestrianState` - Pedestrian light state

**State Rules:**

- **Red Light:** `pedestrianState = "green"` (pedestrians can walk)
- **Yellow Light:** `pedestrianState = "yellow"` (be careful)
- **Green Light:** `pedestrianState = "red"` (cars go, pedestrians wait)
- **Other States:** `pedestrianState = "unknown"` (safe default)

**Important Methods:**

- Constructor sets pedestrian state automatically
- `setState()` - Changes both car and pedestrian lights
- Handles errors safely

### GPSCoordinate Class

Helper class for GPS positions in DMS format (Degrees, Minutes, Seconds).

**References:**

- [Geographic Coordinate System](https://en.wikipedia.org/wiki/Geographic_coordinate_system)
- [World Geodetic System WGS84](https://en.wikipedia.org/wiki/World_Geodetic_System#WGS84)

**Main Parts:**

- `int degrees` - Degree part
- `int minutes` - Minute part (0-59)
- `double seconds` - Second part (0-59.999...)
- `boolean isLatitude` - True for north/south, false for east/west

**Rules:**

- **Latitude:** Must be -90° to 90°
- **Longitude:** Must be -180° to 180°
- **Format Check:** Minutes and seconds must be valid
- **Type Safety:** Prevents mixing latitude and longitude
- **Duckburg Protection:** Avoids Pacific Ocean coordinates (180°E/W)

**Important Methods:**

- `toDecimalDegrees()` - Converts to decimal format
- `fromDecimalDegrees()` - Creates from decimal numbers
- `toString()` - Shows coordinates like "37°30'15\"N"

### Intersection Decision

**Decision:** Use `List<TrafficStatus>` for all lights at an intersection.

**Why:**

- Each `TrafficStatus` has the traffic light ID, time, and state
- Works with any number of lights per intersection
- Easy to loop through and check all lights
- Supports different intersection types

## Testing Data Structures

### How to Run Tests

#### Run All Tests

```bash
# Run all unit tests
cd common-models

mvn test
```

#### Run Specific Tests

```bash
# Test specific class
cd common-models
mvn test -Dtest=TrafficLightIdTest

```

### Unit Tests Overview

| Test Class           | Number of Tests | What is Tested                      |
| -------------------- | --------------- | ----------------------------------- |
| `TrafficLightIdTest` | 18 tests        | GPS validation, Duckburg area, UUID |
| `TrafficStatusTest`  | 8 tests         | Light states, pedestrian logic      |
| `GPSCoordinateTest`  | 17 tests        | DMS format, conversion, ranges      |
| **Total**            | **43 tests**    | **Complete validation**             |

### Security Features

- **GPS Check:** Only allows traffic lights in Duckburg city area
- **UUID Check:** Each traffic light has unique ID
- **State Check:** Only allows valid light colors
- **Error Handling:** Prevents crashes from bad input

The data structures follow all task requirements and provide a solid base for the TCC traffic control system.

### Input Validation

We have implemented comprehensive input validation for public endpoints to ensure data integrity and security. The Priority Service validates vehicle requests with checks for vehicle types, IDs, and traffic light locations. The Status Service validates traffic light queries with proper UUID format checking. For detailed validation specifications and test cases, see [validation.md](validation.md).

# Implementation Details

----

## `traffic-light-devices`

This service was substantially refactored to meet the task requirements. Each traffic light is represented by a `TrafficStatus` object, which includes a `TrafficLightId` (see section above for details). The UUID for each light is derived deterministically from a fixed key, ensuring that the same traffic light receives the same identifier across different systems and test environments.

### Intersection-wide state changes

A state change requested for a single traffic light always applies to the entire intersection. The state transition is implemented as a three-step sequence:

1. **Initiate transition:** set all lights to **yellow**.
2. **Clearance phase:** wait **15 seconds** to allow the intersection to clear. We assume vehicles that previously had red remain stopped, and vehicles that previously had green stop once yellow is active.
3. **Swap phases:** flip each traffic light’s state:

   * **red → green**
   * **green → red**

In real-world traffic control, this logic is more nuanced (e.g., not all signals switch to yellow simultaneously). For this project, a simplified intersection model was chosen to keep the implementation deterministic and easier to test.

### Concurrency and request handling

Because the yellow phase lasts 15 seconds, a state-change request doesn’t return immediately—the client that initiates it has to wait until the transition is finished. While the intersection is in that transition, we reject any other state-change requests with **HTTP `409 Conflict`** (clients can just retry once the switch is done).

We do this to avoid overlapping transitions. If two state changes were accepted at the same time, the intersection could end up in an unsafe mix of signals. Status requests are still allowed during the transition and will simply report `"yellow"`.

### Restricted Traffic States

Regular state-change requests may only set the intersection to **red** or **green**. Only the `traffic-management-center-client` is allowed to set the intersection to **yellow**, using a dedicated endpoint implemented for testing purposes.

This endpoint must be used with extreme caution: forcing the intersection into a permanent yellow state blocks traffic flow. Once the intersection is explicitly set to yellow, only the `traffic-management-center-client` is permitted to restore normal operation. All other state-change requests are denied while the intersection is locked in this mode to prevent unsafe or inconsistent behavior.

### Vehicle vs. pedestrian signals

Each traffic light maintains two internal signal states:

* `state`: vehicle state
* `pedestrianState` : pedestrian state

These are always opposite (vehicle green ↔ pedestrian red, and vice versa), except during the **yellow** phase. This constraint is verified in unit tests.

Additionally, opposing directions always share the same phase:

* **north–south** matches **south–north**
* **west–east** matches **east–west**

---

## `tcc-status-service` — Implementation decisions

The `tcc-status-service` is one of the two externally accessible services and exposes the following endpoints:

* `/api/status/traffic-lights`
* `/api/status/traffic`

### `/api/status/traffic-lights` — intersection metadata

This endpoint provides static metadata describing the intersection, returning the `TrafficLightId` for each light. The UUIDs are generated deterministically, external clients will always receive the same identifiers and metadata across deployments.

Expected Traffic Light Metadata:

```bash
---------
Traffic Light UUID: 8d8d1437-907b-3a79-900a-c5f0ea1f5c73
Direction: north-south
Longitude Coordinate: 121°30'30"W
Latitude Coordinate: 37°30'31"N
---------
Traffic Light UUID: 50fd76e3-3fe5-3961-bc5c-a99008af8904
Direction: south-north
Longitude Coordinate: 121°30'30"W
Latitude Coordinate: 37°30'29"N
---------
Traffic Light UUID: da4f0053-a5c1-3882-a688-52ae2da2e466
Direction: west-east
Longitude Coordinate: 121°30'29"W
Latitude Coordinate: 37°30'30"N
---------
Traffic Light UUID: 320381db-f7cd-3f31-804b-aa6b36e1c682
Direction: east-west
Longitude Coordinate: 121°30'31"W
Latitude Coordinate: 37°30'30"N
---------
```

### Query model

Clients use this metadata to query the `TrafficStatus` of an individual traffic light. Since the intersection behaves as a coupled system (a change in one direction implies the corresponding states of the others), we intentionally implemented status queries per light. Retrieving the full intersection state can be performed by querying each traffic light individually; our external client includes a convenience function to aggregate these responses.

---

## `tcc-priority-service` — Implementation decisions

The priority service is responsible for handling priority-based traffic light changes (emergency vehicle & mayor-vehicle). It exposes two public endpoints:

* `POST /api/requests`
  Body:

  ```json
  { "trafficLightId": "<UUID>", "vehicleType": "<String>", "vehicleId": "<UUID>" }
  ```
* `GET /api/requests/{requestId}`

### `POST /api/requests` — submit a `PriorityRequest`

Submitting a priority request can lead to three outcomes:

1. **Accepted**
   If the intersection is currently in a **red** or **green** phase , the request is accepted.

   * If the requested traffic light is already green, the server accepts the request without changing the intersection state.
   * Otherwise, a state change is  triggered.

2. **Queued**
   If the intersection is currently in the **yellow** phase, the request is queued.

3. **Denied**
   Requests are rejected if the requester is not authorized or if the payload is invalid/malformed.

If a vehicle with the same `vehicleId` submits another request while it already has a queued request, the service rejects the new submission and returns the existing `requestId`.

### `GET /api/requests/{requestId}` — poll request status

Queued requests are tracked by polling. When querying a request status the following is possible:

1. **Accepted**
   If the traffic light is already green, the request can be accepted immediately.
   Otherwise the request is accepted if it has the highest priority.

   After a request is accepted, it is deleted from the server.

2. **Queued**
   The request stays queued if:

   * another request currently has higher priority, or
   * the requested traffic light is not green yet.

### Expiration / cleanup

To prevent stale requests from blocking the system, the service removes any `PriorityRequest` that has not been polled for 60 seconds. This ensures the queue cannot be clogged by abandoned requests.

### Priority order

Requests are stored in a `PriorityQueue` with a custom comparator:

* **Primary ordering:** `emergency-vehicle` > `mayor-vehicle`
* **Tie-breaker:** server-side timestamp (older requests first)

### Role validation

Clients include the `vehicleType` in the request body, which means it could be faked by simply sending a different value (e.g., claiming to be an emergency vehicle). To prevent this, the service compares the role/claims in the access token with the provided `vehicleType`. If they don’t match, the request is rejected.

Repeated mismatches are a strong indicator of misuse or a faulty client. These events can be logged and used to identify and block malicious actors. Automatic banning is not implemented yet, but the service is designed so it can be added later.


### Important limitation

The service cannot verify whether a vehicle actually passed the intersection after its request was accepted. Doing so would require verification of the vehicle location, which is out of scope of this task. For that reason, accepted requests are always removed immediately. If a vehicle misses its window (e.g., it gets delayed), it must submit a new `PriorityRequest` once it is ready to cross again.

---

## `traffic-management-center-client` — Implementation decisions

The `traffic-management-center-client` is an administrative client included as part of this project. It can access two management-only endpoints:

* `GET /api/state/management/state`
* `POST /api/state/management/change-state?traffic-light-id={UUID}`
  Body:

  ```json
  { "goalStatus": "<String>" }
  ```

### Query intersection state

`GET /api/state/management/state` returns the current state of the entire intersection. This endpoint exists only for the management client and is not available to regular external clients.

### Manual intersection control

The management client provides a dedicated endpoint to manually control the intersection state.
A request to `POST /api/state/management/change-state` applies a intersection-wide state change based on:

* `traffic-light-id`: id of the traffic light
* `goalStatus`: the target state (`green`, `red`, or `yellow`)

This is the only interface allowed to set all lights to yellow, and the only one allowed to restore the intersection back to normal operation after it was forced into yellow.

### Safety and consistency rules

For safety reasons, the management client follows the same transition-locking behavior as normal clients:

* If a normal client has already triggered a state transition (yellow phase), management requests are also denied until the transition completes.
* The management client cannot change a single traffic light in isolation. All state changes apply to the entire intersection to avoid inconsistent signal combinations and to keep opposing directions synchronized.

---
## `tcc-audit-service` — Implementation decisions

As part of this task, we added basic security auditing through the `tcc-audit-service`. While intentionally simple, it was already useful during development and debugging. Every request handled by `tcc-state-controller`, `tcc-status-service`, and `tcc-priority-service` results in an audit event that is sent to the audit service.

### Audit event endpoint

* `POST /api/audit/events`
  Body:

  ```json
  {
    "serviceType": "<String>",
    "timestamp": "<long>",
    "logMessage": "<String>"
  }
  ```

Each event uses a dedicated `logMessage` depending on what happened (e.g., request accepted/denied,validation failure etc,). This makes it possible to trace external client activity across services.

### Log retrieval (internal only)

Logs are only readable by internal services. They can be queried via:

* `GET /api/audit/logs?from=<long>&to=<long>`

To inspect logs manually, we use a Kubernetes test pod and authenticate as an authorized service. (`tcc-state-controller`, `tcc-status-service` and `tcc-priority-service`)

```bash
# Apply Kubernetes manifests for the test pod
kubectl apply -f kubernetes/testing-pod/gruppe8-testing-pod.yaml

# Create test pod and mount secrets (run from /task5/)
kubectl -n gruppe8-testing run tls-debug \
  --image=curlimages/curl:8.5.0 \
  --restart=Never \
  --overrides='
{
  "spec": {
    "containers": [{
      "name": "curl",
      "image": "curlimages/curl:8.5.0",
      "command": ["sh","-c","sleep 3600"],
      "stdin": true,
      "tty": true,
      "volumeMounts": [
        {"name":"certs","mountPath":"/etc/tls","readOnly":true}
      ]
    }],
    "volumes": [
      {"name":"certs","secret":{"secretName":"gruppe8-task4-testing-cert-tls"}}
    ]
  }
}'

# Exec into the test pod
kubectl -n gruppe8-testing exec -it tls-debug -- sh
```

Inside the pod, request an access token and fetch logs as `tcc-state-controller`:

```bash
TOKEN=$(curl -vk \
  --cacert /etc/tls/ca.crt \
  -d grant_type=client_credentials \
  -d client_id=tcc-state-controller \
  -d client_secret=MEb3nfjgTGkEHiZs8NugXxKTf2UqHdVC \
  https://keycloak-service.keycloak.svc.cluster.local:8443/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

echo "LEN=${#TOKEN}"  # if > 0, token retrieval worked

curl -v \
  --cacert /etc/tls/ca.crt \
  --cert /etc/tls/tls.crt \
  --key /etc/tls/tls.key \
  -H "Authorization: Bearer $TOKEN" \
  "https://gruppe8-tcc-audit-service.gruppe8-tcc.svc.cluster.local:443/api/audit/logs?from=0&to=9999999999999"
```

**Note:** The example query returns *all* logs. If the system has been running for a while, the response can become very large. Consider using a narrower `from`/`to` range when testing the endpoint.




## Deletion of `tcc-shared-services`

In the initial design, we planned two additional shared services: a **location validator** and a **time service**. During implementation, both were removed.

* **Location validator:** Verifying the physical location of vehicles would require additional assumptions and data sources and after feedback was determined to be outside the scope of this course project. As a result, the location validation service was deleted.

* **Time service:** This was originally intended to validate timestamps on priority requests. We later simplified the design by generating and attaching timestamps exclusively on the server side, rather than accepting them from clients. This prevents timestamp spoofing without requiring a separate service, so the time service was removed as well.


# Testing Priority Service

This part will explain how to test the priority service, in regards to managing the priority between mayor - and emergency-vehicles. 

### 0) Setup: Start all clients (in separate tabs)

➡️ Open **three separate tabs** and start one client in each:

- **State Traffic Management Center Client**
- **Mayor-Vehicle Client**
- **Emergency-Vehicle Client**

> IMPORTANT: Before executing any call from a different client, switch to that client's tab first.

---

### 1) Swap traffic state to `yellow` (State Traffic Management Center Client)

➡️ Switch to tab: **State Traffic Management Center Client**

Command:

```txt
2 8d8d1437-907b-3a79-900a-c5f0ea1f5c73 yellow
```

---

### 2) Send Mayor priority request (Mayor-Vehicle Client)

➡️ Switch to tab: **Mayor-Vehicle Client**

Command:

```txt
3 8d8d1437-907b-3a79-900a-c5f0ea1f5c73 18fa36b7-7e59-4c6a-955c-f8864c995823
```

---

### 3) Send Emergency Vehicle priority request (Emergency-Vehicle Client)

Goal: Verify that the emergency vehicle is prioritized over the mayor even though the emergency request is sent later.

➡️ Switch to tab: **Emergency-Vehicle Client**

Command:

```txt
3 8d8d1437-907b-3a79-900a-c5f0ea1f5c73 7de3d833-8468-4105-8435-f178b6ff279c
```

---

### 4) Set traffic light of the prio request to `red` to verify prio request logic (State Traffic Management Center Client)

➡️ Switch to tab: **State Traffic Management Center Client**

Command:

```txt
2 8d8d1437-907b-3a79-900a-c5f0ea1f5c73 red
```

---

### 5) Verify the Mayor request is NOT highest priority (still queued, not accepted)

Expectation: The mayor request should still be in the queue. If it were highest priority, it would be accepted.

➡️ Switch to tab: **Mayor-Vehicle Client**

Command:

```txt
4 <request id>
```

---

### 6) Verify the Emergency Vehicle request IS highest priority (accepted / state changes)

Expectation: By requesting status, you should see that the state changes and the emergency request is accepted.

➡️ Switch to tab: **Emergency-Vehicle Client**

Command:

```txt
4 <request id>
```

---

### 7) Verify the Mayor request is accepted afterwards

Expectation: After the EV request is accepted/handled, the mayor request should become accepted as well.

➡️ Switch to tab: **Mayor-Vehicle Client**

Command:

```txt
4 <request id>
```

---

## What happened and why it worked (expected behavior)

- **Priority requests are only queued while the traffic light is `yellow`. Otherwise requests are accepted immediately.**  
  That’s why we first switch the traffic state to `yellow` and then send both priority requests. This ensures both requests end up in the same queue.

- **We send the Mayor request first, then the Emergency Vehicle request.**  
  Even though the Mayor request is older, the priority queue rules should rank the Emergency Vehicle request higher once both are queued.

- **After switching the traffic light to `red`, we validate the queue order via status polling.**  
  When you request status (`4 <request id>`), the system will either:
  - **accept** the request is highest priority, or
  - respond that it is **queued**.

- **So the expected outcome is:**
  1. Polling the **Mayor** request first should **NOT** accept it → proves it is not highest priority.
  2. Polling the **Emergency Vehicle** request should **accept** it → proves it is highest priority.
  3. Polling the **Mayor** request again afterwards should then **accept** it → because the higher priority request has been handled.
