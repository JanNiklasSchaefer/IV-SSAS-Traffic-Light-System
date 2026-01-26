TOKEN=$(curl -s --cacert certs/ca.crt \
  -d grant_type=client_credentials \
  -d client_id=emergency-vehicle-client \
  -d client_secret=HgieJW7qal3HUr0R7vThPMLd6CgSptJF \
  https://keycloak.test/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

echo "LEN=${#TOKEN}"    

## test status 

curl -v -X GET \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{}' \
  "https://tcc.test/api/status/traffic?vehicle=true"


## test priority

curl -v -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"emergency"}' \
  "https://tcc.test/api/priority/requests"

  ## redeploy a single service example call:


mvn -pl services/tcc-status-service clean package -Dquarkus.kubernetes.deploy=true


# redeploy everything: 

mvn clean package -Dquarkus.kubernetes.deploy=true


# checking logs of a service:


kubectl logs -n gruppe8-tcc deploy/gruppe8-tcc-status-service --tail=50

##  more general : 

kubectl logs -n {namespace} {deployment name} --tail=50
