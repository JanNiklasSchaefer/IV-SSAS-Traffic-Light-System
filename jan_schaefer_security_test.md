# Security Protocol Jan Niklas Schäfer

## Misuse Case 

An unauthorized client sends priority requests or tries to escalate privileges (e.g., mayor → emergency) to force green lights.

## Security  Requirements

The Security Requirements, to prevent this misuse case are:

1. **Authentication** : Only authenticated clients with a valid access token can call the priority endpoint.
2. **Authorization** : Only clients with an authorized role can request priority. In particular mayor-vehicle and emergency-vehicle.
3. **Integrity** : No client can escalate their access privileges by tampering with the request body or the access token.  Any such attempt shall be rejected and must not change the traffic light state.

## Manual Security Test Cases 

To demonstrate the security requirements above, the following manual tests are executed. The objective is to show that the system rejects requests from unauthorized clients and cannot be tricked into accepting an escalated priority request (e.g., treating a MAYOR / OTHER / PEDESTRIAN as an EMERGENCY) by tampering with the request body or the access token.

### Preconditions

- System is deployed according to README.md
- Host endpoints are set or port forwarding is activated
- TrafficLights North-South and South-North are set to "red"
- TrafficLights East-West and West-East are set to "green"
- traffic-management-center client is running in an extra window of the terminal
- No other Clients are calling the server during tests, to ensure integrity of testing results 

### Required Tools 

- curl 
- jq
- python3

### Test Steps + expected results

**Note (secrets in test protocol):**  
For ease of reproduction, the client secrets are shown in the example commands. In the real deployment,client secrets must not be published in documentation. In our actual client implementation, secrets are loaded from a local `.env` file.


#### Step 1 — Missing authentication (TLS trust and access token)

First, call the endpoint without trusting the server CA and without a client certificate or access token. This should fail during TLS verification (no connection established).

```bash
curl -v -X POST \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"emergency-vehicle","vehicleId":"7de3d833-8468-4105-8435-f178b6ff279c","trafficLightId":"da4f0053-a5c1-3882-a688-52ae2da2e466"}' \
  "https://tcc.test/api/priority/requests"
```

curl should fail TLS certificate verification. The expected output is:

```bash
* Host tcc.test:443 was resolved.
* IPv6: (none)
* IPv4: 127.0.0.1
*   Trying 127.0.0.1:443...
* Connected to tcc.test (127.0.0.1) port 443
* ALPN: curl offers h2,http/1.1
* TLSv1.3 (OUT), TLS handshake, Client hello (1):
*  CAfile: /etc/ssl/certs/ca-certificates.crt
*  CApath: /etc/ssl/certs
* TLSv1.3 (IN), TLS handshake, Server hello (2):
* TLSv1.3 (IN), TLS handshake, Encrypted Extensions (8):
* TLSv1.3 (IN), TLS handshake, Certificate (11):
* TLSv1.3 (OUT), TLS alert, unknown CA (560):
* SSL certificate problem: unable to get local issuer certificate
* Closing connection
curl: (60) SSL certificate problem: unable to get local issuer certificate
More details here: https://curl.se/docs/sslcerts.html

curl failed to verify the legitimacy of the server and therefore could not
establish a secure connection to it. To learn more about this situation and
how to fix it, please visit the web page mentioned above.
```

Now call the endpoint with the required certificates (TLS succeeds) but still without an access token. This should be rejected at the application layer.

```bash
curl -v -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"emergency-vehicle","vehicleId":"7de3d833-8468-4105-8435-f178b6ff279c","trafficLightId":"da4f0053-a5c1-3882-a688-52ae2da2e466"}' \
  "https://tcc.test/api/priority/requests"
```

The server should now verify the TLS certificates but refuse the access with HTTP Error Code ```403```. The expected output is:

