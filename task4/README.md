## Task 4 – Microservices, TLS, and Deployment Guide

### Overview

Task 4 focuses on deploying microservices to Kubernetes with comprehensive TLS encryption. The implementation includes:

- **TLS encryption** for all client-facing services (Priority Service, Status Service)
- **Mutual TLS (mTLS)** between Emergency Vehicle Client and Priority Service for enhanced security
- **Kubernetes deployment** with automated certificate management using cert-manager
- **Ingress configuration** for external access to services via HTTPS

Service-to-service communication within the cluster uses HTTPS with mutual TLS (mTLS) encryption.

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

| Service                          | Type     | Method | Path                        | Purpose                               | Query Parameters             | Parameters / Request Body                      | Response                    | Communication                                        |
| -------------------------------- | -------- | ------ | --------------------------- | ------------------------------------- | ---------------------------- | ---------------------------------------------- | --------------------------- | ---------------------------------------------------- |
| **TCC Priority Service**         | Internal | `POST` | `/api/state/change`         | Trigger state change                  |                              | `{"state_change_request": {...}}`              | `{"status_code": {...}}`    | Calls TCC State Controller                           |
|                                  | Internal | `POST` | `/api/audit/events`         | Log priority request                  | `{"audit_event" : {...} }`   |                                                | `{"status_code": {...}}`    | Calls TCC Audit Service                              |
| **TCC Status Service**           | Internal | `GET`  | `/api/device/traffic-state` | Retrieve current state                | `{"vehicle" : Boolean }`     | -                                              | `{"traffic_status": {...}}` | Calls Traffic Light Device Service                   |
| **TCC State Controller**         | Internal | `POST` | `/api/state/change`         | Execute state change command          |                              | `{"traffic_status": {...}}`                    | `{"status_code": {...}}`    | Called by TCC Priority Service                       |
|                                  | Internal | `POST` | `/api/device/change-state`  | Apply state change to device          |                              | `{"traffic_status": {...}}`                    | `{"status_code": {...}}`    | Calls Traffic Light Device Service                   |
|                                  | Internal | `POST` | `/api/audit/events`         | Log state change                      |                              | `{"audit_event" : {...}}`                      | `{"status_code": {...}}`    | Calls TCC Audit Service                              |
| **TCC Audit Service**            | Internal | `POST` | `/api/audit/events`         | Log audit event                       |                              | `{"audit_event" : {...}}`                      | `{"status_code": {...}}`    | Called by TCC Priority Service, TCC State Controller |
|                                  | Internal | `GET`  | `/api/audit/logs`           | Retrieve audit logs (stub for Task 3) | `{"from": long, "to": long}` |                                                | `{"audit_logs": {...}}`     | TODO: Admin Service (Task 5)                         |
| **Traffic Light Device Service** | Internal | `GET`  | `/api/device/traffic-state` | Get current traffic light state       |                              |                                                | `{"traffic_status": {...}}` | Called by TCC Status Service                         |
|                                  | Internal | `POST` | `/api/device/change-state`  | Apply state change                    | `{"state": String}`          |                                                | `{"status_code": {...}}`    | Called by TCC State Controller                       |
| **Location Validator Service**   | Internal | `POST` | `/api/location/vehicle`     | Validate vehicle location             |                              | `{"vehicle_id": String, "coordinates": {...}}` | `{"status_code": {...}}`    | Called by TCC Priority Service                       |
| **Time Service**                 | Internal | `POST` | `/api/time/validate`        | Validate timestamp                    |                              | `{"timestamp": "..."}`                         | `{"status_code": {...}}`    | Called by TCC Priority Service                       |

**Legend API Calls:**

- **External:** Communication from clients outside the cluster
- **Internal:** Service-to-service communication within the cluster
- **Calls:** This service initiates the call to another service
- **Called by:** This service receives calls from another service

**Note**: Authentication headers are missing, but are required.

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

# Step 5: Apply Network Policies
kubectl apply -f kubernetes/network-policies

# Step 6: Build and deploy all services
mvn clean package -Dquarkus.kubernetes.deploy=true

# Step 7: Verify deployment (optional)
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

# Setting up External and Internal TLS

## Internal TLS

**Service-to-Service Endpoints**

Services communicate across namespaces using Kubernetes Service Discovery:

- Format: `https://<service-name>.<namespace>.svc.cluster.local:8843`
- Example: `https://gruppe8-time-service.gruppe8-shared-services.svc.cluster.local:8443`
- Example: `https://gruppe8-tcc-auth-service.gruppe8-auth-services.svc.cluster.local:8843`

