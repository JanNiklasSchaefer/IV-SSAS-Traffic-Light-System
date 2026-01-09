## Task 5 – Access Controll

### Overview

Task 4 focuses Access Controll

### Repository Layout

```
task5/
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

#Step 5: Deploy updated Keycloak.yaml and wait for deployment. This is necessary to guarantee the Securicy Policy. Changes are explained in later Section.
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

3. Import the realm into Keycloak (change http management port when keycloak is still turned on)

```bash
kubectl exec -n keycloak keycloak-0 -- \
  /opt/keycloak/bin/kc.sh import --optimized \
  --http-management-port=9002 \
  --file /tmp/group8-task5-realm-import.json
```
Expected output includes lines indicating successful realm import.

You can verifiy that the keycloak import worked by visiting the keycloak-admin ui. You should see the realm ```group8-task5```

**URL** ```keycloak.test```
**Username** : ```temp-admin```
**Password** : ```923b2ce326014a22b99766c7c5ab98d2```

Then in a final step, deploy the Quarkus Services:

```bash
# Step 7: Build and deploy all services
mvn clean package -Dquarkus.kubernetes.deploy=true

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

# Run Clients

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

```
# On macOS/Linux, edit /etc/hosts (requires sudo)
sudo nano /etc/hosts
# Add these lines:
127.0.0.1 tcc.test
127.0.0.1 keycloak.test
```

**Note:** On Windows, edit ```C:\Windows\System32\drivers\etc\hosts``` as Administrator.

**Alternative: Port-Forward (if Ingress is not available)**

If you prefer port-forwarding instead of Ingress:

# WHICH PORT TO SUGGEST i tested on the https ports ????

```bash
# In a separate terminal, forward the Priority Service port
kubectl port-forward -n gruppe8-tcc service/gruppe8-tcc-priority-service 8443:443

# Open another terminal and forward the keycloak ingress
kubectl port-forward -n keycloak svc/keycloak-service 8443:8443
```

```bash
# Forward Keycloak port to localhost
kubectl port-forward -n keycloak svc/keycloak-service 8080:80
```

**Note:** Port-forwarding only allows access to one service at a time. Ingress (recommended) allows access to all services via a single hostname.

## Starting and using the interactive External Clients

With this release, the clients are now interactive and provide a GUI via terminal where the endpoints of our cluster can be reached. An example for the emergency-vehicle-client follows, it is done the same for all other clients:

```
#Open a new terminal window per client

cd clients/emergency-vehicle-client

mvn clean package

java -Dbase.url=https://tcc.test -jar target/quarkus-app/quarkus-run.jar
```

This opens an interactive Quarkus Client, from which the public API Endpoints can be called. You are presented 5 Options:

```
=== Menu ===
1 <Boolean>        → GET  /api/status/traffic?vehicle={Boolean}
2                  → POST /api/priority/requests   (pedestrian)
3 <requestId>      → GET  /api/priority/requests/{requestId}
4                  → Run a demo of all endpoints with dummy data.
q                  → Quit

```

An example call for the endpoint https://tcc.test/api/status/traffic?vehicle={Boolean} would be:

```
1 true
```

**Note:** This calls the endpoint /api/status/traffic?vehicle={Boolean} with the Boolean value ```true```

- The first 3 options are manual calls to the endpoints with the possibility to input the parameters.
- The 4th "4" option is a demo run to make testing the clients easier.
- The 5th option "q" shuts off the client. 

## Security Implementation Summary

### ✅ Implemented Features

#### 1. Security Policy for External Communication

All microservices now implement OIDC-based authentication for external client requests:

- **OIDC Token Validation**: All service endpoints validate incoming OIDC tokens from Keycloak
- **Role-Based Access Control (RBAC)**: `@RolesAllowed` annotations are implemented on all service endpoints
- **Service Configuration**: All services are configured with:
  - `quarkus-oidc` dependency for token validation
  - OIDC server configuration pointing to Keycloak realm
  - Role claim path configuration for extracting roles from tokens

**Note:** Keycloak exposes endpoints exclusively over HTTPS. All communication is secured with TLS to prevent access tokens from being intercepted.

**Authorized Roles for each Service**

