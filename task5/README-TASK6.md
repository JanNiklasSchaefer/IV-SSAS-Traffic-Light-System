# Task 6 - Status

## ✅ Was wurde umgesetzt

### Datenstrukturen (Task 6 Anforderung)
- **Common-Models Modul** erstellt: Alle Datenstrukturen zentral (kein Copy-Paste)
- **GPSCoordinate**: DMS-Format (Grad, Minute, Sekunde), WGS84-konform, Duckburg-Validierung
- **TrafficLightId**: GPS + UUID, validiert Duckburg-Koordinaten (37-38°N, 120-122°W)
- **TrafficStatus**: TrafficLightId + Timestamp + `lightStates` Map für einzelne Lichter
- **36 Unit Tests** (alle erfolgreich)


- **Referenzen laut Aufgabenstellung (Clickable/Docs):**
    - [Geographic Coordinate System - Wikipedia](https://en.wikipedia.org/wiki/Geographic_coordinate_system)
    - [WGS84 - Wikipedia](https://en.wikipedia.org/wiki/World_Geodetic_System#WGS84)  


### Internes Routing
- **Priority Service**: Ruft intern Time, Location, State Controller, Audit auf ✅ (SSL-Probleme behoben!)
- **State Controller**: Ruft intern TrafficLightDeviceService und Audit Service auf ✅
- **Status Service**: REST Clients injiziert, TODO: Implementierung durch Kumpel

### Warum so?
- **Common-Models**: Vermeidet Duplikation, zentrale Wartung
- **DMS-Format**: Task-Anforderung, verhindert 180°E/W Problem
- **Routing**: Vorbereitung für Logik, Security bereits da (OIDC + mTLS)

## ❌ Was fehlt noch

1. ✅ ~~**GET /api/state/manual/{intersectionId}** (StateResource)~~ - **FERTIG**
2. ✅ ~~**PUT /api/state/manual/{intersectionId}** (StateResource)~~ - **FERTIG**
3. ✅ ~~**SSL-Verbindungsprobleme behoben** - Priority Service kann jetzt intern kommunizieren~~ - **FERTIG**
4. **GET /api/status/traffic** - sollte Kreuzung mit ALLEN Ampeln zurückgeben (Kumpel macht)
5. **Kreuzungs-Repräsentation** - wie mehrere Ampeln darstellen? (muss geklärt werden)
6. **GitLab Release** erstellen
7. **Test-Spezifikationen** für Security-Tests (jeder einzeln)

## 🧪 Wie testen?

### 1. Datenstrukturen (Unit Tests)
```bash
cd /Users/krzysztof/Documents/group8/task5
mvn test -pl common-models
# Erwartet: Tests run: 36, Failures: 0, Errors: 0, BUILD SUCCESS
```

### 2. Datenstrukturen (Manuelles Testprogramm)
```bash
cd /Users/krzysztof/Documents/group8/task5
mvn compile -pl common-models
cd common-models
java -cp target/classes de.tub.aot.common.models.TestDataStructures
# Zeigt: GPSCoordinate, TrafficLightId, TrafficStatus Funktionalität
```

### 3. Priority Service - Internes Routing

**Code-Check:**
```bash
cd /Users/krzysztof/Documents/group8/task5
mvn clean compile -pl services/tcc-priority-service -am
# Prüft: REST Clients injiziert, kompiliert
```

**internes Routing in Kubernetes testen:**

**Option A: Über Traffic Management Center Client (EINFACHSTER WEG)**
```bash
# 1. Client bauen und starten (INTERAKTIV, nicht Demo-Modus!)
cd task5/clients/traffic-management-center-client

# CLIENT_SECRET aus .env laden
export CLIENT_SECRET=$(grep TRAFFIC_MANAGEMENT_CENTER_CLIENT_SECRET ../.env | cut -d '=' -f2)

# Client bauen
mvn clean package -DskipTests

# Client starten (INTERAKTIV - kein "demo" Argument!)
java -Dbase.url=https://tcc.test -jar target/quarkus-app/quarkus-run.jar

# 2. Im Client-Menü: Option "2" eingeben (POST /api/priority/requests)
kubectl logs -f -n gruppe8-tcc deployment/gruppe8-tcc-priority-service | grep -E "(Priority Request|Time Service|Location|State Controller|Audit Service|Calling)"

# Erwartete Log-Ausgaben (SSL-Fehler sollten jetzt behoben sein!):
# - "=== Priority Request received: <requestId> ==="
# - "Calling Time Service..."
# - "Time Service Response: OK" (oder ähnlich)
# - "Calling Location Validator..."
# - "Location Validator Response: OK" (oder ähnlich)
# - "Calling State Controller..."
# - "State Controller Response: <status>" (oder ähnlich)
# - "Calling Audit Service..."
# - "Audit Service Response: <status>" (oder ähnlich)
# - "=== Priority Request processing completed: <requestId> ==="
#
# HINWEIS: Falls OIDC-Fehler erscheinen ("unauthorized_client"), bedeutet das, dass
# die OIDC-Konfiguration nicht korrekt ist. Das Routing wird trotzdem versucht!
# Die SSL-Verbindungen zu internen Services sollten jetzt funktionieren.
```

**Was das zeigt:**
- ✅ REST Clients werden injiziert
- ✅ Services werden aufgerufen (Routing funktioniert)
- ✅ Exception Handling funktioniert


### FUNKTIONIERT NOCH NICHT

### 4. State Controller - Internes Routing

**Code-Check:**
```bash
cd /Users/krzysztof/Documents/group8/task5
mvn clean compile -pl services/tcc-state-controller -am
# Prüft: GET/PUT /api/state/manual/{intersectionId} implementiert, kompiliert
```

**Internes Routing in Kubernetes testen:**

**Option A: Über Traffic Management Center Client (EINFACHSTER WEG)**
```bash
# 1. Client bauen und starten (INTERAKTIV, nicht Demo-Modus!)
cd task5/clients/traffic-management-center-client

# CLIENT_SECRET aus .env laden
export CLIENT_SECRET=$(grep TRAFFIC_MANAGEMENT_CENTER_CLIENT_SECRET ../.env | cut -d '=' -f2)

# Client bauen
mvn clean package -DskipTests

# Client starten (INTERAKTIV - kein "demo" Argument!)
java -Dbase.url=https://tcc.test -jar target/quarkus-app/quarkus-run.jar

# 2. Im Client-Menü:
#    - Option "4" eingeben (GET /api/state/manual/{intersectionId}) z.B. "4 intersection-001"
#    - Option "5" eingeben (PUT /api/state/manual/{intersectionId}) z.B. "5 intersection-001 red"

# 3. In anderem Terminal: Logs prüfen
kubectl logs -f -n gruppe8-tcc deployment/gruppe8-tcc-state-controller | grep -E "(GET|PUT|Intersection State|Traffic Light|Audit Service|Calling)"

# Erwartete Log-Ausgaben:
# GET:
# - "=== GET Intersection State: intersection-001 ==="
# - "Calling Traffic Light Device Service..."
# - "Calling Audit Service..."
# - "=== GET Intersection State completed: intersection-001 ==="
#
# PUT:
# - "=== PUT Intersection State: intersection-001 to red ==="
# - "Calling Traffic Light Device Service..."
# - "Calling Audit Service..."
# - "=== PUT Intersection State completed: intersection-001 ==="
#
# HINWEIS: Falls OIDC-Fehler erscheinen ("unauthorized_client"), bedeutet das, dass
# CLIENT_SECRET im Kubernetes Deployment fehlt. Das Routing wird trotzdem versucht!
```

**Was das zeigt:**
- ✅ GET/PUT Endpoints funktionieren
- ✅ Internes Routing zu TrafficLightDeviceService
- ✅ Internes Routing zu Audit Service


## 📁 Struktur

```
task5/
├── common-models/              # NEU: Gemeinsame Datenstrukturen
│   └── GPSCoordinate, TrafficLightId, TrafficStatus
├── services/
│   ├── tcc-priority-service/  # ✅ Routing implementiert
│   ├── tcc-state-controller/  # ✅ GET/PUT /api/state/manual implementiert
│   └── tcc-status-service/    # ⚠️ TODO: Implementierung (Kumpel)
└── clients/                   # ✅ Verwenden common-models
```