```bash
* Host tcc.test:443 was resolved.
* IPv6: (none)
* IPv4: 127.0.0.1
*   Trying 127.0.0.1:443...
* Connected to tcc.test (127.0.0.1) port 443
* ALPN: curl offers h2,http/1.1
* TLSv1.3 (OUT), TLS handshake, Client hello (1):
*  CAfile: certs/ca.crt
*  CApath: /etc/ssl/certs
* TLSv1.3 (IN), TLS handshake, Server hello (2):
* TLSv1.3 (IN), TLS handshake, Encrypted Extensions (8):
* TLSv1.3 (IN), TLS handshake, Certificate (11):
* TLSv1.3 (IN), TLS handshake, CERT verify (15):
* TLSv1.3 (IN), TLS handshake, Finished (20):
* TLSv1.3 (OUT), TLS change cipher, Change cipher spec (1):
* TLSv1.3 (OUT), TLS handshake, Finished (20):
* SSL connection using TLSv1.3 / TLS_AES_256_GCM_SHA384 / X25519 / id-ecPublicKey
* ALPN: server accepted h2
* Server certificate:
*  subject: O=Gruppe 8; OU=Task 4; CN=tcc-ingress
*  start date: Jan  5 20:39:39 2026 GMT
*  expire date: Apr  5 20:39:39 2026 GMT
*  subjectAltName: host "tcc.test" matched cert's "tcc.test"
*  issuer: O=Gruppe 8; OU=Task 1; CN=Krzysztof Lagowski CA
*  SSL certificate verify ok.
*   Certificate level 0: Public key type EC/prime256v1 (256/128 Bits/secBits), signed using ecdsa-with-SHA256
*   Certificate level 1: Public key type EC/prime256v1 (256/128 Bits/secBits), signed using ecdsa-with-SHA256
* using HTTP/2
* [HTTP/2] [1] OPENED stream for https://tcc.test/api/priority/requests
* [HTTP/2] [1] [:method: POST]
* [HTTP/2] [1] [:scheme: https]
* [HTTP/2] [1] [:authority: tcc.test]
* [HTTP/2] [1] [:path: /api/priority/requests]
* [HTTP/2] [1] [user-agent: curl/8.5.0]
* [HTTP/2] [1] [accept: */*]
* [HTTP/2] [1] [content-type: application/json]
* [HTTP/2] [1] [content-length: 142]
> POST /api/priority/requests HTTP/2
> Host: tcc.test
> User-Agent: curl/8.5.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 142
> 
* TLSv1.3 (IN), TLS handshake, Newsession Ticket (4):
* TLSv1.3 (IN), TLS handshake, Newsession Ticket (4):
* old SSL session ID is stale, removing
< HTTP/2 403 
< date: Sun, 01 Feb 2026 19:10:47 GMT
< content-length: 0
< strict-transport-security: max-age=31536000; includeSubDomains
< 
* Connection #0 to host tcc.test left intact
```
**Note** From this point on, the following `curl` calls omit `-v` for readability. TLS is already verified in Step 1; subsequent steps focus on HTTP status codes, response bodies, and traffic light state changes.

Additionally, verify that no traffic light state changed.

**Note**  To verify that no traffic light changed, simply open the traffic-management-center client in another tab. Use option ```1``` to get the status of the whole intersection.

Expected Status: 

```bash
--- GET /api/state/manual/state
Traffic Light Direction: north-south
UUID: 8d8d1437-907b-3a79-900a-c5f0ea1f5c73
Vehicle State: red
Pedestrian State: green
-----
Traffic Light Direction: south-north
UUID: 50fd76e3-3fe5-3961-bc5c-a99008af8904
Vehicle State: red
Pedestrian State: green
-----
Traffic Light Direction: west-east
UUID: da4f0053-a5c1-3882-a688-52ae2da2e466
Vehicle State: green
Pedestrian State: red
-----
Traffic Light Direction: east-west
UUID: 320381db-f7cd-3f31-804b-aa6b36e1c682
Vehicle State: green
Pedestrian State: red
```


#### Step 2 - Calling Priority as Mayor-Vehicle

Call Priority Request with Mayor Access Token, to change "North-South".


```bash
#Get a token for the mayor-vehicle client
TOKEN=$(curl -s --cacert certs/ca.crt \
  -d grant_type=client_credentials \
  -d client_id=mayor-vehicle-client \
  -d client_secret=L3T9G2d7YG3Gaz2zU4jIwoPwg8xoxDpt \
  https://keycloak.test/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

echo "LEN=${#TOKEN}" # If this returns more than 0 you have a valid token

#Now call the priority endpoint to change the north-south traffic light. The trafficLightId is that of the north-south one. 

curl -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"mayor-vehicle","vehicleId":"7de3d833-8468-4105-8435-f178b6ff279c","trafficLightId":"8d8d1437-907b-3a79-900a-c5f0ea1f5c73"}' \
  "https://tcc.test/api/priority/requests"              
```

The server will accept the request and change the status of the traffic lights. While this is happening the connection is held up as switching the traffic lights takes about 15 seconds, due to the yellow phase. 

The expected output is (requestId will differ as it is randomly generated with each request):

```bash
{"status":"accepted","requestId":"04e8274e-89ae-42fb-baf6-88b3933fd8f9"}
```

Verify that the traffic status changed. Expected Status:

```bash
--- GET /api/state/manual/state
Traffic Light Direction: north-south
UUID: 8d8d1437-907b-3a79-900a-c5f0ea1f5c73
Vehicle State: green
Pedestrian State: red
-----
Traffic Light Direction: south-north
UUID: 50fd76e3-3fe5-3961-bc5c-a99008af8904
Vehicle State: green
Pedestrian State: red
-----
Traffic Light Direction: west-east
UUID: da4f0053-a5c1-3882-a688-52ae2da2e466
Vehicle State: red
Pedestrian State: green
-----
Traffic Light Direction: east-west
UUID: 320381db-f7cd-3f31-804b-aa6b36e1c682
Vehicle State: red
Pedestrian State: green
-----
```

#### Step 3 - Calling Priority as Emergency-Vehicle

Call Priority Request with Emergency Vehicle Access Token, to change "East-West".

