# Testing Guide für Task 4

Diese Anleitung zeigt dir, wie du alle Services und Clients testen kannst.

## 🚀 Schnellstart: Lokales Testen (Empfohlen für Entwicklung)

### Option 1: Service in Dev Mode + Client

**Schritt 1: Service starten**

```bash
# Terminal 1: Priority Service starten
cd task4/services/tcc-priority-service
mvn compile quarkus:dev
```

Der Service läuft jetzt auf `http://localhost:8080`

**Schritt 2: Client testen**

```bash
# Terminal 2: Emergency Vehicle Client
cd task4/clients/emergency-vehicle-client
mvn compile exec:java -Dexec.mainClass="de.tub.aot.client.EmergencyVehicleClient"

# Oder andere Clients:
cd task4/clients/mayor-vehicle-client
mvn compile exec:java -Dexec.mainClass="de.tub.aot.client.MayorVehicleClient"

cd task4/clients/other-vehicle-client
mvn compile exec:java -Dexec.mainClass="de.tub.aot.client.OtherVehicleClient"

cd task4/clients/pedestrian-client
mvn compile exec:java -Dexec.mainClass="de.tub.aot.client.PedestrianClient"
```

### Option 2: Alle Services bauen und testen

```bash
# Alle Services und Clients kompilieren
cd task4
mvn clean install

# Prüfen ob alles kompiliert wurde
echo "✅ Build erfolgreich!"
```

---

## 🐳 Kubernetes Deployment und Testen

### Voraussetzungen

1. **Kubernetes Cluster** muss laufen (z.B. kind, minikube)
2. **Docker** muss laufen
3. **kubectl** muss konfiguriert sein

### Schritt-für-Schritt Deployment

**1. Namespaces erstellen**

```bash
cd task4
kubectl apply -f kubernetes/namespaces/gruppe8-tcc.yaml
```

**2. Ingress deployen**

```bash
kubectl apply -f kubernetes/ingress.yaml
```

**3. Services bauen und deployen**

```bash
# Alle Services bauen und automatisch deployen
mvn clean package -Dquarkus.kubernetes.deploy=true
```

**4. Deployment prüfen**

```bash
# Alle Pods prüfen
kubectl get pods -n gruppe8-tcc
kubectl get pods -n gruppe8-shared-services
kubectl get pods -n gruppe8-traffic-light-devices

# Services prüfen
kubectl get services -A | grep gruppe8

# Ingress prüfen
kubectl get ingress -n gruppe8-tcc
```

**5. Automatische Endpoint-Tests**

```bash
# Test-Script ausführen (testet alle Endpoints)
cd task4
./test-endpoints.sh
```

---

## 🧪 Client-Tests mit Kubernetes

### Option A: Port-Forward (Einfach)

**Terminal 1: Port-Forward einrichten**

```bash
# Priority Service
kubectl port-forward -n gruppe8-tcc service/gruppe8-tcc-priority-service 8080:80

# Status Service (in separatem Terminal)
kubectl port-forward -n gruppe8-tcc service/gruppe8-tcc-status-service 8081:80
```

**Terminal 2: Client ausführen**

```bash
cd task4/clients/emergency-vehicle-client
mvn compile exec:java -Dexec.mainClass="de.tub.aot.client.EmergencyVehicleClient"
# Verwendet automatisch http://localhost:8080
```

### Option B: Ingress (Produktions-ähnlich)

**1. `/etc/hosts` konfigurieren**

```bash
# macOS/Linux
sudo nano /etc/hosts
# Diese Zeile hinzufügen:
127.0.0.1 tcc.test
```

**2. Ingress testen**

```bash
# Test ob Ingress funktioniert
curl http://tcc.test/api/priority/requests -X POST \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"emergency"}'

curl http://tcc.test/api/status/traffic?vehicle=true
```

**3. Client mit Ingress ausführen**

```bash
cd task4/clients/emergency-vehicle-client
mvn compile exec:java \
  -Dexec.mainClass="de.tub.aot.client.EmergencyVehicleClient" \
  -Dbase.url=http://tcc.test
```

---

## 🔍 Manuelle Tests

