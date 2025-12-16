This is a collection of functioning curl calls if you are inside a test cluster with a proper Certificate mounted:

### time-service 

```
curl -v \
  --cacert /certs/ca.crt \
  --cert /certs/tls.crt \
  --key /certs/tls.key \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{"timestamp":1}' \
  "https://gruppe8-time-service.gruppe8-shared-services.svc.cluster.local:443/api/time/validate"
```

Output:

```
* Host gruppe8-time-service.gruppe8-shared-services.svc.cluster.local:443 was resolved.
* IPv6: (none)
* IPv4: 10.96.165.17
*   Trying 10.96.165.17:443...
* Connected to gruppe8-time-service.gruppe8-shared-services.svc.cluster.local (10.96.165.17) port 443
* ALPN: curl offers h2,http/1.1
* TLSv1.3 (OUT), TLS handshake, Client hello (1):
*  CAfile: /certs/ca.crt
*  CApath: /etc/ssl/certs
* TLSv1.3 (IN), TLS handshake, Server hello (2):
* TLSv1.3 (IN), TLS handshake, Encrypted Extensions (8):
* TLSv1.3 (IN), TLS handshake, Request CERT (13):
* TLSv1.3 (IN), TLS handshake, Certificate (11):
* TLSv1.3 (IN), TLS handshake, CERT verify (15):
* TLSv1.3 (IN), TLS handshake, Finished (20):
* TLSv1.3 (OUT), TLS change cipher, Change cipher spec (1):
* TLSv1.3 (OUT), TLS handshake, Certificate (11):
* TLSv1.3 (OUT), TLS handshake, CERT verify (15):
* TLSv1.3 (OUT), TLS handshake, Finished (20):
* SSL connection using TLSv1.3 / TLS_AES_256_GCM_SHA384 / X25519 / id-ecPublicKey
* ALPN: server accepted h2
* Server certificate:
*  subject: O=Gruppe 8; OU=Task 4; CN=time-service.svc.cluster.local
*  start date: Dec 15 13:15:41 2025 GMT
*  expire date: Mar 15 13:15:41 2026 GMT
*  subjectAltName: host "gruppe8-time-service.gruppe8-shared-services.svc.cluster.local" matched cert's "gruppe8-time-service.gruppe8-shared-services.svc.cluster.local"
*  issuer: O=Gruppe 8; OU=Task 1; CN=Krzysztof Lagowski CA
*  SSL certificate verify ok.
*   Certificate level 0: Public key type EC/prime256v1 (256/128 Bits/secBits), signed using ecdsa-with-SHA256
*   Certificate level 1: Public key type EC/prime256v1 (256/128 Bits/secBits), signed using ecdsa-with-SHA256
*   Certificate level 2: Public key type EC/prime256v1 (256/128 Bits/secBits), signed using ecdsa-with-SHA256
* using HTTP/2
* [HTTP/2] [1] OPENED stream for https://gruppe8-time-service.gruppe8-shared-services.svc.cluster.local:443/api/time/validate
* [HTTP/2] [1] [:method: POST]
* [HTTP/2] [1] [:scheme: https]
* [HTTP/2] [1] [:authority: gruppe8-time-service.gruppe8-shared-services.svc.cluster.local]
* [HTTP/2] [1] [:path: /api/time/validate]
* [HTTP/2] [1] [user-agent: curl/8.5.0]
* [HTTP/2] [1] [content-type: application/json]
* [HTTP/2] [1] [accept: application/json]
* [HTTP/2] [1] [content-length: 15]
> POST /api/time/validate HTTP/2
> Host: gruppe8-time-service.gruppe8-shared-services.svc.cluster.local
> User-Agent: curl/8.5.0
> Content-Type: application/json
> Accept: application/json
> Content-Length: 15
> 
* TLSv1.3 (IN), TLS handshake, Newsession Ticket (4):
< HTTP/2 200 
< content-length: 60
< content-type: application/json;charset=UTF-8
< 
* Connection #0 to host gruppe8-time-service.gruppe8-shared-services.svc.cluster.local left intact
{"valid":false,"message":"Timestamp is outside valid range"}
```

