```
TOKEN=$(curl -s --cacert certs/ca.crt \
  -d grant_type=client_credentials \
  -d client_id=emergency-vehicle-client \
  -d client_secret=HgieJW7qal3HUr0R7vThPMLd6CgSptJF \
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
  -d '{"vehicleType":"emergency"}' \
  "https://tcc.test/api/priority/requests"
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


# redeploy everything: 

mvn clean package -Dquarkus.kubernetes.deploy=true


# checking logs of a service:


kubectl logs -n gruppe8-tcc deploy/gruppe8-tcc-status-service --tail=50

##  more general : 

kubectl logs -n {namespace} {deployment name} --tail=50
