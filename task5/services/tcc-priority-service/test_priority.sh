#!/bin/bash

# Base URL (HTTPS because server only listens on 8443)
URL="https://localhost:8443/api/priority/requests"
# Path to client certificate (relative to project root, adjusting based on script location)
# We assume script is run from services/tcc-priority-service, so we go up
CERT_PATH="../../clients/emergency-vehicle-client/src/main/resources/certs/client.p12"
CERT_PASS="changeit"

# Function to send request
send_request() {
    local type=$1
    local id=$2
    local sleep_time=$3
    
    # Payload
    json="{\"vehicleType\": \"$type\", \"vehicleId\": \"$id\"}"
    
    echo "Sending request for $type ($id)..."
    # Added -k (insecure) and cert args for mTLS
    if [ -f "$CERT_PATH" ]; then
        curl -k -s -X POST "$URL" \
             --cert-type P12 --cert "$CERT_PATH:$CERT_PASS" \
             -H "Content-Type: application/json" \
             -d "$json" &
    else
        echo "Error: Certificate not found at $CERT_PATH"
        # Try without cert just in case (will fail if mTLS enforced)
        curl -k -s -X POST "$URL" \
             -H "Content-Type: application/json" \
             -d "$json" &
    fi
}

echo "=== Starting Test ==="

# 1. Send an initial request to occupy the processor (simulated)
# Assuming the server processes fast, we invoke high load or rely on sync blocks.
# We will send 3 requests rapidly in background.

# Low priority first
send_request "other-vehicle" "car-1" 0

# Medium priority
send_request "mayor-vehicle" "mayor-1" 0

# High priority
send_request "emergency-vehicle" "ambulance-1" 0

# 2. Test Single Active Request Limit
# Try to send another request for car-1 immediately (should likely fail if first is still active/processing)
sleep 0.1
echo "Sending duplicate request for car-1..."
if [ -f "$CERT_PATH" ]; then
    curl -k -v -X POST "$URL" \
         --cert-type P12 --cert "$CERT_PATH:$CERT_PASS" \
         -H "Content-Type: application/json" \
         -d '{"vehicleType": "other-vehicle", "vehicleId": "car-1"}'
else 
     curl -k -v -X POST "$URL" \
         -H "Content-Type: application/json" \
         -d '{"vehicleType": "other-vehicle", "vehicleId": "car-1"}'
fi

wait
echo "=== Test Completed ==="
echo "Check server logs to see if 'emergency-vehicle' was processed before 'mayor-vehicle' (if they arrived while 'other' was processing)."
