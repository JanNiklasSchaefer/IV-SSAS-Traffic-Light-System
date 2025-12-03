#!/bin/bash

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Namespace
NAMESPACE=${NAMESPACE:-gruppe8-tcc}

# Detect if running in cluster or locally
detect_environment() {
    # Check if kubectl is available and cluster is accessible
    if command -v kubectl &> /dev/null && kubectl cluster-info &> /dev/null; then
        # Check if services are deployed in cluster
        if kubectl get service gruppe8-tcc-priority-service -n "$NAMESPACE" &> /dev/null; then
            echo "cluster"
            return 0
        fi
    fi
    echo "local"
    return 1
}

ENVIRONMENT=$(detect_environment)

if [ "$ENVIRONMENT" = "cluster" ]; then
    echo -e "${BLUE}=== Testing TCC Microservices in Kubernetes Cluster ===${NC}"
    echo -e "${BLUE}Namespace: ${NAMESPACE}${NC}\n"
    
    # Get Ingress hostname
    INGRESS_HOST=$(kubectl get ingress -n "$NAMESPACE" -o jsonpath='{.items[0].spec.rules[0].host}' 2>/dev/null || echo "")
    
    # Check if port-forward is running (check for common ports)
    PORT_FORWARD_PRIORITY=$(lsof -ti:8080 2>/dev/null || echo "")
    
    if [ ! -z "$INGRESS_HOST" ]; then
        echo -e "${GREEN}Ingress found: ${INGRESS_HOST}${NC}"
        echo -e "${GREEN}Testing external endpoints via Ingress${NC}\n"
        EXTERNAL_BASE="http://${INGRESS_HOST}"
    elif [ ! -z "$PORT_FORWARD_PRIORITY" ]; then
        echo -e "${YELLOW}Port-forward detected on port 8080${NC}"
        echo -e "${YELLOW}Testing external endpoints via port-forward${NC}\n"
        EXTERNAL_BASE="http://localhost:8080"
    else
        echo -e "${YELLOW}No Ingress or port-forward detected${NC}"
        echo -e "${YELLOW}Setting up port-forward for Priority Service...${NC}"
        echo -e "${YELLOW}Run in another terminal: kubectl port-forward -n ${NAMESPACE} service/gruppe8-tcc-priority-service 8080:80${NC}\n"
        EXTERNAL_BASE="http://localhost:8080"
    fi
    
    # For internal endpoints, we need to test from within cluster
    # Create/use a test pod to execute tests from within the cluster
    echo -e "${BLUE}Setting up test pod for internal endpoint testing...${NC}"
    
    # Check if test-pod exists, create if not
    if ! kubectl get pod test-pod -n "$NAMESPACE" &> /dev/null; then
        echo -e "${YELLOW}Creating test pod...${NC}"
        kubectl apply -f kubernetes/test-pod.yaml
        if ! kubectl wait --for=condition=Ready pod/test-pod -n "$NAMESPACE" --timeout=60s; then
            echo -e "${RED}Failed to create test-pod. Checking status...${NC}"
            kubectl describe pod test-pod -n "$NAMESPACE" | tail -20
            exit 1
        fi
        echo -e "${GREEN}Test pod is ready${NC}\n"
    else
        # Ensure pod is ready
        if ! kubectl wait --for=condition=Ready pod/test-pod -n "$NAMESPACE" --timeout=10s &> /dev/null; then
            echo -e "${YELLOW}Test pod exists but not ready. Waiting...${NC}"
            kubectl wait --for=condition=Ready pod/test-pod -n "$NAMESPACE" --timeout=60s
        fi
    fi
    
    # Use Service Discovery URLs (will be accessed from within cluster via test-pod)
    # TCC Services in gruppe8-tcc namespace
    TCC_NAMESPACE="gruppe8-tcc"
    STATE_BASE="http://gruppe8-tcc-state-controller.${TCC_NAMESPACE}.svc.cluster.local:80"
    STATUS_BASE="http://gruppe8-tcc-status-service.${TCC_NAMESPACE}.svc.cluster.local:80"
    PRIORITY_BASE="http://gruppe8-tcc-priority-service.${TCC_NAMESPACE}.svc.cluster.local:80"
    AUDIT_BASE="http://gruppe8-tcc-audit-service.${TCC_NAMESPACE}.svc.cluster.local:80"
    # Auth Service in gruppe8-auth-services namespace
    AUTH_NAMESPACE="gruppe8-auth-services"
    AUTH_BASE="http://gruppe8-tcc-auth-service.${AUTH_NAMESPACE}.svc.cluster.local:80"
    # Shared Services in gruppe8-shared-services namespace
    SHARED_NAMESPACE="gruppe8-shared-services"
    TIME_BASE="http://gruppe8-time-service.${SHARED_NAMESPACE}.svc.cluster.local:80"
    LOCATION_BASE="http://gruppe8-location-validator.${SHARED_NAMESPACE}.svc.cluster.local:80"
    # Traffic Light Device Service in gruppe8-traffic-light-devices namespace
    DEVICE_NAMESPACE="gruppe8-traffic-light-devices"
    DEVICE_BASE="http://gruppe8-traffic-light-device-service.${DEVICE_NAMESPACE}.svc.cluster.local:80"
    
    # Set flag to use kubectl exec for cluster tests
    USE_CLUSTER_POD=true