```bash
#Get a token for the emergency-vehicle client
TOKEN=$(curl -s --cacert certs/ca.crt \
  -d grant_type=client_credentials \
  -d client_id=emergency-vehicle-client \
  -d client_secret=HgieJW7qal3HUr0R7vThPMLd6CgSptJF \
  https://keycloak.test/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

echo "LEN=${#TOKEN}" # If this returns more than 0 you have a valid token

#Now call the priority endpoint to change the east-west traffic light. The trafficLightId is that of the east-west one. 

curl -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"emergency-vehicle","vehicleId":"56a9209c-1678-4d2f-877f-5cb6cd95671f","trafficLightId":"320381db-f7cd-3f31-804b-aa6b36e1c682"}' \
  "https://tcc.test/api/priority/requests"         
```

The server will accept the request and change the status of the traffic lights. While this is happening the connection is held up as switching the traffic lights takes about 15 seconds, due to the yellow phase. 

The expected output is (requestId will differ as it is randomly generated with each request):

```bash
{"status":"accepted","requestId":"480d24b3-9174-4554-8c8e-ac358078ecc1"}
```

Additionally verify that the Traffic Status changed. Expected Status:

```bash
--- GET /api/state/manual/state
Traffic Light Direction: north-south
UUID: 8d8d1437-907b-3a79-900a-c5f0ea1f5c73
Vehicle State: red
Pedestrian State: green
-----
Traffic Light Direction: south-north
UUID: 50fd76e3-3fe5-3961-bc5c-a99008af8904
Vehicle State: red
Pedestrian State: green
-----
Traffic Light Direction: west-east
UUID: da4f0053-a5c1-3882-a688-52ae2da2e466
Vehicle State: green
Pedestrian State: red
-----
Traffic Light Direction: east-west
UUID: 320381db-f7cd-3f31-804b-aa6b36e1c682
Vehicle State: green
Pedestrian State: red
-----
```

#### Step 4 - Calling Priority as Other-Vehicle

Call Priority Request with other-vehicle Access Token, to change "North-South".


```bash
#Get a token for the other-vehicle client

TOKEN=$(curl -s --cacert certs/ca.crt \
  -d grant_type=client_credentials \
  -d client_id=other-vehicle-client \
  -d client_secret=897oGYqj6NSK8IWGOCddTM6iMtDIUnsY \
  https://keycloak.test/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

echo "LEN=${#TOKEN}" # If this returns more than 0 you have a valid token

#Now we call the priority endpoint to change the north-south traffic light.

curl -v -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"other-vehicle","vehicleId":"f30a2ae7-c567-4b91-a25e-c09c836c80ad","trafficLightId":"8d8d1437-907b-3a79-900a-c5f0ea1f5c73"}' \
  "https://tcc.test/api/priority/requests"    
```
The service should deny access for the other-vehicle client with a HTTP Error Code ```403```. 

Expected Output:

```bash
HTTP/2 403 
date: Sun, 01 Feb 2026 19:53:10 GMT
content-length: 0
strict-transport-security: max-age=31536000; includeSubDomains
```

Validate that the status of the traffic lights did not change:

```bash
--- GET /api/state/manual/state
Traffic Light Direction: north-south
UUID: 8d8d1437-907b-3a79-900a-c5f0ea1f5c73
Vehicle State: red
Pedestrian State: green
-----
Traffic Light Direction: south-north
UUID: 50fd76e3-3fe5-3961-bc5c-a99008af8904
Vehicle State: red
Pedestrian State: green
-----
Traffic Light Direction: west-east
UUID: da4f0053-a5c1-3882-a688-52ae2da2e466
Vehicle State: green
Pedestrian State: red
-----
Traffic Light Direction: east-west
UUID: 320381db-f7cd-3f31-804b-aa6b36e1c682
Vehicle State: green
Pedestrian State: red
-----
```

#### Step 5 - Calling Priority as Pedestrian

Call Priority Request with pedestrian Access Token, to change "North-South".


```bash
#Get a token for the pedestrian client

TOKEN=$(curl -s --cacert certs/ca.crt \
  -d grant_type=client_credentials \
  -d client_id=pedestrian-client \
  -d client_secret=yJCbekK05ZP5tfaSvmBJzqbvetVa7ryT \
  https://keycloak.test/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

echo "LEN=${#TOKEN}" # If this returns more than 0 you have a valid token

#Now we call the priority endpoint to change the north-south traffic light.

curl -i -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"pedestrian","vehicleId":"f30a2ae7-c567-4b91-a25e-c09c836c80ad","trafficLightId":"8d8d1437-907b-3a79-900a-c5f0ea1f5c73"}' \
  "https://tcc.test/api/priority/requests"    
```
The service should deny access for the pedestrian client with a HTTP Error Code ```403```. 

Expected Output:

```bash
HTTP/2 403 
date: Sun, 01 Feb 2026 19:56:44 GMT
content-length: 0
strict-transport-security: max-age=31536000; includeSubDomains
```

Validate that the status of the traffic lights did not change:

