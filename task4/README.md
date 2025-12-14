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

### Install Linkerd

The initial setup of Linkerd is needed to setup the Service Mesh and ensure mTLS between Services inside the cluster.

```
# Setting LINKERD2_VERSION sets the version to install.
# If unset, you'll get the latest available edge version.
export LINKERD2_VERSION=edge-25.10.7
curl --proto '=https' --tlsv1.2 -sSfL https://run.linkerd.io/install-edge | sh

# Add it to your path 
export PATH=$HOME/.linkerd2/bin:$PATH

# Install Gateway API on the Kubernetes Cluster:

kubectl apply -f https://github.com/kubernetes-sigs/gateway-api/releases/download/v1.4.0/standard-install.yaml

# Validate Kubernetes Cluster

linkerd check --pre

#Install Linkered onto cluster

linkerd install --crds | kubectl apply -f -

#Follow it with: 

linkerd install | kubectl apply -f -
```

After installing linkered to the cluster, it might take a few minutes for the pods to properly start. You can watch it with:

```
watch kubectl -n linkerd get pods
```

Once all Pods are ready you can check the installation with:

```
linkerd check
```

Depending on the version there might be some warnings, as long as it says "up-to-date" this can be ignored.
Example Warning:

```
‼ control plane is up-to-date
    is running version 25.10.7 but the latest edge version is 25.12.2
    see https://linkerd.io/2/checks/#l5d-version-control for hints
```

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

# Step 5: Build and deploy all services
mvn clean package -Dquarkus.kubernetes.deploy=true

# Step 6: Verify deployment (optional)
# See "Verify Deployment" section below for commands
```

**Ingress Configuration**

The shared Ingress exposes:

- `https://tcc.test/api/priority/*` → Priority Service (TLS enabled)
- `https://tcc.test/api/status/*` → Status Service (TLS enabled)

**Note:** All communication is now encrypted with TLS. HTTP requests are automatically redirected to HTTPS.

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

**Service-to-Service Communication**

Services communicate across namespaces using Kubernetes Service Discovery:

- Format: `http://<service-name>.<namespace>.svc.cluster.local:80`
- Example: `http://gruppe8-time-service.gruppe8-shared-services.svc.cluster.local:80`
- Example: `http://gruppe8-tcc-auth-service.gruppe8-auth-services.svc.cluster.local:80`

**Note:** Service-to-service communication currently uses HTTP. TLS for service-to-service communication will be handled by Kubernetes/Service Mesh (e.g., Istio, Linkerd) in future tasks, not at the application level.

### Configuring the Service Mesh

## Deploying the Service Mesh

```
# Deploy Service Mesh to keycloak namespace

kubectl get -n keycloak deploy -o yaml \
  | linkerd inject - \
  | kubectl apply -f -

# Deploy service mesh to gruppe8-tcc namespace

kubectl get -n gruppe8-tcc deploy -o yaml \
  | linkerd inject - \
  | kubectl apply -f -

# Deploy service mesh to gruppe8-traffic-light-devices namespace

kubectl get -n gruppe8-traffic-light-devices deploy -o yaml \
  | linkerd inject - \
  | kubectl apply -f -

# Deploy service mesh to gruppe8-traffic-light-devices namespace

kubectl get -n gruppe8-shared-services deploy -o yaml \
  | linkerd inject - \
  | kubectl apply -f -

# Deploy service mesh to ingress-nginx namespace

kubectl get -n ingress-nginx deploy -o yaml \
  | linkerd inject - \
  | kubectl apply -f -

```

### Validate Service Mesh 

Validate that mTls is activated for all pods:

```
linkerd -n default edges deployment
```

You should see the following as part of your output:

```
SRC           DST                                    SRC_NS        DST_NS                          SECURED          
prometheus    gruppe8-location-validator             linkerd-viz   gruppe8-shared-services         √  
prometheus    gruppe8-time-service                   linkerd-viz   gruppe8-shared-services         √  
prometheus    gruppe8-tcc-audit-service              linkerd-viz   gruppe8-tcc                     √  
prometheus    gruppe8-tcc-priority-service           linkerd-viz   gruppe8-tcc                     √  
prometheus    gruppe8-tcc-state-controller           linkerd-viz   gruppe8-tcc                     √  
prometheus    gruppe8-tcc-status-service             linkerd-viz   gruppe8-tcc                     √  
prometheus    gruppe8-traffic-light-device-service   linkerd-viz   gruppe8-traffic-light-devices   √  
prometheus    ingress-nginx-controller               linkerd-viz   ingress-nginx                   √  
prometheus    keycloak-operator                      linkerd-viz   keycloak                        √ 
```

### Testing and Client Setup (Task 4)

#### Test Service Endpoints

After deployment, test the services using the test script:

```bash
cd task4
./test-endpoints.sh
```

This script tests all service endpoints from within the cluster.

#### Setup Client Certificates {#setup-client-certificates}

**IMPORTANT: Do this BEFORE running clients locally!**

To run clients locally, extract and configure certificates:

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

**Note:** Make sure you've completed [Setup Client Certificates](#setup-client-certificates) first and added `tcc.test` to your `/etc/hosts` file (see below).

**Setup `/etc/hosts`:**

```bash
# On macOS/Linux, edit /etc/hosts (requires sudo)
sudo nano /etc/hosts
# Add this line:
127.0.0.1 tcc.test
```

**Note:** On Windows, edit `C:\Windows\System32\drivers\etc\hosts` as Administrator.

**Run clients:**

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

**Note:** The `-Dbase.url=https://tcc.test` parameter is **required** to override the default `https://localhost:8443` from `application.properties`.

**Alternative: Port-Forward (if Ingress is not available)**

If you prefer port-forwarding instead of Ingress:

```bash
# In a separate terminal, forward the Priority Service port
kubectl port-forward -n gruppe8-tcc service/gruppe8-tcc-priority-service 8080:80

# Then run the client (still requires tcc.test hostname)
cd task4/clients/emergency-vehicle-client
mvn quarkus:dev -Dbase.url=https://tcc.test
```

**Note:** Port-forwarding only allows access to one service at a time. Ingress (recommended) allows access to all services via a single hostname.

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
