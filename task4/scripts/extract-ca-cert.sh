#!/bin/bash
# Script to extract CA certificate from Kubernetes cluster for local client testing
# Usage: ./extract-ca-cert.sh [output-dir]
# Can be run from task4/scripts/ or task4/ directory

# Get the directory where the script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# Get task4 directory (parent of scripts)
TASK4_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

OUTPUT_DIR=${1:-$TASK4_DIR/certs}
NAMESPACE=cert-manager
SECRET_NAME=ha1-gruppe8-krzysztoflagowski-ca-secret

echo "Extracting CA certificate from Kubernetes cluster..."
echo "Namespace: $NAMESPACE"
echo "Secret: $SECRET_NAME"
echo "Output directory: $OUTPUT_DIR"

mkdir -p "$OUTPUT_DIR"

# Extract CA certificate
kubectl -n $NAMESPACE get secret $SECRET_NAME -o jsonpath='{.data.ca\.crt}' | base64 -d > "$OUTPUT_DIR/ca.crt"

if [ $? -eq 0 ]; then
    echo "✓ CA certificate extracted to $OUTPUT_DIR/ca.crt"
    echo ""
    echo "Certificate details:"
    openssl x509 -in "$OUTPUT_DIR/ca.crt" -noout -subject -issuer -dates
else
    echo "✗ Failed to extract CA certificate"
    exit 1
fi