```bash
--- GET /api/state/manual/state
Traffic Light Direction: north-south
UUID: 8d8d1437-907b-3a79-900a-c5f0ea1f5c73
Vehicle State: red
Pedestrian State: green
-----
Traffic Light Direction: south-north
UUID: 50fd76e3-3fe5-3961-bc5c-a99008af8904
Vehicle State: red
Pedestrian State: green
-----
Traffic Light Direction: west-east
UUID: da4f0053-a5c1-3882-a688-52ae2da2e466
Vehicle State: green
Pedestrian State: red
-----
Traffic Light Direction: east-west
UUID: 320381db-f7cd-3f31-804b-aa6b36e1c682
Vehicle State: green
Pedestrian State: red
-----
```

#### Step 6 - Spoofing Vehicle Type in Request Body

Here we call the priority request endpoint with the access token of the mayor-vehicle client, but spoof the vehicle type as emergency-vehicle, trying to escalate our privilege. This step tests that no escalation of privilege is possible by tampering with the ```vehicleType```.


```bash
#Get a token for the mayor-vehicle client
TOKEN=$(curl -s --cacert certs/ca.crt \
  -d grant_type=client_credentials \
  -d client_id=mayor-vehicle-client \
  -d client_secret=L3T9G2d7YG3Gaz2zU4jIwoPwg8xoxDpt \
  https://keycloak.test/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

echo "LEN=${#TOKEN}" # If this returns more than 0 you have a valid token

#Now call the priority endpoint to change the north-south traffic light. Set vehicleType to `emergency-vehicle` but use the TOKEN from mayor-vehicle client.

curl -i -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"emergency-vehicle","vehicleId":"7de3d833-8468-4105-8435-f178b6ff279c","trafficLightId":"8d8d1437-907b-3a79-900a-c5f0ea1f5c73"}' \
  "https://tcc.test/api/priority/requests"              
```

The server should recognize that the access token belongs to a mayor-vehicle client and that the vehicleType in the request body was spoofed. 

Expected output:

```bash
HTTP/2 200 
date: Sun, 01 Feb 2026 20:11:22 GMT
content-type: application/json;charset=UTF-8
content-length: 36
strict-transport-security: max-age=31536000; includeSubDomains

{"status":"denied","requestId":null}
```

Although we get a HTTP Status Code 200, the JSON body shows clear denial of the request. 

Additionally validate that the traffic light status did not change. Expected Status:

```bash
--- GET /api/state/manual/state
Traffic Light Direction: north-south
UUID: 8d8d1437-907b-3a79-900a-c5f0ea1f5c73
Vehicle State: red
Pedestrian State: green
-----
Traffic Light Direction: south-north
UUID: 50fd76e3-3fe5-3961-bc5c-a99008af8904
Vehicle State: red
Pedestrian State: green
-----
Traffic Light Direction: west-east
UUID: da4f0053-a5c1-3882-a688-52ae2da2e466
Vehicle State: green
Pedestrian State: red
-----
Traffic Light Direction: east-west
UUID: 320381db-f7cd-3f31-804b-aa6b36e1c682
Vehicle State: green
Pedestrian State: red
-----
```

#### Step 7 - Tampering with the Mayor Access Token

Modify the JWT Token to contain the roles "emergency-vehicle" and resend the request with the tampered token. 

```bash
# Get a valid JWT token
TOKEN=$(curl -s --cacert certs/ca.crt \
  -d grant_type=client_credentials \
  -d client_id=mayor-vehicle-client \
  -d client_secret=L3T9G2d7YG3Gaz2zU4jIwoPwg8xoxDpt \
  https://keycloak.test/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

echo "LEN=${#TOKEN}"

# Split JWT into parts (needed for tampering + rebuilding)
IFS='.' read -r JWT_HEADER_B64 JWT_PAYLOAD_B64 JWT_SIG_B64 <<< "$TOKEN"

# Tamper payload: add emergency-vehicle role
TAMPERED_PAYLOAD_JSON=$(
  printf '%s' "$JWT_PAYLOAD_B64" \
  | python3 -c 'import sys,base64; s=sys.stdin.read().strip(); s+="="*((4-len(s)%4)%4); print(base64.urlsafe_b64decode(s).decode())' \
  | jq '
      .resource_access["tcc-priority-service"].roles |= ((. // []) + ["emergency-vehicle"] | unique)
    | .resource_access["mayor-vehicle-client"].roles  |= ((. // []) + ["emergency-vehicle"] | unique)
    '
)

# Base64url encode tampered payload (no padding)
TAMPERED_PAYLOAD_B64=$(
  printf '%s' "$TAMPERED_PAYLOAD_JSON" \
  | jq -c . \
  | python3 -c 'import sys,base64; data=sys.stdin.read().strip().encode(); print(base64.urlsafe_b64encode(data).decode().rstrip("="))'
)

# Rebuild forged token (same header + same signature)
FORGED_TOKEN="${JWT_HEADER_B64}.${TAMPERED_PAYLOAD_B64}.${JWT_SIG_B64}"

# Call priority endpoint with forged token (use -i, not -v, since TLS was proven earlier)
curl -v -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $FORGED_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"mayor-vehicle","vehicleId":"7de3d833-8468-4105-8435-f178b6ff279c","trafficLightId":"8d8d1437-907b-3a79-900a-c5f0ea1f5c73"}' \
  "https://tcc.test/api/priority/requests" 
```