All communication inside the cluster is encrypted using mTLS. For this we utilize Cert Manager certificates and the Quarkus TLS registry. This is done automatically while deploying.

Traffic on the service port 443 is HTTPS-only, plain HTTP will be denied. The management endpoints, listen on IP 0.0.0.0 with Port 9000, with HTTPS. These Endpoints can not be accessed from outside the pod.

All communication between pods additionally requires HTTPS due to the network policies.

Overall this ensures that all communication inside the cluster is done encrypted. This prevents adversaries from listening to the network or trying to access the network via HTTP. An Adversary would need to break the authentication or get a copy of a secret in order to access any data inside the cluster.

#### Creating a Test Pod:

In order to test internal TLS we must call the Services from inside the cluster. A testing namespace and certificate must be created and then a testing pod can be used to test all the endpoints.

It is assumed directory $/task4/ is open.

**Step 1** Create a namespace and apply the certificate:

```
kubectl apply -f kubernetes/testing-pod/
```

**Step 2** Ceate a Test Pod with the testing certificate mounted inside the testing namespace.

```
# Create Testing Pod with name tls-debug
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
        {"name":"certs","mountPath":"/certs","readOnly":true}
      ]
    }],
    "volumes": [
      {"name":"certs","secret":{"secretName":"gruppe8-task4-testing-cert-tls"}}
    ]
  }
}'
```

**Step 3** Open a shell inside the testing pod:

```
kubectl -n gruppe8-testing exec -it tls-debug -- sh
```

**Note** If the pod stays open for a longer time, the pod might become completed. In that case simply delete the pod and repeat Step 2 to create a new testing pod:

```
kubectl delete pod tls-debug -n gruppe8-testing
```

**Step 4** Test the endpoints and verify HTTPS is working. Example Calls for all Services can be found in /task4/endpoint-calls.md. One example of those is:

```
curl -v \
  --cacert /certs/ca.crt \
  --cert /certs/tls.crt \
  --key /certs/tls.key \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{"timestamp":1}' \
  "https://gruppe8-time-service.gruppe8-shared-services.svc.cluster.local:443/api/time/validate"
```

The same endpoint with HTTP will not work, showing that TLS is required:

```
curl -v \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{"timestamp":1}' \
  "http://gruppe8-time-service.gruppe8-shared-services.svc.cluster.local:443/api/time/validate"
```

**Note** Most Endpoints still have hard coded answers, functionality will be added in a later task.

## External TLS

**Why External TLS?** Clients running outside the Kubernetes cluster need to authenticate and encrypt their communication with the services. This setup enables TLS encryption for client-to-service communication, with mTLS specifically for the Emergency Vehicle Client.

**How External TLS works:**

**Client-to-Service Endpoints:**

- **Ingress URL:** `https://tcc.test` (TLS termination point)
- **Routes:**
  - `https://tcc.test/api/priority/*` → Priority Service (TLS + mTLS for Emergency)
  - `https://tcc.test/api/status/*` → Status Service (TLS)
- **Certificate Storage:** Client certificates stored as Kubernetes secrets, extracted locally via scripts
- **Authentication:**
  - **Emergency Vehicle:** mTLS (client cert + server cert)
  - **Other Clients:** Standard TLS (server cert only)
- **Local Setup:** Certificates extracted to `task4/certs/` and copied to client resources

#### Setup Client Certificates {#setup-client-certificates}

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

#### Run Clients (External TLS Setup)

This section explains how to run clients locally that communicate with Kubernetes-deployed services using TLS encryption.

**Prerequisites:**