### location-validator

```
curl -v \
  --cacert /certs/ca.crt \
  --cert /certs/tls.crt \
  --key /certs/tls.key \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
        "vehicleId": "vehicle-123",
        "coordinates": {
          "latitude": 52.5200,
          "longitude": 13.4050
        }
      }' \
  "https://gruppe8-location-validator.gruppe8-shared-services.svc.cluster.local:443/api/location/vehicle"
```

Expected output:

```
* Host gruppe8-location-validator.gruppe8-shared-services.svc.cluster.local:443 was resolved.
* IPv6: (none)
* IPv4: 10.96.108.8
*   Trying 10.96.108.8:443...
* Connected to gruppe8-location-validator.gruppe8-shared-services.svc.cluster.local (10.96.108.8) port 443
* ALPN: curl offers h2,http/1.1
* TLSv1.3 (OUT), TLS handshake, Client hello (1):
*  CAfile: /certs/ca.crt
*  CApath: /etc/ssl/certs
* TLSv1.3 (IN), TLS handshake, Server hello (2):
* TLSv1.3 (IN), TLS handshake, Encrypted Extensions (8):
* TLSv1.3 (IN), TLS handshake, Request CERT (13):
* TLSv1.3 (IN), TLS handshake, Certificate (11):
* TLSv1.3 (IN), TLS handshake, CERT verify (15):
* TLSv1.3 (IN), TLS handshake, Finished (20):
* TLSv1.3 (OUT), TLS change cipher, Change cipher spec (1):
* TLSv1.3 (OUT), TLS handshake, Certificate (11):
* TLSv1.3 (OUT), TLS handshake, CERT verify (15):
* TLSv1.3 (OUT), TLS handshake, Finished (20):
* SSL connection using TLSv1.3 / TLS_AES_256_GCM_SHA384 / X25519 / id-ecPublicKey
* ALPN: server accepted h2
* Server certificate:
*  subject: O=Gruppe 8; OU=Task 4; CN=location-validator.svc.cluster.local
*  start date: Dec 15 13:15:41 2025 GMT
*  expire date: Mar 15 13:15:41 2026 GMT
*  subjectAltName: host "gruppe8-location-validator.gruppe8-shared-services.svc.cluster.local" matched cert's "gruppe8-location-validator.gruppe8-shared-services.svc.cluster.local"
*  issuer: O=Gruppe 8; OU=Task 1; CN=Krzysztof Lagowski CA
*  SSL certificate verify ok.
*   Certificate level 0: Public key type EC/prime256v1 (256/128 Bits/secBits), signed using ecdsa-with-SHA256
*   Certificate level 1: Public key type EC/prime256v1 (256/128 Bits/secBits), signed using ecdsa-with-SHA256
*   Certificate level 2: Public key type EC/prime256v1 (256/128 Bits/secBits), signed using ecdsa-with-SHA256
* using HTTP/2
* [HTTP/2] [1] OPENED stream for https://gruppe8-location-validator.gruppe8-shared-services.svc.cluster.local:443/api/location/vehicle
* [HTTP/2] [1] [:method: POST]
* [HTTP/2] [1] [:scheme: https]
* [HTTP/2] [1] [:authority: gruppe8-location-validator.gruppe8-shared-services.svc.cluster.local]
* [HTTP/2] [1] [:path: /api/location/vehicle]
* [HTTP/2] [1] [user-agent: curl/8.5.0]
* [HTTP/2] [1] [content-type: application/json]
* [HTTP/2] [1] [accept: application/json]
* [HTTP/2] [1] [content-length: 142]
> POST /api/location/vehicle HTTP/2
> Host: gruppe8-location-validator.gruppe8-shared-services.svc.cluster.local
> User-Agent: curl/8.5.0
> Content-Type: application/json
> Accept: application/json
> Content-Length: 142
> 
* TLSv1.3 (IN), TLS handshake, Newsession Ticket (4):
< HTTP/2 200 
< content-length: 58
< content-type: application/json;charset=UTF-8
< 
* Connection #0 to host gruppe8-location-validator.gruppe8-shared-services.svc.cluster.local left intact
{"valid":true,"message":"Location validated successfully"}
```