The tampered token should be denied and a HTTP Code ```401``` should be returned. 

Expected Output: 

```bash
* Host tcc.test:443 was resolved.
* IPv6: (none)
* IPv4: 127.0.0.1
*   Trying 127.0.0.1:443...
* Connected to tcc.test (127.0.0.1) port 443
* ALPN: curl offers h2,http/1.1
* TLSv1.3 (OUT), TLS handshake, Client hello (1):
*  CAfile: certs/ca.crt
*  CApath: /etc/ssl/certs
* TLSv1.3 (IN), TLS handshake, Server hello (2):
* TLSv1.3 (IN), TLS handshake, Encrypted Extensions (8):
* TLSv1.3 (IN), TLS handshake, Certificate (11):
* TLSv1.3 (IN), TLS handshake, CERT verify (15):
* TLSv1.3 (IN), TLS handshake, Finished (20):
* TLSv1.3 (OUT), TLS change cipher, Change cipher spec (1):
* TLSv1.3 (OUT), TLS handshake, Finished (20):
* SSL connection using TLSv1.3 / TLS_AES_256_GCM_SHA384 / X25519 / id-ecPublicKey
* ALPN: server accepted h2
* Server certificate:
*  subject: O=Gruppe 8; OU=Task 4; CN=tcc-ingress
*  start date: Jan  5 20:39:39 2026 GMT
*  expire date: Apr  5 20:39:39 2026 GMT
*  subjectAltName: host "tcc.test" matched cert's "tcc.test"
*  issuer: O=Gruppe 8; OU=Task 1; CN=Krzysztof Lagowski CA
*  SSL certificate verify ok.
*   Certificate level 0: Public key type EC/prime256v1 (256/128 Bits/secBits), signed using ecdsa-with-SHA256
*   Certificate level 1: Public key type EC/prime256v1 (256/128 Bits/secBits), signed using ecdsa-with-SHA256
* using HTTP/2
* [HTTP/2] [1] OPENED stream for https://tcc.test/api/priority/requests
* [HTTP/2] [1] [:method: POST]
* [HTTP/2] [1] [:scheme: https]
* [HTTP/2] [1] [:authority: tcc.test]
* [HTTP/2] [1] [:path: /api/priority/requests]
* [HTTP/2] [1] [user-agent: curl/8.5.0]
* [HTTP/2] [1] [accept: */*]
* [HTTP/2] [1] [authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJHcThtLVQ5SElLdTVkUm01ZThnOGVwZXFrWEUyVEtuWDBtd3VUZW1RWnRvIn0.eyJleHAiOjE3Njk5ODAyMjAsImlhdCI6MTc2OTk3OTkyMCwianRpIjoidHJydGNjOmUyOTc3NzNmLTY2NjEtYzU3My1lMDhiLWM3ODRiYzkwZmQ0OSIsImlzcyI6Imh0dHBzOi8va2V5Y2xvYWsudGVzdC9rZXljbG9hay9yZWFsbXMvZ3JvdXA4LXRhc2s1IiwiYXVkIjpbInRjYy1wcmlvcml0eS1zZXJ2aWNlIiwidGNjLXN0YXR1cy1zZXJ2aWNlIiwiYWNjb3VudCJdLCJzdWIiOiJiNzMwOTVhYS1kOTE5LTQ3M2ItYWIzOS1hNzg4YmVhNThlNGQiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJtYXlvci12ZWhpY2xlLWNsaWVudCIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiLyoiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtZ3JvdXA4LXRhc2s1Iiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7Im1heW9yLXZlaGljbGUtY2xpZW50Ijp7InJvbGVzIjpbImVtZXJnZW5jeS12ZWhpY2xlIiwibWF5b3ItdmVoaWNsZSJdfSwidGNjLXByaW9yaXR5LXNlcnZpY2UiOnsicm9sZXMiOlsiZW1lcmdlbmN5LXZlaGljbGUiLCJtYXlvci12ZWhpY2xlIl19LCJ0Y2Mtc3RhdHVzLXNlcnZpY2UiOnsicm9sZXMiOlsibWF5b3ItdmVoaWNsZSJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwiY2xpZW50SG9zdCI6IjEyNy4wLjAuMSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwicHJlZmVycmVkX3VzZXJuYW1lIjoic2VydmljZS1hY2NvdW50LW1heW9yLXZlaGljbGUtY2xpZW50IiwiY2xpZW50QWRkcmVzcyI6IjEyNy4wLjAuMSIsImNsaWVudF9pZCI6Im1heW9yLXZlaGljbGUtY2xpZW50In0.1GskLYAPCTV51jZLygvH_zpo5pmpEiG25XFpN-9MnjbAnt4kPfa2yeQ2Jj69lGHAVEQQJObYjLKdY5nS1zzKiD66qKpAqr5AD8lVazMRpn-bbMOVGtnpsqzRcOF_W3dFCGFjGDs6OBBXXsB6P9uoq8HPRIOUvKGtMyaAUG9ktKrJwQ8YBUHQvNau0hEvc8UiK5_qhvPuZrlo0DR94PVjwSuzNQc2g8zzOkK-54nA2NHeOar6iUaOQGWG-52LiUc7Gyc24j2Da9vVJAM_jJyKQ97MpzIRXwivCYp_fl4symLLtkJgw6TkA29ay72yOdcHZTFoCUq_7QGa6JC1Si6anQ]
* [HTTP/2] [1] [content-type: application/json]
* [HTTP/2] [1] [content-length: 138]
> POST /api/priority/requests HTTP/2
> Host: tcc.test
> User-Agent: curl/8.5.0
> Accept: */*
> Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJHcThtLVQ5SElLdTVkUm01ZThnOGVwZXFrWEUyVEtuWDBtd3VUZW1RWnRvIn0.eyJleHAiOjE3Njk5ODAyMjAsImlhdCI6MTc2OTk3OTkyMCwianRpIjoidHJydGNjOmUyOTc3NzNmLTY2NjEtYzU3My1lMDhiLWM3ODRiYzkwZmQ0OSIsImlzcyI6Imh0dHBzOi8va2V5Y2xvYWsudGVzdC9rZXljbG9hay9yZWFsbXMvZ3JvdXA4LXRhc2s1IiwiYXVkIjpbInRjYy1wcmlvcml0eS1zZXJ2aWNlIiwidGNjLXN0YXR1cy1zZXJ2aWNlIiwiYWNjb3VudCJdLCJzdWIiOiJiNzMwOTVhYS1kOTE5LTQ3M2ItYWIzOS1hNzg4YmVhNThlNGQiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJtYXlvci12ZWhpY2xlLWNsaWVudCIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiLyoiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtZ3JvdXA4LXRhc2s1Iiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7Im1heW9yLXZlaGljbGUtY2xpZW50Ijp7InJvbGVzIjpbImVtZXJnZW5jeS12ZWhpY2xlIiwibWF5b3ItdmVoaWNsZSJdfSwidGNjLXByaW9yaXR5LXNlcnZpY2UiOnsicm9sZXMiOlsiZW1lcmdlbmN5LXZlaGljbGUiLCJtYXlvci12ZWhpY2xlIl19LCJ0Y2Mtc3RhdHVzLXNlcnZpY2UiOnsicm9sZXMiOlsibWF5b3ItdmVoaWNsZSJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwiY2xpZW50SG9zdCI6IjEyNy4wLjAuMSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwicHJlZmVycmVkX3VzZXJuYW1lIjoic2VydmljZS1hY2NvdW50LW1heW9yLXZlaGljbGUtY2xpZW50IiwiY2xpZW50QWRkcmVzcyI6IjEyNy4wLjAuMSIsImNsaWVudF9pZCI6Im1heW9yLXZlaGljbGUtY2xpZW50In0.1GskLYAPCTV51jZLygvH_zpo5pmpEiG25XFpN-9MnjbAnt4kPfa2yeQ2Jj69lGHAVEQQJObYjLKdY5nS1zzKiD66qKpAqr5AD8lVazMRpn-bbMOVGtnpsqzRcOF_W3dFCGFjGDs6OBBXXsB6P9uoq8HPRIOUvKGtMyaAUG9ktKrJwQ8YBUHQvNau0hEvc8UiK5_qhvPuZrlo0DR94PVjwSuzNQc2g8zzOkK-54nA2NHeOar6iUaOQGWG-52LiUc7Gyc24j2Da9vVJAM_jJyKQ97MpzIRXwivCYp_fl4symLLtkJgw6TkA29ay72yOdcHZTFoCUq_7QGa6JC1Si6anQ
> Content-Type: application/json
> Content-Length: 138
> 
* TLSv1.3 (IN), TLS handshake, Newsession Ticket (4):
* TLSv1.3 (IN), TLS handshake, Newsession Ticket (4):
* old SSL session ID is stale, removing
< HTTP/2 401 
< date: Sun, 01 Feb 2026 21:05:20 GMT
< content-length: 0
< www-authenticate: Bearer
< strict-transport-security: max-age=31536000; includeSubDomains
< 
* Connection #0 to host tcc.test left intact
```

