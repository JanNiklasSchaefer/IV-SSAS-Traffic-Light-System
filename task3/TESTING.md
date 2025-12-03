# Endpunkt-Testing Anleitung

## Services starten

### Option 1: Einzelne Services starten (in separaten Terminals)

```bash
# Terminal 1: Auth Service
cd task3/services/tcc-auth-service
../../mvnw quarkus:dev

# Terminal 2: State Controller
cd task3/services/tcc-state-controller
../../mvnw quarkus:dev

# Terminal 3: Status Service
cd task3/services/tcc-status-service
../../mvnw quarkus:dev

# Terminal 4: Time Service
cd task3/services/time-service
../../mvnw quarkus:dev
```

**Hinweis:** Alle Services laufen standardmäßig auf Port `8080`. Wenn mehrere gleichzeitig laufen sollen, müssen die Ports in den `application.properties` angepasst werden.

### Option 2: Ports anpassen für paralleles Laufen

Füge in jeder `application.properties` hinzu:

```properties
quarkus.http.port=8081  # für Auth Service
quarkus.http.port=8082  # für State Controller
quarkus.http.port=8083  # für Status Service
quarkus.http.port=8084  # für Time Service
```

## Endpunkte testen

### Automatisches Test-Script

```bash
# Alle Endpunkte testen
cd task3
./test-endpoints.sh
```

### Manuelle Tests mit curl

#### 1. Auth Service

```bash
# Token anfordern
curl -X POST http://localhost:8080/api/auth/token \
  -H "Content-Type: application/json" \
  -d '{
    "clientId": "test-client",
    "clientSecret": "test-secret",
    "username": "test",
    "password": "test",
    "grant_type": "password"
  }'

# Token validieren
curl -X POST http://localhost:8080/api/auth/introspect \
  -H "Content-Type: application/json" \
  -d '"test-token"'

# User Info abrufen
curl -X POST http://localhost:8080/api/auth/userinfo \
  -H "Content-Type: application/json" \
  -d '"access-token-here"'
```

#### 2. State Controller

```bash
# State ändern
curl -X POST http://localhost:8080/api/state/change \
  -H "Content-Type: application/json" \
  -d '{"command": "change-to-red"}'

# Device State ändern
curl -X POST http://localhost:8080/api/state/device/change-state \
  -H "Content-Type: application/json" \
  -d '{"state": "red"}'

# Audit Event loggen
curl -X POST http://localhost:8080/api/state/audit/events \
  -H "Content-Type: application/json" \
  -d '{
    "request": {"id": "123"},
    "timestamp": "2024-01-01T12:00:00Z"
  }'
```

#### 3. Status Service

```bash
# Traffic Status abrufen
curl -X GET http://localhost:8080/api/status/traffic

# Traffic Status mit Vehicle Parameter
curl -X GET "http://localhost:8080/api/status/traffic?vehicle=true"
```

#### 4. Time Service

```bash
# Timestamp validieren
TIMESTAMP=$(date +%s)
curl -X POST "http://localhost:8080/api/time/vehicle?timestamp=${TIMESTAMP}" \
  -H "Content-Type: application/json"
```

## OpenAPI/Swagger UI

Jeder Quarkus Service bietet eine Swagger UI:

- Auth Service: http://localhost:8080/q/swagger-ui
- State Controller: http://localhost:8080/q/swagger-ui
- Status Service: http://localhost:8080/q/swagger-ui
- Time Service: http://localhost:8080/q/swagger-ui

## Integrationstests

Um zu testen, ob die Services zusammenarbeiten:

1. **Auth → State Flow:**

   ```bash
   # 1. Token holen
   TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/token \
     -H "Content-Type: application/json" \
     -d '{"clientId":"test","clientSecret":"test"}' | jq -r '.accessToken')

   # 2. State ändern (mit Token im Header, falls implementiert)
   curl -X POST http://localhost:8080/api/state/change \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer $TOKEN" \
     -d '{"command": "change-to-green"}'
   ```

2. **Status → State Flow:**

   ```bash
   # 1. Status abrufen
   STATUS=$(curl -s http://localhost:8080/api/status/traffic)

   # 2. Basierend auf Status State ändern
   curl -X POST http://localhost:8080/api/state/device/change-state \
     -H "Content-Type: application/json" \
     -d '{"state": "yellow"}'
   ```

## Troubleshooting

- **Port bereits belegt:** Ändere den Port in `application.properties` oder beende den anderen Prozess
- **Service startet nicht:** Prüfe die Logs im Terminal
- **404 Not Found:** Prüfe, ob der Service läuft und der Pfad korrekt ist
- **Connection refused:** Service läuft nicht oder auf falschem Port

## Nützliche Tools

- **jq** für JSON-Formatierung: `curl ... | jq`
- **httpie** als curl-Alternative: `http POST localhost:8080/api/auth/token clientId=test`
- **Postman** für GUI-basiertes Testing
- **Insomnia** als Alternative zu Postman
