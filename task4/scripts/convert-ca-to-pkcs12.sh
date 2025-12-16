#!/bin/bash
# Script to convert CA certificate from PEM to PKCS12 for Java clients
# Usage: ./convert-ca-to-pkcs12.sh [ca-cert-path] [output-path]

# Get the directory where the script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
# Get task4 directory (parent of scripts)
TASK4_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

CA_CERT_PATH=${1:-$TASK4_DIR/certs/ca.crt}
OUTPUT_PATH=${2:-$TASK4_DIR/certs/ca.p12}

if [ ! -f "$CA_CERT_PATH" ]; then
    echo "✗ CA certificate not found at: $CA_CERT_PATH"
    echo "Please run extract-ca-cert.sh first or provide the path to ca.crt"
    exit 1
fi

echo "Converting CA certificate from PEM to PKCS12..."
echo "Input: $CA_CERT_PATH"
echo "Output: $OUTPUT_PATH"

# Remove old keystore if it exists (keytool doesn't overwrite properly)
rm -f "$OUTPUT_PATH"

# Convert PEM to PKCS12 using keytool (Java-compatible, ensures certificate is properly imported)
keytool -importcert \
    -alias ca \
    -file "$CA_CERT_PATH" \
    -keystore "$OUTPUT_PATH" \
    -storepass changeit \
    -storetype PKCS12 \
    -noprompt

if [ $? -eq 0 ]; then
    echo "✓ CA certificate converted to PKCS12 at $OUTPUT_PATH"
    echo ""
    echo "Certificate details:"
    openssl x509 -in "$CA_CERT_PATH" -noout -subject -issuer -dates
else
    echo "✗ Failed to convert CA certificate"
    exit 1
fi

