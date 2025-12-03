## Task 3 – Microservices, Clients, and Deployment Guide

This folder contains the working artifacts for Task 3 of the SAM traffic-light assignment.  
Goal: deliver runnable microservices, Kubernetes manifests, and basic clients (even with stubbed logic) that exercise the REST API defined in Task 2, plus documentation and a release package.

### Repository Layout

```
task3/
├── services/                # Source & Docker context for each microservice
│   ├── tcc-priority-service/        # → gruppe8-tcc namespace
│   ├── tcc-state-controller/         # → gruppe8-tcc namespace
│   ├── tcc-status-service/           # → gruppe8-tcc namespace
│   ├── auth-service/                 # → gruppe8-auth-services namespace
│   ├── tcc-audit-service/           # → gruppe8-tcc namespace
│   ├── traffic-light-device-service/ # → gruppe8-traffic-light-devices namespace
│   ├── time-service/                  # → gruppe8-shared-services namespace
│   └── location-validator/           # → gruppe8-shared-services namespace
├── clients/                 # Client apps hitting the REST endpoints
│   ├── emergency-vehicle-client/
│   ├── mayor-vehicle-client/
│   ├── other-vehicle-client/
│   └── pedestrian-client/
├── kubernetes/              # Kubernetes deployment manifests
│   ├── ingress.yaml         # Manual Ingress configuration
│   ├── test-pod.yaml        # Test pod for cluster-internal testing
│   └── namespaces/          # Namespace definitions
│       └── gruppe8-tcc.yaml
├── test-endpoints.sh        # Comprehensive endpoint testing script
├── pom.xml                  # Parent POM for all modules
└── README.md                # You are here
```

### REST API Specification (Team Agreement)

Complete overview of all REST endpoints organized by service.

### REST API Specification Table View

Complete overview of all REST endpoints organized by service.

| Service | Type | Method | Path | Purpose | Query Parameters | Parameters / Request Body | Response | Communication |
|---------|------|--------|------|---------|------------------|---------------------------|----------|---------------|
| **Clients (outside cluster)** | External | `GET` | `/api/status/traffic` | Get Traffic Status | `{"vehicle": Boolean}` |  | `{"traffic_status": {...}}` | Vehicle Clients → Ingress → TCC Status Service |
|  | External | `POST` | `/api/priority/requests` | Post Priority Request |  | `{"priority_request": {...}}` | `{"status": String}` | Vehicle Clients → Ingress → TCC Priority Service |
|  | External | `GET` | `/api/priority/requests/{request_id}` | Get Request Status | `{"request_id": String}`  | | `{"request_status": {...}}` | Vehicle Clients → Ingress → TCC Priority Service |
|  |  |  |  |  |  | Note: Each client wraps user input and calls these public endpoints via Ingress hostname |  |  |
| **TCC Priority Service** | Internal | `POST` | `/api/state/change` | Trigger state change |  | `{"state_change_request": {...}}` | `{"status_code": {...}}` | Calls TCC State Controller |
|  | Internal | `POST` | `/api/audit/events` | Log priority request | `{"audit_event" : {...} }` |  | `{"status_code": {...}}` | Calls TCC Audit Service |
| **TCC Status Service** | Internal | `GET` | `/api/device/traffic-state` | Retrieve current state | `{"vehicle" : Boolean }`  | - | `{"traffic_status": {...}}` | Calls Traffic Light Device Service |
| **TCC State Controller** | Internal | `POST` | `/api/state/change` | Execute state change command |  | `{"traffic_status": {...}}` | `{"status_code": {...}}` | Called by TCC Priority Service |
|  | Internal | `POST` | `/api/device/change-state` | Apply state change to device |  | `{"traffic_status": {...}}` | `{"status_code": {...}}` | Calls Traffic Light Device Service |
|  | Internal | `POST` | `/api/audit/events` | Log state change |  | `{"audit_event" : {...}}` | `{"status_code": {...}}` | Calls TCC Audit Service |
| **TCC Auth Service** | Internal | `POST` | `/api/auth/token` | Handle Token Requests | `{"grant_type": String}` | `{"token_request": {...}}` | `{"refresh_token": String, "access_token": String}` | Called by all Services |
|  | Internal | `POST` | `/api/auth/introspect` | Validate Auth Data | `{"access_token": String}` |  | `{"status_code": {...}}` | Called by all Services |
|  | Internal | `POST` | `/api/auth/userinfo` | Get User Info | `{"access_token": String}` |  | `{"user_info": {...}}` | Called by all Services |
|  | External | - | OIDC/OAuth2 | Proxy to Keycloak |  | OAuth2/OIDC protocol | Keycloak tokens | Calls Keycloak (Task 5) |
| **TCC Audit Service** | Internal | `POST` | `/api/audit/events` | Log audit event |  | `{"audit_event" : {...}}` | `{"status_code": {...}}` | Called by TCC Priority Service, TCC State Controller |
|  | Internal | `GET` | `/api/audit/logs` | Retrieve audit logs (stub for Task 3) | `{"from": long, "to": long}` |  | `{"audit_logs": {...}}` | TODO: Admin Service (Task 5) |
| **Traffic Light Device Service** | Internal | `GET` | `/api/device/traffic-state` | Get current traffic light state |  |  | `{"traffic_status": {...}}` | Called by TCC Status Service |
|  | Internal | `POST` | `/api/device/change-state` | Apply state change | `{"state": String}` |  | `{"status_code": {...}}` | Called by TCC State Controller |
| **Location Validator Service** | Internal | `POST` | `/api/location/vehicle` | Validate vehicle location | `{"vehicle_id": String}` | `{"coordinates": {...}}` | `{"status_code": {...}}` | Called by TCC Priority Service |
| **Time Service** | Internal | `POST` | `/api/time/validate` | Validate timestamp | `{"timestamp": "..."}`  | | `{"status_code": {...}}` | Called by TCC Priority Service |

