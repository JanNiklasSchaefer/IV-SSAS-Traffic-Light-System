## Bonus Information. (Not relevant for deployment)

### exporting quarkus realm:

To redeploy the realm across multiple systems you need to first export it.

#### 1. Set the current namespace to keycloak

```
kubectl config set-context --current --namespace=keycloak
```

#### 2. Export the realm to a file inside the pod

```
kubectl exec -n keycloak keycloak-0 -- \
  /opt/keycloak/bin/kc.sh export --optimized \
  --http-management-port=9002 \
  --file /tmp/group8-task5-realm-import.json
```

#### 3. Confirm it exists

```
kubectl exec -n keycloak keycloak-0 -- ls -lah /tmp | grep group8-task5
```

#### 4. Copy it out without kubectl cp (no tar needed)

```
kubectl exec -n keycloak keycloak-0 -- cat /tmp/group8-task5-realm-import.json > group8-task5-realm-import.json
```

# get token

```
TOKEN=$(curl -s --cacert certs/ca.crt \
  -d grant_type=client_credentials \
  -d client_id=emergency-vehicle-client \
  -d client_secret=HgieJW7qal3HUr0R7vThPMLd6CgSptJF \
  https://keycloak.test/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

echo "LEN=${#TOKEN}"
```
## token mayor - vehicle

```
TOKEN=$(curl -s --cacert certs/ca.crt \
  -d grant_type=client_credentials \
  -d client_id=mayor-vehicle-client \
  -d client_secret=L3T9G2d7YG3Gaz2zU4jIwoPwg8xoxDpt \
  https://keycloak.test/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

echo "LEN=${#TOKEN}"
```

### token other -vehicle

```
TOKEN=$(curl -s --cacert certs/ca.crt \
  -d grant_type=client_credentials \
  -d client_id=other-vehicle-client \
  -d client_secret=897oGYqj6NSK8IWGOCddTM6iMtDIUnsY \
  https://keycloak.test/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

echo "LEN=${#TOKEN}"
```


## test status

```
curl -v -X GET \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{}' \
  "https://tcc.test/api/status/traffic?traffic-light-id=320381db-f7cd-3f31-804b-aa6b36e1c682"
```

### north-south

```
8d8d1437-907b-3a79-900a-c5f0ea1f5c73
```

### south-north

```
50fd76e3-3fe5-3961-bc5c-a99008af8904
```

### west-east

```
da4f0053-a5c1-3882-a688-52ae2da2e466
```

### east-west

```
320381db-f7cd-3f31-804b-aa6b36e1c682
```

## test priority

```
curl -v -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"emergency-vehicle","vehicleId":"7de3d833-8468-4105-8435-f178b6ff279c","trafficLightId":"da4f0053-a5c1-3882-a688-52ae2da2e466"}' \
  "https://tcc.test/api/priority/requests"
```

## request priority status

```
curl -v -X GET \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  "https://tcc.test/api/priority/requests/25832e4a-e98b-4e81-a5a1-94bf6af35c69"
```

## test traffic lights

```
curl -v -X GET \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{}' \
  "https://tcc.test/api/status/traffic-lights"
```

## redeploy a single service example call:

mvn -pl services/tcc-status-service clean package -Dquarkus.kubernetes.deploy=true

mvn -pl services/tcc-state-controller clean package -Dquarkus.kubernetes.deploy=true

mvn -pl services/tcc-priority-service clean package -Dquarkus.kubernetes.deploy=true

mvn -pl services/gruppe8-traffic-light-device-serviceclean package -Dquarkus.kubernetes.deploy=true

# redeploy everything:

mvn clean package -Dquarkus.kubernetes.deploy=true

# checking logs of a service:

### status service

kubectl logs -n gruppe8-tcc deploy/gruppe8-tcc-status-service --tail=50

### state controller

kubectl logs -n gruppe8-tcc deploy/gruppe8-tcc-state-controller --tail=50

### priority service

kubectl logs -n gruppe8-tcc deploy/gruppe8-tcc-priority-service --tail=50

### traffic light device

kubectl logs -n gruppe8-traffic-light-devices deploy/gruppe8-traffic-light-device-service --tail=50

## more general :

kubectl logs -n {namespace} {deployment name} --tail=50

# Traffic Management Center Client test calls

## get token call

```
TOKEN=$(curl -s --cacert certs/ca.crt \
  -d grant_type=client_credentials \
  -d client_id=traffic-management-center-client\
  -d client_secret=ZDpX2WcFPbSCuk2aDSIPI8DdJXa6PIQ7 \
  https://keycloak.test/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

echo "LEN=${#TOKEN}"
```

## get state call

```
curl -v -X GET \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{}' \
  "https://tcc.test/api/state/management/state"
```

## update state call

```
curl -v -X PUT \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"trafficLightId":{"latitude":{"degrees":37,"minutes":30,"seconds":30.0,"latitude":true},"longitude":{"degrees":-121,"minutes":30,"seconds":29.0,"latitude":false},"uuid":"8d8d1437-907b-3a79-900a-c5f0ea1f5c73","direction":"north-south"},"timestamp": "1", "state":"red", "pedestrianState" :"green" }' \
  "https://tcc.test/api/state/management/change-state"
```

```
curl -v -X PUT \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d 'green' \
  "https://tcc.test/api/state/management/change-state?traffic-light-id=8d8d1437-907b-3a79-900a-c5f0ea1f5c73"
```


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



Delete the pod:

```bash
kubectl delete pod tls-debug -n gruppe8-testing
```

## test calls:

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
  "https://gruppe8-traffic-light-device-service.gruppe8-traffic-light-devices.svc.cluster.local:443/api/device/change-state"
```