else
    echo -e "${YELLOW}=== Testing TCC Microservices Locally ===${NC}"
    echo -e "${YELLOW}No cluster detected, using localhost${NC}\n"
    
    # Detect which services are running on which ports
    # Default: all services run on 8080 (standard Quarkus port)
    # If custom ports are set via environment variables, use those
    DEFAULT_PORT=8080
    
    AUTH_PORT=${AUTH_PORT:-$DEFAULT_PORT}
    STATE_PORT=${STATE_PORT:-$DEFAULT_PORT}
    STATUS_PORT=${STATUS_PORT:-$DEFAULT_PORT}
    TIME_PORT=${TIME_PORT:-$DEFAULT_PORT}
    DEVICE_PORT=${DEVICE_PORT:-$DEFAULT_PORT}
    PRIORITY_PORT=${PRIORITY_PORT:-$DEFAULT_PORT}
    AUDIT_PORT=${AUDIT_PORT:-$DEFAULT_PORT}
    
    # Try to detect which service is actually running on which port
    # by checking if port is in use and what responds
    detect_service_port() {
        local service_name=$1
        local default_port=$2
        local test_port=$default_port
        
        # Check if something is listening on the port
        if lsof -ti:$test_port &> /dev/null; then
            # Try to see if it's the right service by checking a known endpoint
            response=$(curl -s -o /dev/null -w "%{http_code}" "http://localhost:${test_port}/q/health" 2>/dev/null || echo "000")
            if [ "$response" = "200" ] || [ "$response" = "503" ]; then
                echo $test_port
                return 0
            fi
        fi
        echo $default_port
    }
    
    # Use detected or default ports
    EXTERNAL_BASE="http://localhost:${PRIORITY_PORT}"
    AUTH_BASE="http://localhost:${AUTH_PORT}"
    STATE_BASE="http://localhost:${STATE_PORT}"
    STATUS_BASE="http://localhost:${STATUS_PORT}"
    TIME_BASE="http://localhost:${TIME_PORT}"
    LOCATION_PORT=${LOCATION_PORT:-$DEFAULT_PORT}
    LOCATION_BASE="http://localhost:${LOCATION_PORT}"  # Same port as time-service for local testing
    DEVICE_BASE="http://localhost:${DEVICE_PORT}"
    PRIORITY_BASE="http://localhost:${PRIORITY_PORT}"
    AUDIT_BASE="http://localhost:${AUDIT_PORT}"
    USE_CLUSTER_POD=false
    
    echo -e "${YELLOW}Note: All services use port 8080 by default (Quarkus standard)${NC}"
    echo -e "${YELLOW}If you need custom ports, set environment variables:${NC}"
    echo -e "${YELLOW}  AUTH_PORT=8081 STATE_PORT=8082 ... ./test-endpoints.sh${NC}\n"
fi

echo -e "${YELLOW}=== Testing TCC Microservices Endpoints ===${NC}\n"

