#!/bin/bash

# Base URL (HTTP for local testing with security disabled)
URL="http://localhost:8080/api/priority/requests"

# Function to send request
send_request() {
    local type=$1
    local id=$2
    
    # Payload
    json="{\"vehicleType\": \"$type\", \"vehicleId\": \"$id\"}"
    
    echo "Sending request for $type ($id)..."
    curl -s -X POST "$URL" \
         -H "Content-Type: application/json" \
         -d "$json" &
}

echo "=== Starting Local Logic Test ==="

# 1. Send LOW priority first (to see others jump ahead in logs)
send_request "other-vehicle" "car-low"

# 2. Send MEDIUM priority
send_request "mayor-vehicle" "car-med"

# 3. Send HIGH priority
send_request "emergency-vehicle" "car-high"

# 4. Test Single Active Request constraint
# Try to send a duplicate for 'car-low' immediately
echo "Sende Duplikat für car-low (sollte 'denied' zurückgeben)..."
curl -s -X POST "$URL" \
     -H "Content-Type: application/json" \
     -d '{"vehicleType": "other-vehicle", "vehicleId": "car-low"}' | grep -o "denied" && echo " -> Korrekt abgelehnt."

wait
echo "=== Test abgeschlossen ==="
echo "Schau in die Server-Logs von 'mvn quarkus:dev':"
echo "1. Siehst du 'denied' für car-low?"
echo "2. Siehst du, dass car-high VOR car-med verarbeitet wurde?"
