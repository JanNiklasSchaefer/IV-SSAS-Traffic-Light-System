#!/bin/bash
# Run Emergency Vehicle Client with secrets from .env
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
    export CLIENT_SECRET="${EMERGENCY_VEHICLE_CLIENT_SECRET}"
else
    echo "❌ Error: .env file not found at ${ENV_FILE}"
    echo "Please copy .env.example to .env: cd .. && cp .env.example .env"
    exit 1
fi

if [ -z "$CLIENT_SECRET" ]; then
    echo "❌ Error: EMERGENCY_VEHICLE_CLIENT_SECRET not found in .env"
    exit 1
fi

# Build
echo "Building Emergency Vehicle Client..."
mvn clean package -DskipTests

# Run
echo "Starting Emergency Vehicle Client..."
java -Dbase.url=https://tcc.test -jar target/quarkus-app/quarkus-run.jar