**Legend:**

- **External:** Communication from clients outside the cluster
- **Internal:** Service-to-service communication within the cluster
- **Calls:** This service initiates the call to another service
- **Called by:** This service receives calls from another service

### Building the Project

The project can be built using Maven from the `task3/` directory.

#### Build and Test

To build all services and clients and run tests:

```bash
cd task3
mvn clean install
```

This will:

- Compile all services and clients
- Run tests (if any)
- Install artifacts to local Maven repository

#### Build with Container Images

To build all services and clients including Docker container images:

**Prerequisites:**

- Docker Desktop or Docker Engine running
- Local Docker registry (optional, for pushing images)

```bash
cd task3
mvn clean package
```

This will:

- Compile all services and clients
- Build Docker container images for each service
- Images are built using Jib and stored locally
- Container images are tagged for the local registry (`localhost:5001`)

**Note:** If Docker is not available, use `mvn clean package -Dquarkus.container-image.build=false` to skip container image building.

### Kubernetes Deployment

#### Prerequisites

- Kubernetes cluster running (e.g., kind, minikube, or cloud provider)
- `kubectl` configured to connect to your cluster
- Docker registry accessible from the cluster (or use local registry with kind)

#### Generate Kubernetes Manifests

Kubernetes manifests are automatically generated during the build process. They can be found in:

```
task3/services/<service-name>/target/kubernetes/kubernetes.yml
```

To generate manifests without deploying:

```bash
cd task3
mvn clean package -Dquarkus.kubernetes.deploy=false
```

#### Deploy to Kubernetes

**Namespace Structure**

Services are organized into multiple namespaces for better separation:

- **`gruppe8-tcc`**: TCC core services (Priority, State Controller, Status, Audit)
- **`gruppe8-auth-services`**: Auth Service
- **`gruppe8-traffic-light-devices`**: Traffic Light Device Service
- **`gruppe8-shared-services`**: Shared services (Time Service, Location Validator)
- **`ingress-nginx`**: Ingress controller (managed by cluster admin)

**Deployment Steps**

Follow these steps in order:

```bash
# Step 1: Build and test (optional, but recommended)
cd task3
mvn clean install

# Step 2: Create namespaces
# Important: Quarkus does NOT automatically create namespaces for security reasons.
kubectl apply -f kubernetes/namespaces/gruppe8-tcc.yaml

# Step 3: Deploy Ingress (before building to avoid conflicts)
kubectl apply -f kubernetes/ingress.yaml

# Step 4: Build and deploy all services
mvn clean package -Dquarkus.kubernetes.deploy=true

# Step 5: Test endpoints (can also be used locally without deployment)
./test-endpoints.sh
```

**Ingress Configuration**

The shared Ingress exposes:

- `http://tcc.test/api/priority/*` → Priority Service
- `http://tcc.test/api/status/*` → Status Service

**Verify Deployment and Test**

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

# Run test script
./test-endpoints.sh
```

**Service-to-Service Communication**

Services communicate across namespaces using Kubernetes Service Discovery:

- Format: `http://<service-name>.<namespace>.svc.cluster.local:80`
- Example: `http://gruppe8-time-service.gruppe8-shared-services.svc.cluster.local:80`
- Example: `http://gruppe8-tcc-auth-service.gruppe8-auth-services.svc.cluster.local:80`

### Running Clients

The client applications are simple Java programs that call the public REST API of the TCC system.  
For development, you can run them **locally** against Quarkus services started in **dev mode**, without deploying the entire system to Kubernetes.

The workflow is always:

1. **Start the backend service** you want to call (usually the TCC Priority Service or TCC Status Service).  
2. **Run one of the clients** in a separate terminal, which will send REST requests to `http://localhost:8080`.

---

## 1. Start the TCC Priority Service (Backend)

Open **Terminal 1**, go to the service directory, and run it in Quarkus dev mode:

```bash
cd task3/services/tcc-priority-service
mvn compile quarkus:dev
```

This launches the Priority Service on: http://localhost:8080

Leave this terminal running.

---

## 2. Run the Emergency Vehicle Client
Open Terminal 2 and run the Emergency Vehicle Client:

```bash
cd task3/clients/emergency-vehicle-client
mvn compile exec:java -Dexec.mainClass="de.tub.aot.client.EmergencyVehicleClient"
```
The client will start and interactively ask for any necessary input before sending a priority request to the running Priority Service.

---

## 3. Run Other Clients
Each client runs the same way, enter its folder and execute it:

### Mayor Vehicle Client
```bash
cd task3/clients/mayor-vehicle-client
mvn compile exec:java -Dexec.mainClass="de.tub.aot.client.MayorVehicleClient"
```

### Other Vehicle Client
```bash
cd task3/clients/other-vehicle-client
mvn compile exec:java -Dexec.mainClass="de.tub.aot.client.OtherVehicleClient"

```

### Pedestrian Client
```bash
cd task3/clients/pedestrian-client
mvn compile exec:java -Dexec.mainClass="de.tub.aot.client.PedestrianClient"

```

---

## Notes & Tips

- All clients call the public endpoints exposed by the TCC Priority or Status Service.
- When running locally (without Kubernetes), ensure the service they depend on is running in Quarkus dev mode.
- If you want to test everything inside Kubernetes instead, use the Ingress and your test script (./test-endpoints.sh).