# Function to test endpoint
test_endpoint() {
    local name=$1
    local method=$2
    local url=$3
    local data=$4
    local expected_status=$5
    
    echo -e "${YELLOW}Testing: ${name}${NC}"
    echo "  ${method} ${url}"
    
    # Use kubectl exec if testing from cluster, otherwise use local curl
    if [ "$USE_CLUSTER_POD" = "true" ]; then
        if [ "$method" = "GET" ]; then
            response=$(kubectl exec -n "$NAMESPACE" test-pod -- curl -s -w "\n%{http_code}" -X GET "$url" -H "Content-Type: application/json" 2>&1)
        else
            response=$(kubectl exec -n "$NAMESPACE" test-pod -- curl -s -w "\n%{http_code}" -X "$method" "$url" \
                -H "Content-Type: application/json" \
                -d "$data" 2>&1)
        fi
    else
        if [ "$method" = "GET" ]; then
            response=$(curl -s -w "\n%{http_code}" -X GET "$url" -H "Content-Type: application/json")
        else
            response=$(curl -s -w "\n%{http_code}" -X "$method" "$url" \
                -H "Content-Type: application/json" \
                -d "$data")
        fi
    fi
    
    http_code=$(echo "$response" | tail -n1)
    body=$(echo "$response" | sed '$d')
    
    if [ "$http_code" = "$expected_status" ]; then
        echo -e "  ${GREEN}✓ Status: ${http_code}${NC}"
        if [ ! -z "$body" ] && [ "$body" != "null" ]; then
            echo "  Response: $(echo "$body" | head -c 200)"
        fi
    else
        echo -e "  ${RED}✗ Expected ${expected_status}, got ${http_code}${NC}"
        if [ ! -z "$body" ]; then
            echo "  Response: $(echo "$body" | head -c 200)"
        fi
    fi
    echo ""
}

# Check if services are running
echo -e "${YELLOW}Checking if services are running...${NC}\n"

# Test Auth Service
echo -e "${YELLOW}=== 1. Auth Service Tests ===${NC}"
test_endpoint "Get Token" "POST" "${AUTH_BASE}/api/auth/token" \
    '{"clientId":"test-client","clientSecret":"test-secret","username":"test","password":"test","grant_type":"password"}' \
    "200"

test_endpoint "Introspect Token" "POST" "${AUTH_BASE}/api/auth/introspect" \
    '"test-token"' \
    "200"

test_endpoint "User Info" "POST" "${AUTH_BASE}/api/auth/userinfo" \
    '"test-access-token"' \
    "200"

# Test State Controller
echo -e "${YELLOW}=== 2. State Controller Tests ===${NC}"
test_endpoint "Change State" "POST" "${STATE_BASE}/api/state/change" \
    '{"command":"change-to-red"}' \
    "200"

test_endpoint "Change Device State" "POST" "${STATE_BASE}/api/state/device/change-state" \
    '{"state":"red"}' \
    "200"

test_endpoint "Log Audit Event" "POST" "${STATE_BASE}/api/state/audit/events" \
    '{"request":{"id":"123"},"timestamp":"2024-01-01T12:00:00Z"}' \
    "200"

# Test Status Service
echo -e "${YELLOW}=== 3. Status Service Tests ===${NC}"
test_endpoint "Get Traffic Status (no vehicle)" "GET" "${STATUS_BASE}/api/status/traffic" \
    "" \
    "200"

test_endpoint "Get Traffic Status (with vehicle)" "GET" "${STATUS_BASE}/api/status/traffic?vehicle=true" \
    "" \
    "200"

# Test Time Service
echo -e "${YELLOW}=== 4. Time Service Tests ===${NC}"
# Get current time in milliseconds (convert seconds to milliseconds)
current_timestamp=$(($(date +%s) * 1000))
test_endpoint "Validate Time" "POST" "${TIME_BASE}/api/time/vehicle?timestamp=${current_timestamp}" \
    "" \
    "200"

# Test invalid timestamp (should fail)
old_timestamp=$((current_timestamp - 86400000)) # 24 hours ago in milliseconds
test_endpoint "Validate Old Time (should fail)" "POST" "${TIME_BASE}/api/time/vehicle?timestamp=${old_timestamp}" \
    "" \
    "400"