| Service Name | Authorized Roles |
|-------------|------------------|
| `tcc-priority-service` | `emergency-vehicle`, `mayor-vehicle` |
| `tcc-status-service` | `emergency-vehicle`, `mayor-vehicle`, `other-vehicle`, `pedestrian` |
| `tcc-state-controller` | `tcc-priority-service-control-role` |
| `tcc-audit-service` | `tcc-priority-service-audit-role`, `tcc-state-controller-audit-role` |
| `time-service` | `tcc-priority-service-time-service`, `tcc-status-service-time-service` |
| `location-validator` | `tcc-priority-service-location-role` |
| `traffic-light-device-service` | `tcc-state-controller-light-service`, `tcc-status-service-light-service` |

**Note:** Services only allow access by authorized roles. 

**Service-Roles for Internal Clients**

| Internal Client | Assigned Roles |
|----------------|----------------|
| `tcc-priority-service` | `tcc-priority-service-location-role`, `tcc-priority-service-controll-service`, `tcc-priority-service-audit-role`, `tcc-priority-service-time-service` |
| `tcc-status-service` | `tcc-status-service-light-service`, `tcc-status-service-time-service` |
| `tcc-state-controller` | `tcc-state-controller-audit-role`, `tcc-state-controller-light-service` |

**Note:** These are the service-roles each internal client is assigned


**Service-Roles for External Clients**

| Client | Assigned Roles |
|-------|----------------|
| `emergency-vehicle-client` | `emergency-vehicle`, `tcc-priority-service-emergency-vehicle`, `tcc-status-service-emergency-vehicle` |
| `mayor-vehicle-client` | `mayor-vehicle`, `tcc-priority-service-mayor-vehicle`, `tcc-status-service-mayor-vehicle` |
| `other-vehicle-client` | `other-vehicle`, `tcc-priority-service-other-vehicle`, `tcc-status-service-other-vehicle` |
| `pedestrian-client` | `pedestrian`, `tcc-priority-service-pedestrian`, `tcc-status-servicepe-destrian` |

**Note:** These are the service-roles each external client is assigned

**Changes to Keycloak yaml**

As previously described a new keycloak.yaml file must be applied to enable our security policy. 

The keycloak.yaml consists of 3 Parts:

1. The keycloak deployment
2. The keycloak certificate
3. The keycloak ingress 

For deployment the following was modified:

```yaml
  http:
    httpEnabled: false                                #Disables HTTP Requests for Keycloak
    tlsSecret: keycloak-tls-secret                    #Mount the keycloak-secret for TLS 
  hostname:
    hostname: https://keycloak.test/keycloak          #Set keycloak hostname
    strict: false
    backchannelDynamic: true                          #Enable internal DNS for backend Requests. 
```

**Keycloak Certificate**

After extracting the certificate, you can find it in ```certs/keycloak_ca.crt```. This is the certificate:

```
openssl x509 -in certs/ca_keycloak.crt -text -noout
Certificate:
    Data:
        Version: 3 (0x2)
        Serial Number:
            6a:13:f1:b9:e0:63:5e:7e:bb:3f:02:05:a0:da:24:81
        Signature Algorithm: ecdsa-with-SHA256
        Issuer: O = Gruppe 8, OU = Task 1, CN = Krzysztof Lagowski CA
        Validity
            Not Before: Jan  9 13:03:20 2026 GMT
            Not After : Apr  9 13:03:20 2026 GMT
        Subject: O = Gruppe 8, OU = Task 4
        Subject Public Key Info:
            Public Key Algorithm: id-ecPublicKey
                Public-Key: (256 bit)
                pub:
                    04:d3:bc:bd:dc:14:3e:61:2b:93:58:e0:42:c4:36:
                    9f:8f:00:ab:4a:8e:27:53:4e:29:47:34:31:28:9b:
                    3b:a3:9e:ca:7e:f8:d4:a8:df:6d:42:cd:fd:f6:d1:
                    c1:3f:95:78:ed:d6:96:d5:6e:78:29:1c:46:59:7a:
                    4f:d9:64:11:60
                ASN1 OID: prime256v1
                NIST CURVE: P-256
        X509v3 extensions:
            X509v3 Extended Key Usage: 
                TLS Web Server Authentication
            X509v3 Basic Constraints: critical
                CA:FALSE
            X509v3 Authority Key Identifier: 
                37:E0:72:55:46:79:2E:12:48:D3:C9:9E:D1:5D:C6:6F:B2:61:5A:22
            X509v3 Subject Alternative Name: 
                DNS:keycloak.test, DNS:keycloak-service.keycloak.svc.cluster.local
    Signature Algorithm: ecdsa-with-SHA256
    Signature Value:
        30:44:02:20:03:1c:84:95:90:c9:57:ab:d8:ec:8a:9e:5f:6e:
        4b:ad:0c:02:14:5f:23:96:38:10:ab:b7:aa:39:26:14:8f:35:
        02:20:74:da:fa:14:b6:f0:57:20:31:ea:d6:d9:3e:06:9a:77:
        71:f6:b0:e2:8b:f2:a2:f7:7d:f4:e9:31:25:c8:ce:8a
```