### traffic-light-devices

```
curl -v \
  --cacert /certs/ca.crt \
  --cert /certs/tls.crt \
  --key /certs/tls.key \
  "https://gruppe8-traffic-light-device-service.gruppe8-traffic-light-devices.svc.cluster.local:443/api/device/traffic-state"
```

Output:

```
* Host gruppe8-traffic-light-device-service.gruppe8-traffic-light-devices.svc.cluster.local:443 was resolved.
* IPv6: (none)
* IPv4: 10.96.72.33
*   Trying 10.96.72.33:443...
* Connected to gruppe8-traffic-light-device-service.gruppe8-traffic-light-devices.svc.cluster.local (10.96.72.33) port 443
* ALPN: curl offers h2,http/1.1
* TLSv1.3 (OUT), TLS handshake, Client hello (1):
*  CAfile: /certs/ca.crt
*  CApath: /etc/ssl/certs
* TLSv1.3 (IN), TLS handshake, Server hello (2):
* TLSv1.3 (IN), TLS handshake, Encrypted Extensions (8):
* TLSv1.3 (IN), TLS handshake, Request CERT (13):
* TLSv1.3 (IN), TLS handshake, Certificate (11):
* TLSv1.3 (IN), TLS handshake, CERT verify (15):
* TLSv1.3 (IN), TLS handshake, Finished (20):
* TLSv1.3 (OUT), TLS change cipher, Change cipher spec (1):
* TLSv1.3 (OUT), TLS handshake, Certificate (11):
* TLSv1.3 (OUT), TLS handshake, CERT verify (15):
* TLSv1.3 (OUT), TLS handshake, Finished (20):
* SSL connection using TLSv1.3 / TLS_AES_256_GCM_SHA384 / X25519 / id-ecPublicKey
* ALPN: server accepted h2
* Server certificate:
*  subject: O=Gruppe 8; OU=Task 4; CN=gruppe8-traffic-light-device-service.svc.cluster.local
*  start date: Dec 16 16:49:13 2025 GMT
*  expire date: Mar 16 16:49:13 2026 GMT
*  subjectAltName: host "gruppe8-traffic-light-device-service.gruppe8-traffic-light-devices.svc.cluster.local" matched cert's "gruppe8-traffic-light-device-service.gruppe8-traffic-light-devices.svc.cluster.local"
*  issuer: O=Gruppe 8; OU=Task 1; CN=Krzysztof Lagowski CA
*  SSL certificate verify ok.
*   Certificate level 0: Public key type EC/prime256v1 (256/128 Bits/secBits), signed using ecdsa-with-SHA256
*   Certificate level 1: Public key type EC/prime256v1 (256/128 Bits/secBits), signed using ecdsa-with-SHA256
*   Certificate level 2: Public key type EC/prime256v1 (256/128 Bits/secBits), signed using ecdsa-with-SHA256
* using HTTP/2
* [HTTP/2] [1] OPENED stream for https://gruppe8-traffic-light-device-service.gruppe8-traffic-light-devices.svc.cluster.local:443/api/device/traffic-state
* [HTTP/2] [1] [:method: GET]
* [HTTP/2] [1] [:scheme: https]
* [HTTP/2] [1] [:authority: gruppe8-traffic-light-device-service.gruppe8-traffic-light-devices.svc.cluster.local]
* [HTTP/2] [1] [:path: /api/device/traffic-state]
* [HTTP/2] [1] [user-agent: curl/8.5.0]
* [HTTP/2] [1] [accept: */*]
> GET /api/device/traffic-state HTTP/2
> Host: gruppe8-traffic-light-device-service.gruppe8-traffic-light-devices.svc.cluster.local
> User-Agent: curl/8.5.0
> Accept: */*
> 
* TLSv1.3 (IN), TLS handshake, Newsession Ticket (4):
< HTTP/2 200 
< content-type: application/json;charset=UTF-8
< content-length: 52
< 
* Connection #0 to host gruppe8-traffic-light-device-service.gruppe8-traffic-light-devices.svc.cluster.local left intact
{"state":"green","timestamp":"2024-01-01T12:00:00Z"}
```
### tcc-audit-service

