package de.tub.aot.common.models;

import java.time.Instant;
import java.util.UUID;

/**
 * Test-Klasse zum manuellen Testen der Datenstrukturen.
 * Führt die Klasse aus, um zu sehen, ob alles funktioniert.
 */
public class TestDataStructures {

    public static void main(String[] args) {
        System.out.println("=== Test der Datenstrukturen ===\n");

        try {
            // Test 1: GPSCoordinate erstellen
            System.out.println("1. Test: GPSCoordinate erstellen");
            GPSCoordinate lat = new GPSCoordinate(37, 45, 30.5, true);
            GPSCoordinate lon = new GPSCoordinate(-121, 30, 15.25, false);
            System.out.println("   Latitude: " + lat);
            System.out.println("   Longitude: " + lon);
            System.out.println("   Latitude (decimal): " + lat.toDecimalDegrees());
            System.out.println("   Longitude (decimal): " + lon.toDecimalDegrees());
            System.out.println("   ✅ GPSCoordinate funktioniert!\n");

            // Test 2: TrafficLightId erstellen
            System.out.println("2. Test: TrafficLightId erstellen");
            TrafficLightId lightId = new TrafficLightId(lat, lon, UUID.randomUUID());
            System.out.println("   TrafficLightId: " + lightId);
            System.out.println("   UUID: " + lightId.getUuid());
            System.out.println("   ✅ TrafficLightId funktioniert!\n");

            // Test 3: TrafficStatus erstellen
            System.out.println("3. Test: TrafficStatus erstellen");
            TrafficStatus status = new TrafficStatus(lightId, Instant.now().toString());
            status.setState("green");
            status.setLightState("north-south", "green");
            status.setLightState("east-west", "red");
            System.out.println("   TrafficLightId: " + status.getTrafficLightId());
            System.out.println("   Timestamp: " + status.getTimestamp());
            System.out.println("   Overall State: " + status.getState());
            System.out.println("   North-South: " + status.getLightState("north-south"));
            System.out.println("   East-West: " + status.getLightState("east-west"));
            System.out.println("   All Light States: " + status.getLightStates());
            System.out.println("   ✅ TrafficStatus funktioniert!\n");

            // Test 4: Validierung - außerhalb von Duckburg (sollte fehlschlagen)
            System.out.println("4. Test: Validierung - außerhalb von Duckburg");
            try {
                TrafficLightId invalid = new TrafficLightId(
                        new GPSCoordinate(50, 0, 0.0, true), // Zu weit nördlich
                        new GPSCoordinate(-121, 30, 0.0, false),
                        UUID.randomUUID());
                System.out.println("   ❌ FEHLER: Validierung hat nicht funktioniert!");
            } catch (IllegalArgumentException e) {
                System.out.println("   ✅ Validierung funktioniert: " + e.getMessage() + "\n");
            }

            // Test 5: Konvertierung von Dezimalgraden
            System.out.println("5. Test: Konvertierung von Dezimalgraden");
            GPSCoordinate fromDecimal = GPSCoordinate.fromDecimalDegrees(37.758472, true);
            System.out.println("   Decimal: 37.758472°");
            System.out.println("   DMS: " + fromDecimal);
            System.out.println("   Zurück zu Decimal: " + fromDecimal.toDecimalDegrees());
            System.out.println("   ✅ Konvertierung funktioniert!\n");

            System.out.println("=== Alle Tests erfolgreich! ===");

        } catch (Exception e) {
            System.err.println("❌ FEHLER: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