**Example Configuration (application.properties):**

```properties
# OIDC Server Configuration for External Authentication
quarkus.oidc.auth-server-url=http://keycloak-service.keycloak.svc.cluster.local:8080/keycloak/realms/group8-task5
quarkus.oidc.discovery-enabled=true
quarkus.oidc.client-id=tcc-priority-service
quarkus.oidc.application-type=service
quarkus.oidc.roles.source=accesstoken
quarkus.oidc.roles.role-claim-path=resource_access/tcc-priority-service/roles
```

**Example Endpoint Protection (Java):**

```java
@Path("/api/priority")
@ApplicationScoped
public class PriorityResource {

    @POST
    @Path("/requests")
    @RolesAllowed({ "emergency-vehicle", "mayor-vehicle" })
    public PriorityResponse createPriorityRequest(PriorityRequest request) {
        // Implementation
    }
}
```

#### 2. Client-Secrets as Kubernetes Secrets

All service client secrets are stored in Kubernetes Secrets instead of plaintext in `application.properties`:

**Implemented Services:**

- ✅ `tcc-priority-service`: Secret `tcc-priority-service-secret`
- ✅ `tcc-status-service`: Secret `tcc-status-service-secret`
- ✅ `tcc-state-controller`: Secret `tcc-state-controller-secret`

**Secret Configuration:**

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: tcc-priority-service-secret
  namespace: gruppe8-tcc
type: Opaque
data:
  client-secret: <base64-encoded-secret>
```

**Application Configuration:**

```properties
# OIDC Client Secret from Kubernetes Secret
%prod.quarkus.oidc-client.credentials.secret=${CLIENT_SECRET}
```

**Deployment Patch:**
Since Quarkus doesn't support direct `secretKeyRef` syntax in application.properties, environment variables are added via Kubernetes patch manifests:

```bash
kubectl patch deployment gruppe8-tcc-priority-service -n gruppe8-tcc \
  --type='json' \
  -p='[{"op": "add", "path": "/spec/template/spec/containers/0/env/-", "value": {"name": "CLIENT_SECRET", "valueFrom": {"secretKeyRef": {"name": "tcc-priority-service-secret", "key": "client-secret"}}}}]'
```

Patch manifests are available in `kubernetes/secrets/`:

- `tcc-priority-service-env-patch.yaml`
- `tcc-status-service-env-patch.yaml`
- `tcc-state-controller-env-patch.yaml`

#### External Testing (from your local machine)

**Prerequisites:**

- Ingress is deployed (`kubernetes/ingress.yaml`)
- Certificates have been extracted: `./setup-certs-now.sh` executed
- `/etc/hosts` contains `127.0.0.1 tcc.test` and `127.0.0.1 keycloak.test`or port forwarding from above example
- Keycloak realm `group8-task5` is imported and clients are present

**1. Standard TLS Clients (Mayor, Other, Pedestrian):**

```bash
cd clients/mayor-vehicle-client
mvn quarkus:dev -Dbase.url=https://tcc.test
```

Similarly for:

```bash
cd clients/other-vehicle-client
mvn quarkus:dev -Dbase.url=https://tcc.test

cd clients/pedestrian-client
mvn quarkus:dev -Dbase.url=https://tcc.test
```

**2. mTLS Client (Emergency Vehicle):**

```bash
cd clients/emergency-vehicle-client
mvn quarkus:dev -Dbase.url=https://tcc.test
```

**3. Optional: Direct curl test via Ingress (without clients):**

```bash
# Get token from Keycloak Ingress
TOKEN=$(curl -s \
  -d grant_type=client_credentials \
  -d client_id=emergency-vehicle-client \
  -d client_secret=<secret> \
  https://keycloak.test/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

# Call Priority Service via Ingress
curl -v -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"emergency"}' \
  "https://tcc.test/api/priority/requests"
