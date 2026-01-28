# 🧪 TLS / mTLS Testing Cheat Sheet (Gruppe 8)

This document contains all commands required to:
- Create a TLS debug pod
- Obtain service tokens from Keycloak
- Call all protected services using mTLS + Bearer tokens
- Clean up resources

**Important**

- Make sure you current value of $TOKEN was pulled for a client, that was authorized for the endpoints. If you follow the guide from top to bottom this can not become an issue.
- Make sure to repull the token should you get an 401 Error. The token probably expired
- The following calls are all expected to return a HTTP Code 200, unless stated otherwise.

**Pre-requisite**

- Applied `gruppe8-testing-pod.yaml` in `kubernetes/testing-pod`, otherwise:

```
kubectl apply -f kubernetes/testing-pod/gruppe8-testing-pod.yaml
```

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

## 3️⃣ Get new token for tcc-state-controller and call authorized endpoints

```bash
TOKEN=$(curl -vk \
  --cacert /etc/tls/ca.crt \
  -d grant_type=client_credentials \
  -d client_id=tcc-state-controller \
  -d client_secret=MEb3nfjgTGkEHiZs8NugXxKTf2UqHdVC \
  https://keycloak-service.keycloak.svc.cluster.local:8443/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

echo "LEN=${#TOKEN}"            # If this is greater than 0 we have gotten an access_token from keycloak.
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
### Call Get Traffic Lights 

```bash
curl -v -X GET \
  --cacert /etc/tls/ca.crt \
  --cert /etc/tls/tls.crt \
  --key /etc/tls/tls.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{}' \
  "https://gruppe8-traffic-light-device-service.gruppe8-traffic-light-devices.svc.cluster.local:443/api/device/traffic-lights"
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

## 4️⃣ Get token for tcc-status-service and call authorized endpoints

```bash
TOKEN=$(curl -vk \
  --cacert /etc/tls/ca.crt \
  -d grant_type=client_credentials \
  -d client_id=tcc-status-service \
  -d client_secret=u7dV5aMSxMpRrGL2BezDnMf1uQFIULkQ \
  https://keycloak-service.keycloak.svc.cluster.local:8443/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

echo "LEN=${#TOKEN}"            # If this is greater than 0 we have gotten an access_token from keycloak.
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
  "https://gruppe8-traffic-light-device-service.gruppe8-traffic-light-devices.svc.cluster.local:443/api/device/traffic-state?state=green"
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

## 5️⃣ Get token for tcc-priority-service and call authorized endpoints

```bash
TOKEN=$(curl -s \
  --cacert /etc/tls/ca.crt \
  -d grant_type=client_credentials \
  -d client_id=tcc-priority-service \
  -d client_secret=ORzb7Qc6FxG82m1GJjDTwPZmcmwNtPJb \
  https://keycloak-service.keycloak.svc.cluster.local:8443/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

echo "LEN=${#TOKEN}"            # If this is greater than 0 we have gotten an access_token from keycloak.
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

## 6️⃣  Verify Securicy Policy

All previous calls were designed to work. This shows that access controll works. Additionally requests with wrong Authorization Headers or missing ones should be denied. Here one example for each situation is provided, which lead to a 403 Error. To know if a 403 Error should occur you can have a look at the Securicy Policy in [README.md](README.md).

### No authorization header

```bash
curl -v -X POST \
  --cacert /etc/tls/ca.crt \
  --cert /etc/tls/tls.crt \
  --key /etc/tls/tls.key \
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

### Calling an Endpoint without Authorization

1. Get an access_token for the tcc-state-controller:

```bash
TOKEN=$(curl -vk \
  --cacert /etc/tls/ca.crt \
  -d grant_type=client_credentials \
  -d client_id=tcc-state-controller \
  -d client_secret=MEb3nfjgTGkEHiZs8NugXxKTf2UqHdVC \
  https://keycloak-service.keycloak.svc.cluster.local:8443/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

echo "LEN=${#TOKEN}"
```

2. Try to call an endpoint, which is not authorized for the tcc-state-controller:

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

## 7️⃣ Cleanup

Exit the pod:

```
exit
```

Delete the pod:

```bash
kubectl delete pod tls-debug -n gruppe8-testing
```