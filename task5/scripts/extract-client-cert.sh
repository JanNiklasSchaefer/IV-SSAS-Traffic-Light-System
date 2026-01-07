#!/bin/bash
# Script to extract client certificate from Kubernetes cluster for mTLS
# Usage: ./extract-client-cert.sh [output-dir]
# Can be run from task4/scripts/ or task4/ directory

# Get the directory where the script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# Get task4 directory (parent of scripts)
TASK4_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

OUTPUT_DIR=${1:-$TASK4_DIR/certs}
NAMESPACE=gruppe8-tcc
SECRET_NAME=task4-gruppe8-emergency-vehicle-client-tls

echo "Extracting client certificate from Kubernetes cluster..."
echo "Namespace: $NAMESPACE"
echo "Secret: $SECRET_NAME"
echo "Output directory: $OUTPUT_DIR"

mkdir -p "$OUTPUT_DIR"

# Extract certificate and key
kubectl -n $NAMESPACE get secret $SECRET_NAME -o jsonpath='{.data.tls\.crt}' | base64 -d > "$OUTPUT_DIR/client.crt"
kubectl -n $NAMESPACE get secret $SECRET_NAME -o jsonpath='{.data.tls\.key}' | base64 -d > "$OUTPUT_DIR/client.key"

if [ $? -eq 0 ]; then
    echo "✓ Client certificate extracted to $OUTPUT_DIR/client.crt"
    echo "✓ Client key extracted to $OUTPUT_DIR/client.key"
    echo ""
    echo "Creating PKCS12 keystore for Quarkus REST Client..."
    
    # Create PKCS12 keystore with standard password (Quarkus requires explicit password)
    openssl pkcs12 -export \
        -in "$OUTPUT_DIR/client.crt" \
        -inkey "$OUTPUT_DIR/client.key" \
        -out "$OUTPUT_DIR/client.p12" \
        -name "client" \
        -passout pass:changeit
    
    if [ $? -eq 0 ]; then
        echo "✓ PKCS12 keystore created at $OUTPUT_DIR/client.p12"
        echo ""
        echo "Certificate details:"
        openssl x509 -in "$OUTPUT_DIR/client.crt" -noout -subject -issuer -dates
    else
        echo "✗ Failed to create PKCS12 keystore"
        exit 1
    fi
else
    echo "✗ Failed to extract client certificate"
    exit 1
fi