### Service-Endpoints direkt testen

**Priority Service:**

```bash
# Priority Request erstellen
curl -X POST http://localhost:8080/api/priority/requests \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"emergency"}'

# Request Status abfragen (mit requestId aus vorheriger Antwort)
curl http://localhost:8080/api/priority/requests/{requestId}
```

**Status Service:**

```bash
# Traffic Status abfragen
curl "http://localhost:8080/api/status/traffic?vehicle=true"
```

**Location Validator:**

```bash
curl -X POST http://localhost:8080/api/location/vehicle \
  -H "Content-Type: application/json" \
  -d '{
    "vehicleId": "test-vehicle",
    "coordinates": {
      "latitude": 52.5,
      "longitude": 13.4
    }
  }'
```

**Time Service:**

```bash
curl -X POST http://localhost:8080/api/time/validate \
  -H "Content-Type: application/json" \
  -d '{"timestamp": '$(date +%s000)'}'
```

---

## ✅ Checkliste für erfolgreiches Testen

- [ ] Alle Services kompilieren ohne Fehler (`mvn clean install`)
- [ ] Service in Dev Mode startet (`mvn quarkus:dev`)
- [ ] Client kann Service erreichen (keine Connection Errors)
- [ ] Kubernetes Cluster läuft (`kubectl cluster-info`)
- [ ] Namespaces erstellt (`kubectl get namespaces | grep gruppe8`)
- [ ] Pods laufen (`kubectl get pods -A | grep gruppe8`)
- [ ] Services erreichbar (`kubectl get services -A | grep gruppe8`)
- [ ] Ingress konfiguriert (`kubectl get ingress -n gruppe8-tcc`)
- [ ] `/etc/hosts` konfiguriert (falls Ingress verwendet)
- [ ] Test-Script läuft ohne Fehler (`./test-endpoints.sh`)

---

## 🐛 Troubleshooting

### Problem: "Connection refused" beim Client

**Lösung:**

- Prüfe ob Service läuft: `lsof -i :8080`
- Prüfe ob Port-Forward aktiv ist
- Prüfe ob `/etc/hosts` korrekt konfiguriert ist

### Problem: "Could not resolve host: tcc.test"

**Lösung:**

```bash
# /etc/hosts prüfen
cat /etc/hosts | grep tcc.test

# Falls nicht vorhanden, hinzufügen:
echo "127.0.0.1 tcc.test" | sudo tee -a /etc/hosts
```

### Problem: Pods starten nicht

**Lösung:**

```bash
# Pod-Logs prüfen
kubectl logs -n gruppe8-tcc <pod-name>

# Pod-Status prüfen
kubectl describe pod -n gruppe8-tcc <pod-name>
```

### Problem: Build-Fehler

**Lösung:**

```bash
# Clean Build
cd task4
mvn clean install

# Falls Docker-Problem:
mvn clean package -Dquarkus.container-image.build=false
```

---

## 📊 Erwartete Ergebnisse

### Client-Output (erfolgreich)

```
=== Emergency Vehicle Client ===
Using base URL: http://localhost:8080

--- GET /api/status/traffic?vehicle=true ---
Status: 200 OK
State: green
Timestamp: 2024-01-01T12:00:00Z

--- POST /api/priority/requests ---
Request: emergency
Status: 200 OK
Response status: accepted
Request ID: 123e4567-e89b-12d3-a456-426614174000

--- GET /api/priority/requests/{requestId} ---
Status: 200 OK
Request ID: 123e4567-e89b-12d3-a456-426614174000
Status: processing
Vehicle Type: emergency

=== Demo finished ===
```

### Test-Script Output (erfolgreich)

```
=== Testing TCC Microservices Endpoints ===

Testing: Get Traffic Status (vehicle)
  GET http://...
  ✓ Status: 200

Testing: Post Priority Request
  POST http://...
  ✓ Status: 200

...
```

---

## 🎯 Nächste Schritte

Nach erfolgreichem Testen kannst du:

- Services erweitern (neue Endpoints)
- Clients anpassen (neue Features)
- Integration mit Keycloak (Task 5)
- Monitoring und Logging hinzufügen
