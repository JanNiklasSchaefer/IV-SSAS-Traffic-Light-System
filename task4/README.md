## Task 4 – Microservices, TLS, and Deployment Guide

### Overview

Task 4 focuses on deploying microservices to Kubernetes with TLS encryption for client-to-service communication. The implementation includes:

- **TLS encryption** for all client-facing services (Priority Service, Status Service)
- **Mutual TLS (mTLS)** between Emergency Vehicle Client and Priority Service for enhanced security
- **Kubernetes deployment** with automated certificate management using cert-manager
- **Ingress configuration** for external access to services via HTTPS

Service-to-service communication within the cluster uses HTTP and will be secured by Kubernetes/Service Mesh in future tasks.

### Repository Layout

```
task4/
├── services/                # Source & Docker context for each microservice
│   ├── tcc-priority-service/        # → gruppe8-tcc namespace (TLS + mTLS enabled)
│   ├── tcc-state-controller/         # → gruppe8-tcc namespace
│   ├── tcc-status-service/           # → gruppe8-tcc namespace (TLS enabled)
│   ├── tcc-audit-service/           # → gruppe8-tcc namespace
│   ├── traffic-light-device-service/ # → gruppe8-traffic-light-devices namespace
│   ├── time-service/                  # → gruppe8-shared-services namespace
│   └── location-validator/           # → gruppe8-shared-services namespace
├── clients/                 # Client apps hitting the REST endpoints (TLS enabled)
│   ├── emergency-vehicle-client/     # (mTLS enabled)
│   ├── mayor-vehicle-client/
│   ├── other-vehicle-client/
│   └── pedestrian-client/
├── kubernetes/              # Kubernetes deployment manifests
│   ├── ingress.yaml         # Manual Ingress configuration (TLS enabled)
│   ├── certificates/        # Certificate manifests for TLS
│   │   ├── tcc-priority-service-certificate.yaml
│   │   ├── tcc-status-service-certificate.yaml
│   │   ├── tcc-ingress-certificate.yaml
│   │   └── emergency-vehicle-client-certificate.yaml
│   ├── pki/                 # PKI from Task 1 (if not already deployed)
│   │   ├── ca-certificate.yaml
│   │   └── ca-issuer.yaml
│   ├── test-pod.yaml        # Test pod for cluster-internal testing
│   └── namespaces/          # Namespace definitions
│       └── gruppe8-tcc.yaml
├── scripts/                 # Helper scripts
│   ├── extract-ca-cert.sh   # Extract CA certificate for local client testing
│   └── extract-client-cert.sh # Extract client certificate for mTLS
├── test-endpoints.sh        # Comprehensive endpoint testing script
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
- **PKI from Task 1**: The Intermediate CA (`ha1-gruppe8-krzysztoflagowski-clusterissuer`) must be deployed in the cluster. If not already deployed, see "Deploy to Kubernetes" → Step 3 for instructions.
- **cert-manager** and **trust-manager** must be installed in the cluster

### Building the Project

The project can be built using Maven from the `task3/` directory.

#### Build and Test

To build all services and clients and run tests:

```bash
cd task4
mvn clean install
```

This will:

- Compile all services and clients
- Run tests (if any)
- Install artifacts to local Maven repository

#### Build with Container Images

To build all services and clients including Docker container images:

```bash
cd task4
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
task4/services/<service-name>/target/kubernetes/kubernetes.yml
```

To generate manifests without deploying:

```bash
cd task4
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
cd task4
mvn clean install

# Step 2: Create namespaces
# Important: Quarkus does NOT automatically create namespaces for security reasons.
kubectl apply -f kubernetes/namespaces/gruppe8-tcc.yaml

# Step 3: Deploy TLS certificates (Task 4)
# Certificates are created by cert-manager using the PKI from Task 1

# IMPORTANT: If the ClusterIssuer from Task 1 is not deployed, deploy it first:
kubectl apply -f kubernetes/pki/ca-certificate.yaml
kubectl wait --for=condition=Ready certificate ha1-gruppe8-krzysztoflagowski-ca -n cert-manager --timeout=60s
kubectl apply -f kubernetes/pki/ca-issuer.yaml
kubectl wait --for=condition=Ready clusterissuer ha1-gruppe8-krzysztoflagowski-clusterissuer --timeout=60s

# Check if ClusterIssuer exists (optional verification):
kubectl get clusterissuer ha1-gruppe8-krzysztoflagowski-clusterissuer

# Deploy Task 4 certificates (requires ClusterIssuer from Task 1)
kubectl apply -f kubernetes/certificates/tcc-priority-service-certificate.yaml
kubectl apply -f kubernetes/certificates/tcc-status-service-certificate.yaml
kubectl apply -f kubernetes/certificates/tcc-ingress-certificate.yaml
kubectl apply -f kubernetes/certificates/emergency-vehicle-client-certificate.yaml