```
curl -v \
  -X GET \
  --cacert /certs/ca.crt \
  --cert /certs/tls.crt \
  --key /certs/tls.key \
  "https://gruppe8-tcc-audit-service.gruppe8-tcc.svc.cluster.local:443/api/audit/logs"
```

Expected output:

```
* Host gruppe8-tcc-audit-service.gruppe8-tcc.svc.cluster.local:443 was resolved.
* IPv6: (none)
* IPv4: 10.96.41.1
*   Trying 10.96.41.1:443...
* Connected to gruppe8-tcc-audit-service.gruppe8-tcc.svc.cluster.local (10.96.41.1) port 443
* ALPN: curl offers h2,http/1.1
* TLSv1.3 (OUT), TLS handshake, Client hello (1):
*  CAfile: /certs/ca.crt
*  CApath: /etc/ssl/certs
* TLSv1.3 (IN), TLS handshake, Server hello (2):
* TLSv1.3 (IN), TLS handshake, Encrypted Extensions (8):
* TLSv1.3 (IN), TLS handshake, Request CERT (13):
* TLSv1.3 (IN), TLS handshake, Certificate (11):
* TLSv1.3 (IN), TLS handshake, CERT verify (15):
* TLSv1.3 (IN), TLS handshake, Finished (20):
* TLSv1.3 (OUT), TLS change cipher, Change cipher spec (1):
* TLSv1.3 (OUT), TLS handshake, Certificate (11):
* TLSv1.3 (OUT), TLS handshake, CERT verify (15):
* TLSv1.3 (OUT), TLS handshake, Finished (20):
* SSL connection using TLSv1.3 / TLS_AES_256_GCM_SHA384 / X25519 / id-ecPublicKey
* ALPN: server accepted h2
* Server certificate:
*  subject: O=Gruppe 8; OU=Task 4; CN=tcc-audit-service.gruppe8-tcc.svc.cluster.local
*  start date: Dec 15 13:15:42 2025 GMT
*  expire date: Mar 15 13:15:42 2026 GMT
*  subjectAltName: host "gruppe8-tcc-audit-service.gruppe8-tcc.svc.cluster.local" matched cert's "gruppe8-tcc-audit-service.gruppe8-tcc.svc.cluster.local"
*  issuer: O=Gruppe 8; OU=Task 1; CN=Krzysztof Lagowski CA
*  SSL certificate verify ok.
*   Certificate level 0: Public key type EC/prime256v1 (256/128 Bits/secBits), signed using ecdsa-with-SHA256
*   Certificate level 1: Public key type EC/prime256v1 (256/128 Bits/secBits), signed using ecdsa-with-SHA256
*   Certificate level 2: Public key type EC/prime256v1 (256/128 Bits/secBits), signed using ecdsa-with-SHA256
* using HTTP/2
* [HTTP/2] [1] OPENED stream for https://gruppe8-tcc-audit-service.gruppe8-tcc.svc.cluster.local:443/api/audit/logs
* [HTTP/2] [1] [:method: GET]
* [HTTP/2] [1] [:scheme: https]
* [HTTP/2] [1] [:authority: gruppe8-tcc-audit-service.gruppe8-tcc.svc.cluster.local]
* [HTTP/2] [1] [:path: /api/audit/logs]
* [HTTP/2] [1] [user-agent: curl/8.5.0]
* [HTTP/2] [1] [accept: */*]
> GET /api/audit/logs HTTP/2
> Host: gruppe8-tcc-audit-service.gruppe8-tcc.svc.cluster.local
> User-Agent: curl/8.5.0
> Accept: */*
> 
* TLSv1.3 (IN), TLS handshake, Newsession Ticket (4):
< HTTP/2 200 
< content-type: application/json;charset=UTF-8
< content-length: 2
< 
* Connection #0 to host gruppe8-tcc-audit-service.gruppe8-tcc.svc.cluster.local left intact
```