You can also verify that the token has been tampered by decoding the token and comparing it to the original one:

```bash
# Decode tampered token
IFS='.' read -r F_HEADER_B64 F_PAYLOAD_B64 F_SIG_B64 <<< "$FORGED_TOKEN"

echo "==================== FORGED TOKEN (TAMPERED) ===================="

echo "$F_HEADER_B64" | python3 -c 'import sys,base64,json; s=sys.stdin.read().strip(); s+="="*((4-len(s)%4)%4); print(json.dumps(json.loads(base64.urlsafe_b64decode(s)), indent=2))'
echo "$F_PAYLOAD_B64" | python3 -c 'import sys,base64,json; s=sys.stdin.read().strip(); s+="="*((4-len(s)%4)%4); print(json.dumps(json.loads(base64.urlsafe_b64decode(s)), indent=2))'

echo
echo "==================== ORIGINAL TOKEN ============================="
echo

# Decode original token
IFS='.' read -r JWT_HEADER_B64 JWT_PAYLOAD_B64 JWT_SIG_B64 <<< "$TOKEN"

echo "$JWT_HEADER_B64"  | python3 -c 'import sys,base64,json; s=sys.stdin.read().strip(); s+="="*((4-len(s)%4)%4); print(json.dumps(json.loads(base64.urlsafe_b64decode(s)), indent=2))'
echo "$JWT_PAYLOAD_B64" | python3 -c 'import sys,base64,json; s=sys.stdin.read().strip(); s+="="*((4-len(s)%4)%4); print(json.dumps(json.loads(base64.urlsafe_b64decode(s)), indent=2))'

```

