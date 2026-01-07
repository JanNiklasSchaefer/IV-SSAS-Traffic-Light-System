# 🧪 TLS / mTLS Testing Cheat Sheet (Gruppe 8)

This document contains all commands required to:
- Create a TLS debug pod
- Obtain service tokens from Keycloak
- Call all protected services using mTLS + Bearer tokens
- Clean up resources

---

## 1️⃣ Create TLS Debug Pod

```bash
kubectl -n gruppe8-testing run tls-debug \
  --image=curlimages/curl:8.5.0 \
  --restart=Never \
  --overrides='
{
  "spec": {
    "containers": [{
      "name": "curl",
      "image": "curlimages/curl:8.5.0",
      "command": ["sh","-c","sleep 3600"],
      "stdin": true,
      "tty": true,
      "volumeMounts": [
        {"name":"certs","mountPath":"/etc/tls","readOnly":true}
      ]
    }],
    "volumes": [
      {"name":"certs","secret":{"secretName":"gruppe8-task4-testing-cert-tls"}}
    ]
  }
}'
```

---

## 2️⃣ Exec into Debug Pod

```bash
kubectl -n gruppe8-testing exec -it tls-debug -- sh
```

---

## 3️⃣ Token: tcc-state-controller

```bash
TOKEN=$(curl -s \
  -d grant_type=client_credentials \
  -d client_id=tcc-state-controller \
  -d client_secret=MEb3nfjgTGkEHiZs8NugXxKTf2UqHdVC \
  http://keycloak-service.keycloak.svc.cluster.local:8080/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

echo "LEN=${#TOKEN}"
```

### Call: Traffic Light Device

```bash
curl -v -X POST \
  --cacert /etc/tls/ca.crt \
  --cert /etc/tls/tls.crt \
  --key /etc/tls/tls.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{}' \
  "https://gruppe8-traffic-light-device-service.gruppe8-traffic-light-devices.svc.cluster.local:443/api/device/change-state?state=green"
```

### Call: Audit – Create Event

```bash
curl -v -X POST \
  --cacert /etc/tls/ca.crt \
  --cert /etc/tls/tls.crt \
  --key /etc/tls/tls.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
        "service": "tcc-priority-service",
        "eventType": "STATE_CHANGE",
        "state": "green",
        "timestamp": 1767818200000
      }' \
  "https://gruppe8-tcc-audit-service.gruppe8-tcc.svc.cluster.local:443/api/audit/events"
```

### Call: Audit – Read Logs

```bash
curl -v \
  --cacert /etc/tls/ca.crt \
  --cert /etc/tls/tls.crt \
  --key /etc/tls/tls.key \
  -H "Authorization: Bearer $TOKEN" \
  "https://gruppe8-tcc-audit-service.gruppe8-tcc.svc.cluster.local:443/api/audit/logs?from=0&to=9999999999999"
```

---

## 4️⃣ Token: tcc-status-service

```bash
TOKEN=$(curl -s \
  -d grant_type=client_credentials \
  -d client_id=tcc-status-service \
  -d client_secret=u7dV5aMSxMpRrGL2BezDnMf1uQFIULkQ \
  http://keycloak-service.keycloak.svc.cluster.local:8080/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

echo "LEN=${#TOKEN}"
```

### Call: Traffic Light Device

```bash
curl -v -X POST \
  --cacert /etc/tls/ca.crt \
  --cert /etc/tls/tls.crt \
  --key /etc/tls/tls.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{}' \
  "https://gruppe8-traffic-light-device-service.gruppe8-traffic-light-devices.svc.cluster.local:443/api/device/change-state?state=green"
```

### Call: Time Service

```bash
curl -v -X POST \
  --cacert /etc/tls/ca.crt \
  --cert /etc/tls/tls.crt \
  --key /etc/tls/tls.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"timestamp":1700000000000}' \
  "https://gruppe8-time-service.gruppe8-shared-services.svc.cluster.local:443/api/time/validate"
```

---

## 5️⃣ Token: tcc-priority-service

```bash
TOKEN=$(curl -s \
  -d grant_type=client_credentials \
  -d client_id=tcc-priority-service \
  -d client_secret=ORzb7Qc6FxG82m1GJjDTwPZmcmwNtPJb \
  http://keycloak-service.keycloak.svc.cluster.local:8080/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

echo "LEN=${#TOKEN}"
```

### Call: Time Service

```bash
curl -v -X POST \
  --cacert /etc/tls/ca.crt \
  --cert /etc/tls/tls.crt \
  --key /etc/tls/tls.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"timestamp":1700000000000}' \
  "https://gruppe8-time-service.gruppe8-shared-services.svc.cluster.local:443/api/time/validate"
```

### Call: State Controller

```bash
curl -v -X POST \
  --cacert /etc/tls/ca.crt \
  --cert /etc/tls/tls.crt \
  --key /etc/tls/tls.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"state":"green"}' \
  "https://gruppe8-tcc-state-controller.gruppe8-tcc.svc.cluster.local:443/api/state/change"
```

### Call: Audit – Create Event

```bash
curl -v -X POST \
  --cacert /etc/tls/ca.crt \
  --cert /etc/tls/tls.crt \
  --key /etc/tls/tls.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
        "service": "tcc-priority-service",
        "eventType": "STATE_CHANGE",
        "state": "green",
        "timestamp": 1767818200000
      }' \
  "https://gruppe8-tcc-audit-service.gruppe8-tcc.svc.cluster.local:443/api/audit/events"
```

### Call: Audit – Read Logs

```bash
curl -v \
  --cacert /etc/tls/ca.crt \
  --cert /etc/tls/tls.crt \
  --key /etc/tls/tls.key \
  -H "Authorization: Bearer $TOKEN" \
  "https://gruppe8-tcc-audit-service.gruppe8-tcc.svc.cluster.local:443/api/audit/logs?from=0&to=9999999999999"
```

### Call: Location Validator

```bash
curl -v -X POST \
  --cacert /etc/tls/ca.crt \
  --cert /etc/tls/tls.crt \
  --key /etc/tls/tls.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
        "vehicleId": "vehicle-123",
        "coordinates": {
          "latitude": 52.520008,
          "longitude": 13.404954
        }
      }' \
  "https://gruppe8-location-validator.gruppe8-shared-services.svc.cluster.local:443/api/location/vehicle"
```

---

## 6️⃣ Cleanup

```bash
kubectl delete pod tls-debug -n gruppe8-testing
```
