#!/bin/bash

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Get the directory where this script is located
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd "$SCRIPT_DIR"

echo -e "${YELLOW}=== Starting ALL TCC Microservices ===${NC}\n"

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo -e "${RED}Error: Maven (mvn) not found. Please install Maven first.${NC}"
    exit 1
fi

echo -e "${GREEN}Maven found: $(mvn -version | head -1)${NC}\n"
echo -e "${YELLOW}Starting services on different ports:${NC}"
echo "  Auth Service: 8081"
echo "  State Controller: 8082"
echo "  Status Service: 8083"
echo "  Time Service: 8084"
echo "  Traffic Light Device Service: 8085"
echo "  Priority Service: 8086"
echo ""
echo -e "${YELLOW}Each service will run in a separate terminal window${NC}\n"
echo -e "${YELLOW}Press Ctrl+C in each terminal to stop the service${NC}\n"

# Function to start a service in a new terminal window
start_service() {
    local service_name=$1
    local service_path=$2
    local port=$3
    
    echo -e "${GREEN}Starting ${service_name} on port ${port}...${NC}"
    
    if [[ "$OSTYPE" == "darwin"* ]]; then
        # macOS
        osascript -e "tell application \"Terminal\" to do script \"cd '${SCRIPT_DIR}/${service_path}' && echo 'Starting ${service_name} on port ${port}...' && mvn quarkus:dev\"" 2>/dev/null
    elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
        # Linux
        gnome-terminal -- bash -c "cd '${SCRIPT_DIR}/${service_path}' && echo 'Starting ${service_name} on port ${port}...' && mvn quarkus:dev; exec bash" 2>/dev/null || \
        xterm -e "cd '${SCRIPT_DIR}/${service_path}' && mvn quarkus:dev" 2>/dev/null || \
        echo "Please start manually: cd ${service_path} && mvn quarkus:dev"
    else
        echo "Please start manually in a new terminal:"
        echo "  cd ${service_path}"
        echo "  mvn quarkus:dev"
    fi
    
    sleep 3
}

# Start all services
start_service "Auth Service" "services/tcc-auth-service" "8081"
start_service "State Controller" "services/tcc-state-controller" "8082"
start_service "Status Service" "services/tcc-status-service" "8083"
start_service "Time Service" "services/time-service" "8084"
start_service "Traffic Light Device Service" "services/traffic-light-device-service" "8085"
start_service "Priority Service" "services/tcc-priority-service" "8086"

echo ""
echo -e "${GREEN}=== All Services Started ===${NC}"
echo ""
echo -e "${YELLOW}Wait 10-15 seconds for all services to start, then test with:${NC}"
echo "  ./test-endpoints.sh"
echo ""
echo -e "${YELLOW}Or test individual services:${NC}"
echo "  curl http://localhost:8081/api/auth/token -X POST -H 'Content-Type: application/json' -d '{\"clientId\":\"test\"}'"
echo "  curl http://localhost:8083/api/status/traffic"
echo "  curl http://localhost:8086/api/priority/requests -X POST -H 'Content-Type: application/json' -d '{\"vehicleType\":\"emergency\"}'"
echo ""
echo -e "${YELLOW}Swagger UI URLs:${NC}"
echo "  Auth: http://localhost:8081/q/swagger-ui"
echo "  State: http://localhost:8082/q/swagger-ui"
echo "  Status: http://localhost:8083/q/swagger-ui"
echo "  Time: http://localhost:8084/q/swagger-ui"
echo "  Device: http://localhost:8085/q/swagger-ui"
echo "  Priority: http://localhost:8086/q/swagger-ui"