- Services must be deployed to Kubernetes (see "Deploy to Kubernetes" section)
- Client certificates must be extracted ([Setup Client Certificates](#setup-client-certificates))
- `tcc.test` must be added to your `/etc/hosts` file (see below)

**Note:** Clients run locally (outside Kubernetes) and establish TLS/mTLS connections to services running in the cluster.

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
cd clients/mayor-vehicle-client
mvn quarkus:dev -Dbase.url=https://tcc.test
```

**Example successful output:**

```
=== Mayor Vehicle Client ===
Using Quarkus REST Client

--- GET /api/status/traffic?vehicle=true ---
Status: 200 OK
State: green
Timestamp: 2024-01-01T12:00:00Z

--- POST /api/priority/requests ---
Request: mayor
Status: 200 OK
Response status: accepted
Request ID: 12eee0e1-7215-43ed-9852-5ca9be0d7600

--- GET /api/priority/requests/{requestId} ---
Using dummy requestId: 123e4567-e89b-12d3-a456-426614174000
Status: 200 OK
```

Additionally you can confirm that the endpoints are only reachable via HTTP by doing a simple curl request to the exposed Ingres endpoints:

```
curl -v \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{"timestamp":1}' \
  "http://tcc.test/api/time/validate"
```

**Example successful output:**

```
* Host tcc.test:80 was resolved.
* IPv6: (none)
* IPv4: 127.0.0.1
*   Trying 127.0.0.1:80...
* Connected to tcc.test (127.0.0.1) port 80
> POST /api/time/validate HTTP/1.1
> Host: tcc.test
> User-Agent: curl/8.5.0
> Content-Type: application/json
> Accept: application/json
> Content-Length: 15
> 
< HTTP/1.1 308 Permanent Redirect
< Date: Thu, 18 Dec 2025 02:06:27 GMT
< Content-Type: text/html
< Content-Length: 164
< Connection: keep-alive
< Location: https://tcc.test/api/time/validate
< 
<html>
<head><title>308 Permanent Redirect</title></head>
<body>
<center><h1>308 Permanent Redirect</h1></center>
<hr><center>nginx</center>
</body>
</html>
* Connection #0 to host tcc.test left intact
```

**mTLS client** (Emergency Vehicle):

```bash
cd clients/emergency-vehicle-client
mvn quarkus:dev -Dbase.url=https://tcc.test
```

**Example successful output (mTLS):**

```
=== Emergency Vehicle Client ===
Using Quarkus REST Client

--- GET /api/status/traffic?vehicle=true ---
Status: 200 OK
State: green
Timestamp: 2024-01-01T12:00:00Z

--- POST /api/priority/requests ---
Request: emergency
Status: 200 OK
Response status: accepted
Request ID: 2fce1ab1-9a1a-4bb8-88e1-9b9faf1ff44f

--- GET /api/priority/requests/{requestId} ---
Using dummy requestId: 123e4567-e89b-12d3-a456-426614174000
Status: 200 OK
Request ID: 123e4567-e89b-12d3-a456-426614174000
Status: NOT_FOUND
Vehicle Type: null
=== Demo finished ===
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

## TLS Configuration

All client-to-service communication is encrypted using TLS. The **Emergency Vehicle Client** uses **mutual TLS (mTLS)** when communicating with the **Priority Service**.

### Architecture Decisions

**TLS for Client-to-Service Communication:**

- **Decision:** TLS is enabled for all microservices that are accessed by clients (Priority Service, Status Service). Service-to-service communication within the cluster uses HTTPS with mutual TLS (mTLS) encryption.
- **Rationale:** Ensures end-to-end encryption from clients through the Ingress to the services. Service-to-service communication is secured at the application level using mTLS.
- **Implementation:** Services use TLS certificates issued by cert-manager, mounted as Kubernetes secrets, for both client-facing and internal service-to-service communication.

**Mutual TLS (mTLS):**

- **Decision:** mTLS is implemented between the Emergency Vehicle Client and the Priority Service.
- **Rationale:** Emergency vehicles require higher security due to their critical nature. mTLS provides mutual authentication, ensuring both client and server verify each other's identity.
- **Implementation:**
  - Priority Service requires client certificates (`quarkus.http.ssl.client-auth=required`)
  - Emergency Vehicle Client presents its client certificate during TLS handshake
  - Ingress Controller reuses the Emergency Vehicle Client certificate when forwarding requests to the Priority Service (both are client auth certificates)

**Health Checks:**

- **Decision:** Health checks run on a separate management port (9000) with HTTPS but without mTLS.
- **Rationale:** Kubernetes health checks cannot provide client certificates. Using a separate HTTPS port for health checks allows the service to remain healthy while enforcing mTLS on the main API port (8443).
- **Implementation:** Quarkus Management Interface enabled on port 9000 for health checks.

### Technical Details

**Service Configuration:**

- **Priority Service:**
  - API port: 8443 (HTTPS with mTLS, `client-auth=required`)
  - Management port: 9000 (HTTPS, for health checks)
  - Trust store: `/etc/certs/trust/root-certs.pem` (CA bundle from trust-manager)
- **Status Service:**
  - API port: 8443 (HTTPS, standard TLS)
  - No mTLS required

**Ingress Configuration:**

- Client-facing: HTTPS on port 443 (certificate: `task4-gruppe8-tcc-ingress-tls`)
- Backend to Priority Service: HTTPS with mTLS (reuses Emergency Vehicle Client certificate: `task4-gruppe8-emergency-vehicle-client-tls`)
- Backend to Status Service: HTTPS



## UPDATE Cluster DNS so keycloak.test resolves internally makes mapping way easier

Find your Keycloak service ClusterIP:

```
kubectl -n keycloak get svc keycloak-service -o wide
```

Patch CoreDNS ConfigMap to map keycloak.test → that IP. (This opens a vim instance of the file to edit)

```
kubectl -n kube-system edit configmap coredns
```

Inside the Corefile, add a hosts block above forward:

```
hosts {
  10.96.190.102 keycloak.test
  fallthrough
}
```

Restart CoreDNS:

```
kubectl -n kube-system rollout restart deploy/coredns
```

# TODO'S (ranked after priority somewhat)

- ~~keycloak service to service for all services~~
- ~~set up keycloak so its redeployable across systems~~
- setup external client connection to public ingress url of keycloak
- create yaml for all secrets so they are not plaintext
- look at keycloak via https 
- check and implement for "interactive client" (see feedback last task)
- unify role names (currently, some are service some are role. I fucked up here)
- make all folders 1 to task5 i mean we have releases anyway and could convert back if we need.

zu klären wieso:

# Time Service REST Client (internal service-to-service with mTLS)
de.tub.aot.tcc.priority.TimeServiceClient/mp-rest/url=https://gruppe8-time-service.gruppe8-shared-services.svc.cluster.local:443
de.tub.aot.tcc.priority.TimeServiceClient/mp-rest/trust-store=tls
de.tub.aot.tcc.priority.TimeServiceClient/mp-rest/key-store=tls

in tcc.priority.services ist 


-FIX 500 error when calling priority service and we forward to endpoint. (currently commented out) some weird mtls / tls server/client bullshit bug 

%prod.quarkus.rest-client.traffic-light-api.tls-configuration-name=http  (irgendwie sowas hier halt wird der fix sein glaube ich, der handshake beim weiterleiten failed)



# How to get acces controll running: update keycloak.yaml 

change "hostname" entry to, keep everything else the same: 

```
  hostname:
    hostname: https://keycloak.test/keycloak
    strict: false
    backchannelDynamic: true
```

afterwards apply file with 

```
kubectl apply -f keycloak.yaml -n keycloak
```

applying the yaml should restart the keycloak instance with the new metadata set 

# Importing the quarkus realm  I have got 2 guides by ai just try what works:

## first one:

# Importing the `group8-task5` Keycloak Realm (Instructor Guide)

This guide explains how to import the Keycloak realm  
`group8-task5-realm-import.json` into an **already running Keycloak**  
in the existing `keycloak` namespace on a local Kubernetes cluster.

This method does **not** require rebuilding or redeploying Keycloak.

---

## Preconditions

- Kubernetes cluster is running
- Namespace `keycloak` already exists
- A Keycloak pod is already running in that namespace
- `kubectl` access to the cluster
- File `group8-task5-realm-import.json` is present locally

---

## 1. Verify Keycloak pod

```bash
kubectl get pods -n keycloak
```

You should see something similar to:

```
keycloak-0   1/1   Running
```

If the pod name differs, replace `keycloak-0` in the commands below.

---

## 2. Copy the realm import file into the Keycloak pod

> `kubectl cp` may fail because the Keycloak image does not contain `tar`.
> This method works on minimal images.

```bash
kubectl exec -n keycloak -i keycloak-0 -- sh -c \
  'cat > /tmp/group8-task5-realm-import.json' \
  < group8-task5-realm-import.json
```

Verify the file exists:

```bash
kubectl exec -n keycloak keycloak-0 -- ls -lah /tmp | grep group8-task5
```

---

## 3. Import the realm into Keycloak

```bash
kubectl exec -n keycloak keycloak-0 -- \
  /opt/keycloak/bin/kc.sh import --optimized \
  --file /tmp/group8-task5-realm-import.json
```

Expected output includes lines indicating successful realm import.

---

## 4. Restart Keycloak (required)

Restart Keycloak so the imported realm becomes active.

If Keycloak runs as a StatefulSet:

```bash
kubectl rollout restart -n keycloak statefulset/keycloak
```

If Keycloak runs as a Deployment:

```bash
kubectl rollout restart -n keycloak deployment/keycloak
```

Wait until the pod is running again:

```bash
kubectl get pods -n keycloak
```

---

## 5. Verify the realm

Open the Keycloak Admin Console and verify that the realm
`group8-task5` exists.

Typical URL (may differ depending on setup):

```
http://localhost:8080/admin
```

---

## Notes for Quarkus Services

Quarkus services expect the following realm:

```
group8-task5
```

Example OIDC configuration used by the services:

```properties
quarkus.oidc.auth-server-url=http://keycloak.keycloak.svc.cluster.local:8080/realms/group8-task5
```

Ensure Keycloak is reachable via the Kubernetes service DNS name and not `localhost`.

---

## Troubleshooting

- Realm import fails:
  - Ensure the realm name in the JSON matches exactly (`group8-task5`)
  - Ensure Keycloak is not running in read-only mode
- File not found:
  - Re-run step 2 and verify `/tmp/group8-task5-realm-import.json`
- Import succeeds but realm not visible:
  - Ensure Keycloak was restarted after import

---

End of guide.


## Second one:

import quarkus realm that is sinide quarkus.json

1) Create/Update the Secret containing the realm export

```
kubectl -n keycloak create secret generic realm-import \
  --from-file=quarkus.json=./quarkus.json \
  --dry-run=client -o yaml | kubectl apply -f -
```

2) Create the KeycloakRealmImport that targets your Keycloak instance

