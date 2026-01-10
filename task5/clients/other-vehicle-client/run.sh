#!/bin/bash
# Run Other Vehicle Client with secrets from .env
set -e

# Get script directory and go to client directory
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

# Load .env file from parent directory
ENV_FILE="../.env"
if [ -f "$ENV_FILE" ]; then
    set -a
    source "$ENV_FILE"
    set +a
    export CLIENT_SECRET="${OTHER_VEHICLE_CLIENT_SECRET}"
else
    echo "❌ Error: .env file not found at ${ENV_FILE}"
    echo "Please copy .env.example to .env: cd .. && cp .env.example .env"
    exit 1
fi

if [ -z "$CLIENT_SECRET" ]; then
    echo "❌ Error: OTHER_VEHICLE_CLIENT_SECRET not found in .env"
    exit 1
fi

# Build
echo "Building Other Vehicle Client..."
mvn clean package -DskipTests

# Run
echo "Starting Other Vehicle Client..."
java -Dbase.url=https://tcc.test -jar target/quarkus-app/quarkus-run.jar

