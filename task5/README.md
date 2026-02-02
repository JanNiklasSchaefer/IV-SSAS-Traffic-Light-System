## Task 5 – Access Controll

### REST API Specification Table View

TODO: Add endpoints

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

An example call for the endpoint https://tcc.test/api/status/traffic?vehicle={Boolean} would be:

```
2 8d8d1437-907b-3a79-900a-c5f0ea1f5c73
```

**Note:** This calls the endpoint /api/status/traffic?vehicle={Boolean} with the Boolean value `true`

- The first 3 options are manual calls to the endpoints with the possibility to input the parameters.
- The 4th "4" option is a demo run to make testing the clients easier.
- The 5th option "q" shuts off the client.

### Testing Security Implementation

#### Internal Tests (from inside the cluster)

We provide an additional guide to test our internal access policy in the file [testing-access-endpoints.md](testing-access-endpoints.md).

## Testing Priority Service

This part will explain how to test the priority service, in regards to managin the priority between mayor - and emergency-vehicles. 

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
  That’s why we first switch the traffic state to `yellow` and then send both priority requests. This ensures both requests end up in the **same queue**.

- **We send the Mayor request first, then the Emergency Vehicle request.**  
  Even though the Mayor request is older, **priority queue rules** should rank the Emergency Vehicle request higher once both are queued.

- **After switching the traffic light to `red`, we validate the queue order via status polling.**  
  When you request status (`4 <request id>`), the system will either:
  - **accept** the request (meaning it is currently **#1 / highest priority**), or
  - respond that it is **queued** (meaning it isn't highest priority).

- **So the expected outcome is:**
  1) Polling the **Mayor** request first should **NOT** accept it → proves it is not highest priority.  
  2) Polling the **Emergency Vehicle** request should **accept** it → proves it is highest priority.  
  3) Polling the **Mayor** request again afterwards should then **accept** it → because the higher priority request has been handled.