```
kubectl apply -f - <<'YAML'
apiVersion: k8s.keycloak.org/v2alpha1
kind: KeycloakRealmImport
metadata:
  name: realm-import
  namespace: keycloak
spec:
  keycloakCRName: keycloak
  realm:
    secretName: realm-import
YAML
```

3) Watch import status

```
kubectl -n keycloak get keycloakrealmimports
kubectl -n keycloak describe keycloakrealmimport realm-import
```

You want to see a success-ish Phase/Conditions (often done / Ready=True depending on operator version).

### exporting quarkus realm:

# 1. Set the current namespace to keycloak
```
kubectl config set-context --current --namespace=keycloak
```
# Export the realm to a file inside the pod
```
kubectl exec -n keycloak keycloak-0 -- \
  /opt/keycloak/bin/kc.sh export --optimized \
  --realm group8-task5 \
  --file /tmp/group8-task5-realm-import.json
```
# Confirm it exists
```
kubectl exec -n keycloak keycloak-0 -- ls -lah /tmp | grep group8-task5
```
# Copy it out without kubectl cp (no tar needed)
```
kubectl exec -n keycloak keycloak-0 -- cat /tmp/group8-task5-realm-import.json > group8-task5-realm-import.json
```

## Access Scheme:

How to read it : 