Expected Output:

```bash
==================== FORGED TOKEN (TAMPERED) ====================
{
  "alg": "RS256",
  "typ": "JWT",
  "kid": "Gq8m-T9HIKu5dRm5e8g8epeqkXE2TKnX0mwuTemQZto"
}
{
  "exp": 1769980220,
  "iat": 1769979920,
  "jti": "trrtcc:e297773f-6661-c573-e08b-c784bc90fd49",
  "iss": "https://keycloak.test/keycloak/realms/group8-task5",
  "aud": [
    "tcc-priority-service",
    "tcc-status-service",
    "account"
  ],
  "sub": "b73095aa-d919-473b-ab39-a788bea58e4d",
  "typ": "Bearer",
  "azp": "mayor-vehicle-client",
  "acr": "1",
  "allowed-origins": [
    "/*"
  ],
  "realm_access": {
    "roles": [
      "default-roles-group8-task5",
      "offline_access",
      "uma_authorization"
    ]
  },
  "resource_access": {
    "mayor-vehicle-client": {
      "roles": [
        "emergency-vehicle",
        "mayor-vehicle"
      ]
    },
    "tcc-priority-service": {
      "roles": [
        "emergency-vehicle",
        "mayor-vehicle"
      ]
    },
    "tcc-status-service": {
      "roles": [
        "mayor-vehicle"
      ]
    },
    "account": {
      "roles": [
        "manage-account",
        "manage-account-links",
        "view-profile"
      ]
    }
  },
  "scope": "profile email",
  "clientHost": "127.0.0.1",
  "email_verified": false,
  "preferred_username": "service-account-mayor-vehicle-client",
  "clientAddress": "127.0.0.1",
  "client_id": "mayor-vehicle-client"
}

==================== ORIGINAL TOKEN =============================

{
  "alg": "RS256",
  "typ": "JWT",
  "kid": "Gq8m-T9HIKu5dRm5e8g8epeqkXE2TKnX0mwuTemQZto"
}
{
  "exp": 1769980220,
  "iat": 1769979920,
  "jti": "trrtcc:e297773f-6661-c573-e08b-c784bc90fd49",
  "iss": "https://keycloak.test/keycloak/realms/group8-task5",
  "aud": [
    "tcc-priority-service",
    "tcc-status-service",
    "account"
  ],
  "sub": "b73095aa-d919-473b-ab39-a788bea58e4d",
  "typ": "Bearer",
  "azp": "mayor-vehicle-client",
  "acr": "1",
  "allowed-origins": [
    "/*"
  ],
  "realm_access": {
    "roles": [
      "default-roles-group8-task5",
      "offline_access",
      "uma_authorization"
    ]
  },
  "resource_access": {
    "mayor-vehicle-client": {
      "roles": [
        "mayor-vehicle"
      ]
    },
    "tcc-priority-service": {
      "roles": [
        "mayor-vehicle"
      ]
    },
    "tcc-status-service": {
      "roles": [
        "mayor-vehicle"
      ]
    },
    "account": {
      "roles": [
        "manage-account",
        "manage-account-links",
        "view-profile"
      ]
    }
  },
  "scope": "profile email",
  "clientHost": "127.0.0.1",
  "email_verified": false,
  "preferred_username": "service-account-mayor-vehicle-client",
  "clientAddress": "127.0.0.1",
  "client_id": "mayor-vehicle-client"
}
```

Additionally validate that the traffic light status did not change. Expected Status:

```bash
--- GET /api/state/manual/state
Traffic Light Direction: north-south
UUID: 8d8d1437-907b-3a79-900a-c5f0ea1f5c73
Vehicle State: red
Pedestrian State: green
-----
Traffic Light Direction: south-north
UUID: 50fd76e3-3fe5-3961-bc5c-a99008af8904
Vehicle State: red
Pedestrian State: green
-----
Traffic Light Direction: west-east
UUID: da4f0053-a5c1-3882-a688-52ae2da2e466
Vehicle State: green
Pedestrian State: red
-----
Traffic Light Direction: east-west
UUID: 320381db-f7cd-3f31-804b-aa6b36e1c682
Vehicle State: green
Pedestrian State: red
-----
```