```

#### 3. Internal Security Policy (Service-to-Service Authentication)

Already implemented in previous tasks:

- ✅ mTLS for service-to-service communication
- ✅ OIDC client credentials flow for service-to-service calls
- ✅ Role-based access control for internal service endpoints

### Security Policy Documentation

#### External Access Policy

**Principle**: All external client requests must be authenticated using OIDC tokens issued by Keycloak.

**Authentication Flow:**

1. Client authenticates with Keycloak using client credentials or user credentials
2. Keycloak issues an OIDC access token containing client roles
3. Client includes token in `Authorization: Bearer <token>` header
4. Service validates token with Keycloak
5. Service extracts roles from token and checks against `@RolesAllowed` annotations
6. Request is allowed or denied based on role membership

**Role Mapping:**

- Client roles are stored in Keycloak realm under `resource_access/<service-name>/roles`
- Services extract roles using `quarkus.oidc.roles.role-claim-path=resource_access/<service-name>/roles`
- Roles are matched against `@RolesAllowed` annotations on endpoints

**Example Role Structure in Keycloak:**

```
Realm: group8-task5
  Client: tcc-priority-service
    Roles:
      - emergency-vehicle
      - mayor-vehicle
      - other-vehicle
      - pedestrian
```

#### Internal Access Policy

**Principle**: Service-to-service communication uses mTLS + OIDC client credentials.

**Authentication Flow:**

1. Service authenticates with Keycloak using client credentials (stored in Kubernetes Secret)
2. Keycloak issues OIDC access token for service
3. Service includes token in `Authorization: Bearer <token>` header when calling other services
4. Target service validates token and checks service roles
5. Communication is secured with mTLS (mutual TLS)

### Testing Security Implementation

#### Internal Tests (from inside the cluster)

We provide an additional guide to test our internal access policy in the file [testing-access-endpoints.md](testing-access-endpoints.md).

## Bonus Information. (Not relevant for deployment)

### exporting quarkus realm:

To redeploy the realm across multiple systems you need to first export it.

#### 1. Set the current namespace to keycloak

```
kubectl config set-context --current --namespace=keycloak
```

#### 2. Export the realm to a file inside the pod

```
kubectl exec -n keycloak keycloak-0 -- \                                                                           
  /opt/keycloak/bin/kc.sh export --optimized \
  --http-management-port=9002 \
  --file /tmp/group8-task5-realm-import-new.json
```

#### 3. Confirm it exists

```
kubectl exec -n keycloak keycloak-0 -- ls -lah /tmp | grep group8-task5
```

#### 4. Copy it out without kubectl cp (no tar needed)

```
kubectl exec -n keycloak keycloak-0 -- cat /tmp/group8-task5-realm-import.json > group8-task5-realm-import.json
```


# TODO'S (ranked after priority somewhat)

- ~~keycloak service to service for all services~~
- ~~set up keycloak so its redeployable across systems~~
- ~~setup external client connection to public ingress url of keycloak~~
- ~~create yaml for all secrets so they are not plaintext~~
- ~~look at keycloak via https~~
- ~~look at keycloak via https for external clients~~
- ~~check and implement for "interactive client" (see feedback last task)~~
- ~~make all folders 1 to task5 i mean we have releases anyway and could convert back if we need.~~
- save external client-secrets as non plaintext
- unify role names (currently, some are service some are role. I fucked up here)
- delete unnecessary roles (die pedestrian und mayor role für prio service, haben ja eh kein acess)

zu klären wieso:

# Time Service REST Client (internal service-to-service with mTLS)

de.tub.aot.tcc.priority.TimeServiceClient/mp-rest/url=https://gruppe8-time-service.gruppe8-shared-services.svc.cluster.local:443
de.tub.aot.tcc.priority.TimeServiceClient/mp-rest/trust-store=tls
de.tub.aot.tcc.priority.TimeServiceClient/mp-rest/key-store=tls

in tcc.priority.services ist

-FIX 500 error when calling priority service and we forward to endpoint. (currently commented out) some weird mtls / tls server/client bullshit bug

%prod.quarkus.rest-client.traffic-light-api.tls-configuration-name=http (irgendwie sowas hier halt wird der fix sein glaube ich, der handshake beim weiterleiten failed)