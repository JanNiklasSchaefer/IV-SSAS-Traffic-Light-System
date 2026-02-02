# Priority Service Input Validation Tests

## ✅ SUCCESS Cases (Erwartet: 200 OK)

### 1. Gültige Emergency Vehicle Request

```bash
curl -v -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"emergency-vehicle","vehicleId":"emergency_001","trafficLightId":"da4f0053-a5c1-3882-a688-52ae2da2e466"}' \
  "https://tcc.test/api/priority/requests"

# Erwartet: {"status":"accepted","requestId":"uuid..."} oder {"status":"queued","requestId":"uuid..."}
```

### 2. Gültige Mayor Vehicle Request

```bash
curl -v -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"mayor-vehicle","vehicleId":"mayor_001","trafficLightId":"da4f0053-a5c1-3882-a688-52ae2da2e466"}' \
  "https://tcc.test/api/priority/requests"

# Erwartet: {"status":"accepted","requestId":"uuid..."} oder {"status":"queued","requestId":"uuid..."}
```

### 3. Gültige Other Vehicle Request

```bash
curl -v -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"other","vehicleId":"other_001","trafficLightId":"da4f0053-a5c1-3882-a688-52ae2da2e466"}' \
  "https://tcc.test/api/priority/requests"

# Erwartet: {"status":"accepted","requestId":"uuid..."} oder {"status":"queued","requestId":"uuid..."}
```

### 4. Gültiger GET Request

```bash
# Erst eine POST Request erstellen und die requestId aus der Response notieren
# Beispiel: {"status":"accepted","requestId":"123e4567-e89b-12d3-a456-426614174000"}

# Dann mit der echten UUID abfragen:
curl -v -X GET \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  "https://tcc.test/api/priority/requests/123e4567-e89b-12d3-a456-426614174000"

# Erwartet: {"status":"accepted","requestId":"123e4567-e89b-12d3-a456-426614174000"} oder {"status":"queued","requestId":"123e4567-e89b-12d3-a456-426614174000"}
```

## ❌ ERROR Cases (Erwartet: 200 OK mit "denied")

### 1. Leerer Request Body

```bash
curl -v -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '' \
  "https://tcc.test/api/priority/requests"

# Erwartet: {"status":"denied","requestId":"Request body is required or incomplete"}
```

### 2. Leere vehicleId

```bash
curl -v -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"emergency-vehicle","vehicleId":"","trafficLightId":"da4f0053-a5c1-3882-a688-52ae2da2e466"}' \
  "https://tcc.test/api/priority/requests"

# Erwartet: {"status":"denied","requestId":"Invalid or missing vehicleId"}
```

### 3. Nur Leerzeichen vehicleId

```bash
curl -v -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"emergency-vehicle","vehicleId":"   ","trafficLightId":"da4f0053-a5c1-3882-a688-52ae2da2e466"}' \
  "https://tcc.test/api/priority/requests"

# Erwartet: {"status":"denied","requestId":"Invalid or missing vehicleId"}
```

### 4. Ungültiger vehicleType

```bash
curl -v -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"ambulance","vehicleId":"test_1","trafficLightId":"da4f0053-a5c1-3882-a688-52ae2da2e466"}' \
  "https://tcc.test/api/priority/requests"

# Erwartet: {"status":"denied","requestId":"Invalid vehicleType. Allowed values: emergency-vehicle, mayor-vehicle, other"}
```

### 5. Leerer vehicleType

```bash
curl -v -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"","vehicleId":"test_1","trafficLightId":"da4f0053-a5c1-3882-a688-52ae2da2e466"}' \
  "https://tcc.test/api/priority/requests"

# Erwartet: {"status":"denied","requestId":"Invalid vehicleType. Allowed values: emergency-vehicle, mayor-vehicle, other"}
```

### 6. Ungültige trafficLightId (keine UUID)

```bash
curl -v -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"emergency-vehicle","vehicleId":"test_1","trafficLightId":"invalid-uuid"}' \
  "https://tcc.test/api/priority/requests"

# Erwartet: HTTP 400 Bad Request (JSON Parsing Error vom Framework)
```

### 7. Leere trafficLightId

```bash
curl -v -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"emergency-vehicle","vehicleId":"test_1","trafficLightId":""}' \
  "https://tcc.test/api/priority/requests"

# Erwartet: {"status":"denied","requestId":"Invalid or missing trafficLightId"}
```

### 8. Duplicate vehicleId

```bash
# Erste Request (success)
curl -v -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"emergency-vehicle","vehicleId":"duplicate_test","trafficLightId":"da4f0053-a5c1-3882-a688-52ae2da2e466"}' \
  "https://tcc.test/api/priority/requests"

# Zweite Request mit gleicher vehicleId (error)
curl -v -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"mayor-vehicle","vehicleId":"duplicate_test","trafficLightId":"da4f0053-a5c1-3882-a688-52ae2da2e466"}' \
  "https://tcc.test/api/priority/requests"

# Erwartet: {"status":"denied","requestId":"Request with ID: [uuid] already exists."}
```

### 9. Ungültige requestId im GET

```bash
curl -v -X GET \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  "https://tcc.test/api/priority/requests/invalid-request-id"

# Erwartet: {"status":"denied","requestId":"Invalid requestId format (must be valid UUID)"}
```

## 📊 Zusammenfassung der Validierungen

| Feld              | Validierung                                                        |
| ----------------- | ------------------------------------------------------------------ |
| `vehicleId`       | Nicht null, nicht leer, nicht nur Leerzeichen                      |
| `vehicleType`     | Muss einer von: "emergency-vehicle", "mayor-vehicle", "other" sein |
| `trafficLightId`  | Muss gültige UUID sein (Framework prüft Format)                    |
| `requestId` (GET) | Muss gültige UUID sein                                             |
| Duplicate Check   | Ein vehicleId darf nur eine aktive Request haben                   |

# Status Service Input Validation Tests

## ❌ ERROR Cases (Erwartet: 200 OK mit "denied")

### 1. Ungültige UUID

```bash
curl -v -X GET \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  "https://tcc.test/api/status/traffic?traffic-light-id=invalid-uuid"

# Erwartet: {"status":"denied","requestId":"Invalid traffic-light-id format (must be valid UUID)"}
```

### 2. Leerer Parameter

```bash
curl -v -X GET \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  "https://tcc.test/api/status/traffic?traffic-light-id="

# Erwartet: {"status":"denied","requestId":"Missing or empty traffic-light-id parameter"}
```

### 3. Fehlender Parameter

```bash
curl -v -X GET \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  "https://tcc.test/api/status/traffic"

# Erwartet: {"status":"denied","requestId":"Missing or empty traffic-light-id parameter"}
```

### 4. Nicht existierende Traffic Light

```bash
curl -v -X GET \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  "https://tcc.test/api/status/traffic?traffic-light-id=00000000-0000-0000-0000-000000000000"

# Erwartet: {"status":"not_found","requestId":"Traffic light not found"}
```

## 📊 Zusammenfassung

| Parameter          | Validierung                                      |
| ------------------ | ------------------------------------------------ |
| `traffic-light-id` | Erforderlich, nicht leer, muss gültige UUID sein |
| Traffic Light      | Muss existieren                                  |

**Alle Fehler geben HTTP 200 mit JSON Response zurück** (einheitlich mit Priority Service).