# Wait for certificates to be issued
# Note: Wait for specific certificates (not --all, to avoid waiting for duplicate/old certificates)
kubectl wait --for=condition=Ready certificate task4-gruppe8-tcc-priority-service-cert -n gruppe8-tcc --timeout=60s
kubectl wait --for=condition=Ready certificate task4-gruppe8-tcc-status-service-cert -n gruppe8-tcc --timeout=60s
kubectl wait --for=condition=Ready certificate task4-gruppe8-tcc-ingress-cert -n gruppe8-tcc --timeout=60s
kubectl wait --for=condition=Ready certificate task4-gruppe8-emergency-vehicle-client-cert -n gruppe8-tcc --timeout=60s

# Verify all certificates are ready
kubectl get certificate -n gruppe8-tcc

# Step 4: Deploy Ingress (before building to avoid conflicts)
kubectl apply -f kubernetes/ingress.yaml

# Step 5: Build and deploy all services
mvn clean package -Dquarkus.kubernetes.deploy=true

# Step 6: Test endpoints (can also be used locally without deployment)
./test-endpoints.sh
```

**Ingress Configuration**

The shared Ingress exposes:

- `https://tcc.test/api/priority/*` → Priority Service (TLS enabled)
- `https://tcc.test/api/status/*` → Status Service (TLS enabled)

**Note:** All communication is now encrypted with TLS. HTTP requests are automatically redirected to HTTPS.

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

**Note:** Service-to-service communication currently uses HTTP. TLS for service-to-service communication will be handled by Kubernetes/Service Mesh (e.g., Istio, Linkerd) in future tasks, not at the application level.

### Using Clients with TLS (Task 4)

#### Setup Client Certificates {#setup-client-certificates}

**IMPORTANT: Do this BEFORE running clients locally!**

Before running clients locally, extract and configure certificates:

```bash
cd task4

# Extract CA certificate and convert to PKCS12 for all clients
./setup-certs-now.sh
```

This script:

- Extracts the CA certificate from Kubernetes
- Converts it to PKCS12 format
- Copies certificates to all client resources directories
- Extracts client certificate for Emergency Vehicle Client (mTLS)

**Alternative: Manual extraction using individual scripts**

If you need more control or want to extract certificates manually, you can use the individual scripts in the `scripts/` directory. **Note:** These scripts only extract certificates to the `certs/` directory - you'll need to manually copy them to client directories if needed.

```bash
# Extract CA certificate only (writes to certs/ca.crt)
./scripts/extract-ca-cert.sh

# Convert CA to PKCS12 (writes to certs/ca.p12)
./scripts/convert-ca-to-pkcs12.sh

# Extract client certificate for mTLS only (writes to certs/client.p12)
./scripts/extract-client-cert.sh

# Then manually copy to clients if needed:
# cp certs/ca.p12 clients/emergency-vehicle-client/src/main/resources/certs/
# cp certs/client.p12 clients/emergency-vehicle-client/src/main/resources/certs/
```

These scripts are useful if you only need specific certificates or want to customize the extraction process.

#### Run Clients

**Standard TLS clients** (Mayor, Other Vehicle, Pedestrian):

```bash
cd task4/clients/mayor-vehicle-client
mvn quarkus:dev -Dbase.url=https://tcc.test
```

**mTLS client** (Emergency Vehicle):

```bash
cd task4/clients/emergency-vehicle-client
mvn quarkus:dev -Dbase.url=https://tcc.test
```

**Note:** The `-Dbase.url=https://tcc.test` parameter is **required** to override the default `https://localhost:8443` from `application.properties`. Ensure `tcc.test` is in your `/etc/hosts` file pointing to `127.0.0.1`.

### Connecting Clients to Kubernetes Cluster

**Note:** This section provides additional details on connection options. Make sure you've completed [Setup Client Certificates](#setup-client-certificates) first!

If your services are deployed in Kubernetes, you have two options to connect clients:

**Option 1: Port-Forward (Alternative for local testing)**

```bash
# In a separate terminal, forward the Priority Service port
kubectl port-forward -n gruppe8-tcc service/gruppe8-tcc-priority-service 8080:80

# Then run the client with tcc.test (required even with port-forward)
cd task4/clients/emergency-vehicle-client
mvn quarkus:dev -Dbase.url=https://tcc.test
```

**Note:**

- **You still need `-Dbase.url=https://tcc.test` even with port-forward!** The port-forward forwards to the Ingress, which requires the `tcc.test` hostname and HTTPS.
- With port-forwarding, you can only access one service at a time. If you need both Priority and Status services, you'll need to set up port-forwarding for both services in separate terminals, or use the Ingress option below.

**Option 2: Use Ingress Hostname (Recommended - Production-like setup)**

**Important:** You must pass `-Dbase.url=https://tcc.test` when running the client, otherwise it will use the default `https://localhost:8443` and fail.

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

