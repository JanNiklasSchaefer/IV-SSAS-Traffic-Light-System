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
├── test-endpoints.sh        # Comprehensive endpoint testing script (AI-generated)
├── pom.xml                  # Parent POM for all modules
└── README.md                # You are here
```

### REST API Specification (Team Agreement)

Complete overview of all REST endpoints organized by service.

### REST API Specification Table View

Complete overview of all REST endpoints organized by service.

| Service                          | Type     | Method | Path                        | Purpose                               | Query Parameters             | Parameters / Request Body         | Response                                            | Communication                                        |
| -------------------------------- | -------- | ------ | --------------------------- | ------------------------------------- | ---------------------------- | --------------------------------- | --------------------------------------------------- | ---------------------------------------------------- |
| **TCC Priority Service**         | Internal | `POST` | `/api/state/change`         | Trigger state change                  |                              | `{"state_change_request": {...}}` | `{"status_code": {...}}`                            | Calls TCC State Controller                           |
|                                  | Internal | `POST` | `/api/audit/events`         | Log priority request                  | `{"audit_event" : {...} }`   |                                   | `{"status_code": {...}}`                            | Calls TCC Audit Service                              |
| **TCC Status Service**           | Internal | `GET`  | `/api/device/traffic-state` | Retrieve current state                | `{"vehicle" : Boolean }`     | -                                 | `{"traffic_status": {...}}`                         | Calls Traffic Light Device Service                   |
| **TCC State Controller**         | Internal | `POST` | `/api/state/change`         | Execute state change command          |                              | `{"traffic_status": {...}}`       | `{"status_code": {...}}`                            | Called by TCC Priority Service                       |
|                                  | Internal | `POST` | `/api/device/change-state`  | Apply state change to device          |                              | `{"traffic_status": {...}}`       | `{"status_code": {...}}`                            | Calls Traffic Light Device Service                   |
|                                  | Internal | `POST` | `/api/audit/events`         | Log state change                      |                              | `{"audit_event" : {...}}`         | `{"status_code": {...}}`                            | Calls TCC Audit Service                              |
| **TCC Auth Service**             | Internal | `POST` | `/api/auth/token`           | Handle Token Requests                 | `{"grant_type": String}`     | `{"token_request": {...}}`        | `{"refresh_token": String, "access_token": String}` | Called by all Services                               |
|                                  | Internal | `POST` | `/api/auth/introspect`      | Validate Auth Data                    | `{"access_token": String}`   |                                   | `{"status_code": {...}}`                            | Called by all Services                               |
|                                  | Internal | `POST` | `/api/auth/userinfo`        | Get User Info                         | `{"access_token": String}`   |                                   | `{"user_info": {...}}`                              | Called by all Services                               |
|                                  | External | -      | OIDC/OAuth2                 | Proxy to Keycloak                     |                              | OAuth2/OIDC protocol              | Keycloak tokens                                     | Calls Keycloak (Task 5)                              |
| **TCC Audit Service**            | Internal | `POST` | `/api/audit/events`         | Log audit event                       |                              | `{"audit_event" : {...}}`         | `{"status_code": {...}}`                            | Called by TCC Priority Service, TCC State Controller |
|                                  | Internal | `GET`  | `/api/audit/logs`           | Retrieve audit logs (stub for Task 3) | `{"from": long, "to": long}` |                                   | `{"audit_logs": {...}}`                             | TODO: Admin Service (Task 5)                         |
| **Traffic Light Device Service** | Internal | `GET`  | `/api/device/traffic-state` | Get current traffic light state       |                              |                                   | `{"traffic_status": {...}}`                         | Called by TCC Status Service                         |
|                                  | Internal | `POST` | `/api/device/change-state`  | Apply state change                    | `{"state": String}`          |                                   | `{"status_code": {...}}`                            | Called by TCC State Controller                       |
| **Location Validator Service**   | Internal | `POST` | `/api/location/vehicle`     | Validate vehicle location             | `{"vehicle_id": String}`     | `{"coordinates": {...}}`          | `{"status_code": {...}}`                            | Called by TCC Priority Service                       |
| **Time Service**                 | Internal | `POST` | `/api/time/validate`        | Validate timestamp                    | `{"timestamp": "..."}`       |                                   | `{"status_code": {...}}`                            | Called by TCC Priority Service                       |

**Legend:**

- **External:** Communication from clients outside the cluster
- **Internal:** Service-to-service communication within the cluster
- **Calls:** This service initiates the call to another service
- **Called by:** This service receives calls from another service

**Additional Note:**

Most Endpoints are currently missing their authorization and authentication headers. These will be added in a later iteration when keycloak will be deployed.

### Prerequisites

Download the IV-SSAS-Kind-Cluster. Follow the Instructions there to install and start it : https://git.tu-berlin.de/aot-security/iv-software-security-for-autonomous-systems/examples/iv-ssas-kind-cluster

The Cluster must be running on your device before you execute the next steps.

Additionally you need:

- Docker Desktop or Docker Engine running
- Local Docker registry (optional, for pushing images)

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

### Connecting Clients to Kubernetes Cluster

If your services are deployed in Kubernetes, you have two options to connect clients:

**Option 1: Port-Forward (Alternative for local testing)**

```bash
# In a separate terminal, forward the Priority Service port
kubectl port-forward -n gruppe8-tcc service/gruppe8-tcc-priority-service 8080:80

# Then run the client with tcc.test (required even with port-forward)
cd task4/clients/emergency-vehicle-client
mvn quarkus:dev -Dbase.url=http://tcc.test
```

**Note:**

- **You still need `-Dbase.url=http://tcc.test` even with port-forward!** The port-forward forwards to the Ingress, which requires the `tcc.test` hostname.
- With port-forwarding, you can only access one service at a time. If you need both Priority and Status services, you'll need to set up port-forwarding for both services in separate terminals, or use the Ingress option below.

**Option 2: Use Ingress Hostname (Recommended - Production-like setup)**

**Important:** You must pass `-Dbase.url=http://tcc.test` when running the client, otherwise it will use the default `http://localhost:8080` and fail with a 404 error.

First, add `tcc.test` to your `/etc/hosts` file:

```bash
# On macOS/Linux, edit /etc/hosts (requires sudo)
sudo nano /etc/hosts
# or
sudo vi /etc/hosts

# Add this line:
127.0.0.1 tcc.test
```

**Note:** On Windows, edit `C:\Windows\System32\drivers\etc\hosts` as Administrator.

Then verify the Ingress is working:

```bash
# Check if Ingress is configured
kubectl get ingress -n gruppe8-tcc

# Test the Ingress endpoint
curl http://tcc.test/api/priority/requests -X POST -H "Content-Type: application/json" -d '{"vehicleType":"emergency"}'
```

Finally, run the client with the Ingress hostname:

```bash
cd task4/clients/emergency-vehicle-client
mvn quarkus:dev -Dbase.url=http://tcc.test
```

**Note:** The `-Dbase.url=http://tcc.test` parameter is **required** to override the default `http://localhost:8080` from `application.properties`. Without it, the client will try to connect to `localhost:8080` and fail.

**Advantages of Ingress:**

- Both Priority and Status services accessible via single hostname
- Path-based routing (`/api/priority/*` and `/api/status/*`)
- Production-like setup