### tcc-state-controller


```
curl -vk \
  -X POST \
  --cacert /certs/ca.crt \
  --cert /certs/tls.crt \
  --key /certs/tls.key \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
        "state": "green"
      }' \
  "https://gruppe8-tcc-state-controller.gruppe8-tcc.svc.cluster.local:443/api/state/device/change-state"
```

Output:

```
* Host gruppe8-tcc-state-controller.gruppe8-tcc.svc.cluster.local:443 was resolved.
* IPv6: (none)
* IPv4: 10.96.248.194
*   Trying 10.96.248.194:443...
* Connected to gruppe8-tcc-state-controller.gruppe8-tcc.svc.cluster.local (10.96.248.194) port 443
* ALPN: curl offers h2,http/1.1
* TLSv1.3 (OUT), TLS handshake, Client hello (1):
* TLSv1.3 (IN), TLS handshake, Server hello (2):
* TLSv1.3 (IN), TLS handshake, Encrypted Extensions (8):
* TLSv1.3 (IN), TLS handshake, Request CERT (13):
* TLSv1.3 (IN), TLS handshake, Certificate (11):
* TLSv1.3 (IN), TLS handshake, CERT verify (15):
* TLSv1.3 (IN), TLS handshake, Finished (20):
* TLSv1.3 (OUT), TLS change cipher, Change cipher spec (1):
* TLSv1.3 (OUT), TLS handshake, Certificate (11):
* TLSv1.3 (OUT), TLS handshake, CERT verify (15):
* TLSv1.3 (OUT), TLS handshake, Finished (20):
* SSL connection using TLSv1.3 / TLS_AES_256_GCM_SHA384 / X25519 / id-ecPublicKey
* ALPN: server accepted h2
* Server certificate:
*  subject: O=Gruppe 8; OU=Task 4; CN=tcc-state-controller.gruppe8-tcc.svc.cluster.local
*  start date: Dec 16 17:10:52 2025 GMT
*  expire date: Mar 16 17:10:52 2026 GMT
*  issuer: O=Gruppe 8; OU=Task 1; CN=Krzysztof Lagowski CA
*  SSL certificate verify result: unable to get local issuer certificate (20), continuing anyway.
*   Certificate level 0: Public key type EC/prime256v1 (256/128 Bits/secBits), signed using ecdsa-with-SHA256
*   Certificate level 1: Public key type EC/prime256v1 (256/128 Bits/secBits), signed using ecdsa-with-SHA256
* using HTTP/2
* [HTTP/2] [1] OPENED stream for https://gruppe8-tcc-state-controller.gruppe8-tcc.svc.cluster.local:443/api/state/device/change-state
* [HTTP/2] [1] [:method: POST]
* [HTTP/2] [1] [:scheme: https]
* [HTTP/2] [1] [:authority: gruppe8-tcc-state-controller.gruppe8-tcc.svc.cluster.local]
* [HTTP/2] [1] [:path: /api/state/device/change-state]
* [HTTP/2] [1] [user-agent: curl/8.5.0]
* [HTTP/2] [1] [content-type: application/json]
* [HTTP/2] [1] [accept: application/json]
* [HTTP/2] [1] [content-length: 34]
> POST /api/state/device/change-state HTTP/2
> Host: gruppe8-tcc-state-controller.gruppe8-tcc.svc.cluster.local
> User-Agent: curl/8.5.0
> Content-Type: application/json
> Accept: application/json
> Content-Length: 34
> 
* TLSv1.3 (IN), TLS handshake, Newsession Ticket (4):
< HTTP/2 200 
< content-length: 0
< 
* Connection #0 to host gruppe8-tcc-state-controller.gruppe8-tcc.svc.cluster.local left intact
```