# Test Traffic Light Device Service
echo -e "${YELLOW}=== 5. Traffic Light Device Service Tests ===${NC}"
test_endpoint "Get Traffic State" "GET" "${DEVICE_BASE}/api/device/traffic-state" \
    "" \
    "200"

test_endpoint "Change Device State (green)" "POST" "${DEVICE_BASE}/api/device/change-state?state=green" \
    "" \
    "200"

test_endpoint "Change Device State (red)" "POST" "${DEVICE_BASE}/api/device/change-state?state=red" \
    "" \
    "200"

# Test Audit Service
echo -e "${YELLOW}=== 6. Audit Service Tests ===${NC}"
test_endpoint "Log Audit Event" "POST" "${AUDIT_BASE}/api/audit/events" \
    '{"request":{"id":"test-123"},"timestamp":"2024-01-01T12:00:00Z"}' \
    "200"

test_endpoint "Get Audit Logs" "GET" "${AUDIT_BASE}/api/audit/logs" \
    "" \
    "200"

# Test Location Validator Service
echo -e "${YELLOW}=== 7. Location Validator Service Tests ===${NC}"
test_endpoint "Validate Vehicle Location" "POST" "${LOCATION_BASE}/api/location/vehicle?vehicle_id=test-vehicle" \
    '{"coordinates":[52.5,13.4]}' \
    "200"

# Test Priority Service (External endpoints called by clients)
echo -e "${YELLOW}=== 8. Priority Service Tests (External) ===${NC}"
# First create a request to get a real requestId
if [ "$USE_CLUSTER_POD" = "true" ]; then
    response=$(kubectl exec -n "$NAMESPACE" test-pod -- curl -s -X POST "${PRIORITY_BASE}/api/priority/requests" \
        -H "Content-Type: application/json" \
        -d '{"vehicleType":"emergency"}' 2>&1)
else
    response=$(curl -s -X POST "${EXTERNAL_BASE}/api/priority/requests" \
        -H "Content-Type: application/json" \
        -d '{"vehicleType":"emergency"}')
fi
request_id=$(echo "$response" | grep -o '"requestId":"[^"]*"' | cut -d'"' -f4)

if [ ! -z "$request_id" ]; then
    test_endpoint "Post Priority Request" "POST" "${PRIORITY_BASE}/api/priority/requests" \
        '{"vehicleType":"emergency"}' \
        "200"
    
    test_endpoint "Get Request Status" "GET" "${PRIORITY_BASE}/api/priority/requests/${request_id}" \
        "" \
        "200"
else
    test_endpoint "Post Priority Request" "POST" "${PRIORITY_BASE}/api/priority/requests" \
        '{"vehicleType":"emergency"}' \
        "200"
    
    DUMMY_REQUEST_ID="123e4567-e89b-12d3-a456-426614174000"
    test_endpoint "Get Request Status" "GET" "${PRIORITY_BASE}/api/priority/requests/${DUMMY_REQUEST_ID}" \
        "" \
        "200"
fi

# Test Clients (simulate client calls to external endpoints)
echo -e "${YELLOW}=== 9. Client Tests (External Endpoints) ===${NC}"
echo -e "${BLUE}Testing endpoints that clients call via Ingress${NC}\n"

# Note: Client endpoints are tested via Priority Service (which clients call through Ingress)
# In cluster mode, we test the Priority Service directly since test-pod can't access Ingress
# Clients run outside the cluster and call these endpoints via Ingress hostname

if [ "$ENVIRONMENT" = "cluster" ]; then
    # In cluster, test Priority Service directly (clients would call via Ingress)
    CLIENT_BASE="$PRIORITY_BASE"
    echo -e "${YELLOW}Note: Testing Priority Service directly (clients call via Ingress from outside cluster)${NC}\n"
else
    # Locally, test via external base URL
    CLIENT_BASE="${EXTERNAL_BASE:-$PRIORITY_BASE}"
fi

# Test Status Service endpoint (called by clients via Ingress)
test_endpoint "Client Endpoint: Get Traffic Status (vehicle)" "GET" "${STATUS_BASE}/api/status/traffic?vehicle=true" \
    "" \
    "200"