Client -> Services it can use

### DONE Connections 
Tcc State controller  -> trafficLightDeviceService, audit-service

tcc  status service -> time service, trafficLightDeviceService

tcc priority -> state controller, time service, audit-service, location validator


#### fields to copy for clients

# Setting up OIDC Client to get send requests to other services with access control

application.properties:


%prod.quarkus.oidc-client.auth-server-url=http://keycloak-service.keycloak.svc.cluster.local:8080/keycloak/realms/group8-task5
%prod.quarkus.oidc-client.client-id=tcc-state-controller
%prod.quarkus.oidc-client.credentials.secret=MEb3nfjgTGkEHiZs8NugXxKTf2UqHdVC
%prod.quarkus.oidc-client.grant.type=client

#Set Client base url for api calls for different clients of service
%prod.quarkus.rest-client.traffic-light-api.url=https://gruppe8-traffic-light-device-service.gruppe8-traffic-light-devices.svc.cluster.local:443


and pom.xml:

      <dependency>
         <groupId>io.quarkus</groupId>
         <artifactId>quarkus-oidc-client</artifactId>
      </dependency>
      <dependency>
         <groupId>io.quarkus</groupId>
         <artifactId>quarkus-rest-client-oidc-filter</artifactId>
      </dependency>


#### fields to copy for services 

application.properties:

#set oidc stuff 
quarkus.oidc.auth-server-url=http://keycloak-service.keycloak.svc.cluster.local:8080/keycloak/realms/group8-task5
quarkus.oidc.discovery-enabled=true

quarkus.oidc.application-type=service

quarkus.oidc.roles.role-claim-path=resource_access/traffic-light-device/roles

quarkus.oidc.roles.source=accesstoken

and pom.xml:

      <dependency>
         <groupId>io.quarkus</groupId>
         <artifactId>quarkus-oidc</artifactId>
      </dependency>

#### turn on debugging

# TEMP debug logs
quarkus.log.category."io.quarkus.oidc".level=DEBUG
quarkus.log.category."io.quarkus.security".level=DEBUG