# Test the Ingress endpoint (HTTPS)
curl -k https://tcc.test/api/priority/requests -X POST -H "Content-Type: application/json" -d '{"vehicleType":"emergency"}'
```

Finally, run the client with the Ingress hostname:

```bash
cd task4/clients/emergency-vehicle-client
mvn quarkus:dev -Dbase.url=https://tcc.test
```

**Note:** The `-Dbase.url=https://tcc.test` parameter is **required** to override the default `https://localhost:8443` from `application.properties`. Without it, the client will try to connect to `localhost:8443` and fail.

**Advantages of Ingress:**

- Both Priority and Status services accessible via single hostname
- Path-based routing (`/api/priority/*` and `/api/status/*`)
- Production-like setup
- TLS encryption enabled

## TLS Configuration (Task 4)

All client-to-service communication is encrypted using TLS. The **Emergency Vehicle Client** uses **mutual TLS (mTLS)** when communicating with the **Priority Service**.

### Architecture Decisions

**TLS for Client-to-Service Communication:**

- **Decision:** TLS is enabled for all microservices that are accessed by clients (Priority Service, Status Service). Service-to-service communication within the cluster uses plain HTTP and will be secured by Kubernetes/Service Mesh in future tasks.
- **Rationale:** Ensures end-to-end encryption from clients through the Ingress to the services. Service-to-service TLS will be handled at the infrastructure level (e.g., Istio, Linkerd) rather than at the application level.
- **Implementation:** Services use TLS certificates issued by cert-manager, mounted as Kubernetes secrets, for client-facing endpoints only.

**Mutual TLS (mTLS):**

- **Decision:** mTLS is implemented between the Emergency Vehicle Client and the Priority Service.
- **Rationale:** Emergency vehicles require higher security due to their critical nature. mTLS provides mutual authentication, ensuring both client and server verify each other's identity.
- **Implementation:**
  - Priority Service requires client certificates (`quarkus.http.ssl.client-auth=required`)
  - Emergency Vehicle Client presents its client certificate during TLS handshake
  - Ingress Controller reuses the Emergency Vehicle Client certificate when forwarding requests to the Priority Service (both are client auth certificates)

**Health Checks:**

- **Decision:** Health checks run on a separate management port (9000) without mTLS.
- **Rationale:** Kubernetes health checks cannot provide client certificates. Using a separate HTTP port for health checks allows the service to remain healthy while enforcing mTLS on the main API port (8443).
- **Implementation:** Quarkus Management Interface enabled on port 9000 for health checks.

### Building and Deploying Services with TLS

Services are automatically configured with TLS when deployed to Kubernetes. TLS certificates are managed by cert-manager using the PKI from Task 1.

**Deploy certificates and services:**

```bash
cd task4

# Deploy TLS certificates (see "Deploy to Kubernetes" → Step 3)
kubectl apply -f kubernetes/certificates/
# Wait for specific certificates to be ready
kubectl wait --for=condition=Ready certificate task4-gruppe8-tcc-priority-service-cert task4-gruppe8-tcc-status-service-cert task4-gruppe8-tcc-ingress-cert task4-gruppe8-emergency-vehicle-client-cert -n gruppe8-tcc --timeout=60s

# Build and deploy services (TLS is automatically enabled)
mvn clean package -Dquarkus.kubernetes.deploy=true
```

**Note:** Services should NOT be run locally. They must be deployed to Kubernetes where TLS certificates are available.

**Certificate Overview:**

- `task4-gruppe8-tcc-priority-service-cert`: Server certificate for Priority Service (with mTLS enabled)
- `task4-gruppe8-tcc-status-service-cert`: Server certificate for Status Service
- `task4-gruppe8-tcc-ingress-cert`: Server certificate for Ingress (client-facing)
- `task4-gruppe8-emergency-vehicle-client-cert`: Client certificate for Emergency Vehicle Client (mTLS). Also reused by Ingress to authenticate to Priority Service.

### Technical Details

**Service Configuration:**

- **Priority Service:**
  - API port: 8443 (HTTPS with mTLS, `client-auth=required`)
  - Management port: 9000 (HTTP, for health checks without mTLS)
  - Trust store: `/etc/certs/trust/root-certs.pem` (CA bundle from trust-manager)
- **Status Service:**
  - API port: 8443 (HTTPS, standard TLS)
  - No mTLS required

**Ingress Configuration:**

- Client-facing: HTTPS on port 443 (certificate: `task4-gruppe8-tcc-ingress-tls`)
- Backend to Priority Service: HTTPS with mTLS (reuses Emergency Vehicle Client certificate: `task4-gruppe8-emergency-vehicle-client-tls`)
- Backend to Status Service: HTTPS (standard TLS)
- SSL verification disabled for backend connections (certificates are trusted within the cluster)