test_endpoint "Client Endpoint: Get Traffic Status (no vehicle)" "GET" "${STATUS_BASE}/api/status/traffic?vehicle=false" \
    "" \
    "200"

# Test Priority Service endpoints (called by clients via Ingress)
test_endpoint "Client Endpoint: Post Priority Request (emergency)" "POST" "${CLIENT_BASE}/api/priority/requests" \
    '{"vehicleType":"emergency"}' \
    "200"

# Create a priority request for client testing
if [ "$USE_CLUSTER_POD" = "true" ]; then
    client_response=$(kubectl exec -n "$NAMESPACE" test-pod -- curl -s -X POST "${CLIENT_BASE}/api/priority/requests" \
        -H "Content-Type: application/json" \
        -d '{"vehicleType":"mayor"}' 2>&1)
else
    client_response=$(curl -s -X POST "${CLIENT_BASE}/api/priority/requests" \
        -H "Content-Type: application/json" \
        -d '{"vehicleType":"mayor"}')
fi
client_request_id=$(echo "$client_response" | grep -o '"requestId":"[^"]*"' | cut -d'"' -f4)

if [ ! -z "$client_request_id" ]; then
    test_endpoint "Client Endpoint: Get Request Status" "GET" "${CLIENT_BASE}/api/priority/requests/${client_request_id}" \
        "" \
        "200"
fi

test_endpoint "Client Endpoint: Post Priority Request (mayor)" "POST" "${CLIENT_BASE}/api/priority/requests" \
    '{"vehicleType":"mayor"}' \
    "200"

test_endpoint "Client Endpoint: Post Priority Request (other)" "POST" "${CLIENT_BASE}/api/priority/requests" \
    '{"vehicleType":"other"}' \
    "200"

test_endpoint "Client Endpoint: Post Priority Request (pedestrian)" "POST" "${CLIENT_BASE}/api/priority/requests" \
    '{"vehicleType":"pedestrian"}' \
    "200"

echo -e "${BLUE}Note: Clients run outside the cluster and call these endpoints via Ingress (${EXTERNAL_BASE:-tcc.test})${NC}"
echo -e "${BLUE}To test clients directly, run the Java client applications:${NC}"
echo -e "${BLUE}  cd clients/emergency-vehicle-client && java -Dbase.url=${EXTERNAL_BASE:-http://tcc.test} -cp target/classes de.tub.aot.client.EmergencyVehicleClient${NC}"

echo -e "${GREEN}=== Testing Complete ===${NC}"
echo ""

if [ "$ENVIRONMENT" = "cluster" ]; then
    echo -e "${BLUE}Tested in Kubernetes cluster (namespace: ${NAMESPACE})${NC}"
    echo -e "${YELLOW}Note: For external endpoints, ensure port-forward is running:${NC}"
    echo "  kubectl port-forward -n ${NAMESPACE} service/gruppe8-tcc-priority-service 8080:80"
    echo ""
    echo -e "${YELLOW}Or configure Ingress and use the hostname${NC}"
else
    echo -e "${YELLOW}Tested locally. To test in cluster:${NC}"
    echo "  1. Deploy services to Kubernetes"
    echo "  2. Ensure kubectl is configured"
    echo "  3. Run this script again - it will auto-detect the cluster"
    echo ""
    echo -e "${YELLOW}For local testing, make sure all services are running in dev mode:${NC}"
    echo "  cd services/auth-service && mvn quarkus:dev"
    echo "  cd services/tcc-state-controller && mvn quarkus:dev"
    echo "  cd services/tcc-status-service && mvn quarkus:dev"
    echo "  cd services/time-service && mvn quarkus:dev"
    echo "  cd services/traffic-light-device-service && mvn quarkus:dev"
    echo "  cd services/tcc-priority-service && mvn quarkus:dev"
    echo ""
    echo -e "${YELLOW}Or use different ports by setting environment variables:${NC}"
    echo "  AUTH_PORT=8081 STATE_PORT=8082 STATUS_PORT=8083 TIME_PORT=8084 DEVICE_PORT=8085 PRIORITY_PORT=8086 ./test-endpoints.sh"
fi