#### Step 8 - Token Expiry 

Should a token ever leak for whatever reason, the access tokens are short lived. To allow no prolonged exposure of endpoints and mimizing of any damage, even when a critical security failure happens. Testing this is rather cumbersome and I acknowledge that, but it must be documented for completness sake. 

```bash
#First make a sucesfull call towards the endpoint.

TOKEN=$(curl -s --cacert certs/ca.crt \
  -d grant_type=client_credentials \
  -d client_id=mayor-vehicle-client \
  -d client_secret=L3T9G2d7YG3Gaz2zU4jIwoPwg8xoxDpt \
  https://keycloak.test/keycloak/realms/group8-task5/protocol/openid-connect/token \
  | sed -n 's/.*"access_token":"\([^"]*\)".*/\1/p')

echo "LEN=${#TOKEN}" # If this returns more than 0 you have a valid token

#Now call the priority endpoint to change the north-south traffic light. The trafficLightId is that of the north-south one. 

curl -i -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"mayor-vehicle","vehicleId":"7de3d833-8468-4105-8435-f178b6ff279c","trafficLightId":"8d8d1437-907b-3a79-900a-c5f0ea1f5c73"}' \
  "https://tcc.test/api/priority/requests" 

# Call the Endpoint a second time to verify that multiple calls with 1 acces token can be made

curl -i -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"mayor-vehicle","vehicleId":"7de3d833-8468-4105-8435-f178b6ff279c","trafficLightId":"8d8d1437-907b-3a79-900a-c5f0ea1f5c73"}' \
  "https://tcc.test/api/priority/requests" 
```

These calls should have worked as they were within the lifespan of the token. The expected output for both is:

```bash
LEN=1613
HTTP/2 200 
date: Sun, 01 Feb 2026 20:53:34 GMT
content-type: application/json;charset=UTF-8
content-length: 72
strict-transport-security: max-age=31536000; includeSubDomains

{"status":"accepted","requestId":"9eba8d69-0ea6-40d6-996f-473f65fc1b0f"}HTTP/2 200 
date: Sun, 01 Feb 2026 20:53:34 GMT
content-type: application/json;charset=UTF-8
content-length: 72
strict-transport-security: max-age=31536000; includeSubDomains

{"status":"accepted","requestId":"48228ce7-e5f7-4adb-9eef-d45451308211"}
```

You now need to wait 5 Minutes for the access token to expire. Once it expires we call the same endpoint with the same access token and should be denied access.

```bash
curl -i -X POST \
  --cacert certs/ca.crt \
  --cert certs/client.crt \
  --key certs/client.key \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"vehicleType":"mayor-vehicle","vehicleId":"7de3d833-8468-4105-8435-f178b6ff279c","trafficLightId":"8d8d1437-907b-3a79-900a-c5f0ea1f5c73"}' \
  "https://tcc.test/api/priority/requests" 
```

We now confirmed that the tokens expire and that should a token leak, the exploit is only short lived. 

Expected Output:

```bash
HTTP/2 401 
date: Sun, 01 Feb 2026 20:58:25 GMT
content-length: 0
www-authenticate: Bearer
strict-transport-security: max-age=31536000; includeSubDomains
```

Additionally the traffic status only changed once, as the same traffic light was always requested. Thus no traffic changes should have happened as it always stayed green. 

Expected Traffic Status:

```bash
--- GET /api/state/manual/state
Traffic Light Direction: north-south
UUID: 8d8d1437-907b-3a79-900a-c5f0ea1f5c73
Vehicle State: green
Pedestrian State: red
-----
Traffic Light Direction: south-north
UUID: 50fd76e3-3fe5-3961-bc5c-a99008af8904
Vehicle State: green
Pedestrian State: red
-----
Traffic Light Direction: west-east
UUID: da4f0053-a5c1-3882-a688-52ae2da2e466
Vehicle State: red
Pedestrian State: green
-----
Traffic Light Direction: east-west
UUID: 320381db-f7cd-3f31-804b-aa6b36e1c682
Vehicle State: red
Pedestrian State: green
-----
```

### Conclusion

These manual tests demonstrate that the traffic control system prevents the misuse case “unauthorized clients force green lights” by enforcing:

- **Authentication:** Without a valid access token, requests are rejected (TLS trust is required and missing tokens are denied).
- **Authorization:** Only the intended roles (**mayor-vehicle** and **emergency-vehicle**) can successfully request priority; other clients (**other-vehicle**, **pedestrian**) are denied and do not change the intersection state.
- **Integrity / non-spoofing:** Privilege escalation attempts fail both when spoofing `vehicleType` in the request body and when tampering with the JWT payload. The tampered token is rejected (invalid signature), and no traffic light state change occurs.

A remaining risk outside the scope of these tests is **credential leakage** (client secrets). If a client secret is compromised, an attacker could obtain valid tokens until the secret is rotated. However, token expiry limits the time window of misuse, and proper secret management (e.g., `.env`, rotation, least privilege) mitigates this risk.


