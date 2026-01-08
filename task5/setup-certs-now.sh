#!/bin/bash
# Quick setup script for TLS certificates (Task 4)
# Sets up truststore and mTLS certificates for clients
# Run this: bash setup-certs-now.sh
# Note: Used in both Task 4 (TLS) and Task 5 (OIDC over TLS)

set -e

cd "$(dirname "$0")"

echo "=== Setting up client certificates ==="
echo ""

# Step 1: Extract CA certificate
echo "Step 1: Extracting CA certificate..."
mkdir -p certs
# Use TCC ingress certificate as trust anchor (contains the correct CA chain)
kubectl -n gruppe8-tcc get secret task4-gruppe8-tcc-ingress-tls -o jsonpath='{.data.tls\.crt}' | base64 -d > certs/ca.crt
echo "✓ TCC certificate extracted as trust anchor"

# Step 2: Convert to PKCS12
echo "Step 2: Converting CA to PKCS12..."
# Remove old keystore if it exists (keytool doesn't overwrite properly)
rm -f certs/ca.p12
# Use keytool to import CA certificate into PKCS12 truststore (Java-compatible)
keytool -importcert -alias ca -file certs/ca.crt -keystore certs/ca.p12 -storepass changeit -storetype PKCS12 -noprompt
echo "✓ CA converted to PKCS12"

# Step 3: Copy to all clients
echo "Step 3: Copying CA to all clients..."
mkdir -p clients/emergency-vehicle-client/src/main/resources/certs
mkdir -p clients/mayor-vehicle-client/src/main/resources/certs
mkdir -p clients/other-vehicle-client/src/main/resources/certs
mkdir -p clients/pedestrian-client/src/main/resources/certs

cp certs/ca.p12 clients/emergency-vehicle-client/src/main/resources/certs/
cp certs/ca.p12 clients/mayor-vehicle-client/src/main/resources/certs/
cp certs/ca.p12 clients/other-vehicle-client/src/main/resources/certs/
cp certs/ca.p12 clients/pedestrian-client/src/main/resources/certs/
echo "✓ CA copied to all clients"

# Step 4: Extract client certificate for mTLS
echo "Step 4: Extracting client certificate for mTLS..."
if kubectl -n gruppe8-tcc get secret task4-gruppe8-emergency-vehicle-client-tls &>/dev/null; then
    kubectl -n gruppe8-tcc get secret task4-gruppe8-emergency-vehicle-client-tls -o jsonpath='{.data.tls\.crt}' | base64 -d > certs/client.crt
    kubectl -n gruppe8-tcc get secret task4-gruppe8-emergency-vehicle-client-tls -o jsonpath='{.data.tls\.key}' | base64 -d > certs/client.key
    # Create PKCS12 with standard password (Quarkus requires explicit password)
    openssl pkcs12 -export -in certs/client.crt -inkey certs/client.key -out certs/client.p12 -name "client" -passout pass:changeit
    cp certs/client.p12 clients/emergency-vehicle-client/src/main/resources/certs/
    echo "✓ Client certificate extracted and copied"
else
    echo "⚠ Client certificate secret not found (may not be ready yet)"
fi

echo ""
echo "=== Setup Complete ==="
echo "Certificates are ready in:"
find clients -name "*.p12" -type f

