#!/bin/bash

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Default ports (configured in application.properties)
AUTH_PORT=8081
STATE_PORT=8082
STATUS_PORT=8083
TIME_PORT=8084
DEVICE_PORT=8085
PRIORITY_PORT=8086

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
    
    if [ "$method" = "GET" ]; then
        response=$(curl -s -w "\n%{http_code}" -X GET "$url" -H "Content-Type: application/json")
    else
        response=$(curl -s -w "\n%{http_code}" -X "$method" "$url" \
            -H "Content-Type: application/json" \
            -d "$data")
    fi
    
    http_code=$(echo "$response" | tail -n1)
    body=$(echo "$response" | sed '$d')
    
    if [ "$http_code" = "$expected_status" ]; then
        echo -e "  ${GREEN}✓ Status: ${http_code}${NC}"
        if [ ! -z "$body" ]; then
            echo "  Response: $body"
        fi
    else
        echo -e "  ${RED}✗ Expected ${expected_status}, got ${http_code}${NC}"
        if [ ! -z "$body" ]; then
            echo "  Response: $body"
        fi
    fi
    echo ""
}

# Check if services are running
echo -e "${YELLOW}Checking if services are running...${NC}\n"

# Test Auth Service
echo -e "${YELLOW}=== 1. Auth Service Tests ===${NC}"
test_endpoint "Get Token" "POST" "http://localhost:${AUTH_PORT}/api/auth/token" \
    '{"clientId":"test-client","clientSecret":"test-secret","username":"test","password":"test","grant_type":"password"}' \
    "200"

test_endpoint "Introspect Token" "POST" "http://localhost:${AUTH_PORT}/api/auth/introspect" \
    '"test-token"' \
    "200"

test_endpoint "User Info" "POST" "http://localhost:${AUTH_PORT}/api/auth/userinfo" \
    '"test-access-token"' \
    "200"

# Test State Controller
echo -e "${YELLOW}=== 2. State Controller Tests ===${NC}"
test_endpoint "Change State" "POST" "http://localhost:${STATE_PORT}/api/state/change" \
    '{"command":"change-to-red"}' \
    "200"

test_endpoint "Change Device State" "POST" "http://localhost:${STATE_PORT}/api/state/device/change-state" \
    '{"state":"red"}' \
    "200"

test_endpoint "Log Audit Event" "POST" "http://localhost:${STATE_PORT}/api/state/audit/events" \
    '{"request":{"id":"123"},"timestamp":"2024-01-01T12:00:00Z"}' \
    "200"

# Test Status Service
echo -e "${YELLOW}=== 3. Status Service Tests ===${NC}"
test_endpoint "Get Traffic Status (no vehicle)" "GET" "http://localhost:${STATUS_PORT}/api/status/traffic" \
    "" \
    "200"

test_endpoint "Get Traffic Status (with vehicle)" "GET" "http://localhost:${STATUS_PORT}/api/status/traffic?vehicle=true" \
    "" \
    "200"

# Test Time Service
echo -e "${YELLOW}=== 4. Time Service Tests ===${NC}"
# Get current time in milliseconds (convert seconds to milliseconds)
current_timestamp=$(($(date +%s) * 1000))
test_endpoint "Validate Time" "POST" "http://localhost:${TIME_PORT}/api/time/vehicle?timestamp=${current_timestamp}" \
    "" \
    "200"

# Test invalid timestamp (should fail)
old_timestamp=$((current_timestamp - 86400000)) # 24 hours ago in milliseconds
test_endpoint "Validate Old Time (should fail)" "POST" "http://localhost:${TIME_PORT}/api/time/vehicle?timestamp=${old_timestamp}" \
    "" \
    "400"

# Test Traffic Light Device Service
echo -e "${YELLOW}=== 5. Traffic Light Device Service Tests ===${NC}"
test_endpoint "Get Traffic State" "GET" "http://localhost:${DEVICE_PORT}/api/device/traffic-state" \
    "" \
    "200"

test_endpoint "Change Device State (green)" "POST" "http://localhost:${DEVICE_PORT}/api/device/change-state?state=green" \
    "" \
    "200"

test_endpoint "Change Device State (red)" "POST" "http://localhost:${DEVICE_PORT}/api/device/change-state?state=red" \
    "" \
    "200"

# Test Priority Service (External endpoints called by clients)
echo -e "${YELLOW}=== 6. Priority Service Tests (External) ===${NC}"
test_endpoint "Post Priority Request" "POST" "http://localhost:${PRIORITY_PORT}/api/priority/requests" \
    '{"vehicleType":"emergency"}' \
    "200"

DUMMY_REQUEST_ID="123e4567-e89b-12d3-a456-426614174000"
test_endpoint "Get Request Status" "GET" "http://localhost:${PRIORITY_PORT}/api/priority/requests/${DUMMY_REQUEST_ID}" \
    "" \
    "200"

echo -e "${GREEN}=== Testing Complete ===${NC}"
echo ""
echo -e "${YELLOW}Note: Make sure all services are running in dev mode:${NC}"
echo "  cd task3/services/tcc-auth-service && mvn quarkus:dev"
echo "  cd task3/services/tcc-state-controller && mvn quarkus:dev"
echo "  cd task3/services/tcc-status-service && mvn quarkus:dev"
echo "  cd task3/services/time-service && mvn quarkus:dev"
echo "  cd task3/services/traffic-light-device-service && mvn quarkus:dev"
echo "  cd task3/services/tcc-priority-service && mvn quarkus:dev"
echo ""
echo -e "${YELLOW}Or use different ports by setting environment variables:${NC}"
echo "  AUTH_PORT=8081 STATE_PORT=8082 STATUS_PORT=8083 TIME_PORT=8084 DEVICE_PORT=8085 PRIORITY_PORT=8086 ./test-endpoints.sh"

