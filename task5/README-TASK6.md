# Task 6 - Status

## ✅ Was wurde umgesetzt

### Datenstrukturen (Task 6 Anforderung)
- **Common-Models Modul** erstellt: Alle Datenstrukturen zentral (kein Copy-Paste)
- **GPSCoordinate**: DMS-Format (Grad, Minute, Sekunde), WGS84-konform, Duckburg-Validierung
- **TrafficLightId**: GPS + UUID, validiert Duckburg-Koordinaten (37-38°N, 120-122°W)
- **TrafficStatus**: TrafficLightId + Timestamp + `lightStates` Map für einzelne Lichter
- **36 Unit Tests** (alle erfolgreich)

### Internes Routing
- **Priority Service**: Ruft intern Time, Location, State Controller, Audit auf
- **Status Service**: REST Clients injiziert, TODO: Implementierung durch Kumpel

### Warum so?
- **Common-Models**: Vermeidet Duplikation, zentrale Wartung
- **DMS-Format**: Task-Anforderung, verhindert 180°E/W Problem
- **Routing**: Vorbereitung für Logik, Security bereits da (OIDC + mTLS)

## ❌ Was fehlt noch

1. **GET /api/state/manual/{intersectionId}** (StateResource) - fehlt komplett
2. **PUT /api/state/manual/{intersectionId}** (StateResource) - fehlt komplett
3. **GET /api/status/traffic** - sollte Kreuzung mit ALLEN Ampeln zurückgeben
4. **Kreuzungs-Repräsentation** - wie mehrere Ampeln darstellen?
5. **GitLab Release** erstellen
6. **Test-Spezifikationen** für Security-Tests

## 🧪 Wie testen?

### Unit Tests
```bash
cd /Users/krzysztof/Documents/group8/task5
mvn test -pl common-models
# Erwartet: Tests run: 36, Failures: 0, Errors: 0, BUILD SUCCESS
```

### Manuelles Testprogramm
```bash
cd /Users/krzysztof/Documents/group8/task5
mvn compile -pl common-models
cd common-models
java -cp target/classes de.tub.aot.common.models.TestDataStructures
# Zeigt: GPSCoordinate, TrafficLightId, TrafficStatus Funktionalität
```


### Priority Service Routing
```bash
cd /Users/krzysztof/Documents/group8/task5
mvn clean compile -pl services/tcc-priority-service -am
# Prüft: REST Clients injiziert, kompiliert
```

## 📁 Struktur

```
task5/
├── common-models/              # NEU: Gemeinsame Datenstrukturen
│   └── GPSCoordinate, TrafficLightId, TrafficStatus
├── services/
│   ├── tcc-priority-service/  # ✅ Routing implementiert
│   └── tcc-status-service/    # ⚠️ TODO: Implementierung
└── clients/                   # ✅ Verwenden common-models
```

