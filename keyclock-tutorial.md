# IV Software Security for Autonomous Systems

## Keycloak

## Dr. Karsten Bsufka

## karsten.bsufka@tu-berlin.de

## WiSe 2025/

# Relevant scripts

## Lecture cluster 06-keycloak Installs the Kubernetes Keycloak operator and a Keycloak instance.

## Example - Keycloak folder import-realm.sh Reads an exported Keycloak realm file and imports it into a running Keycloak instance in the Kubernetes cluster. Requires Python!

# Create and configure a realm in Keycloak

## 1. Open the Keycloak application in your web browser. Use the URL from the defined ingress, e.g., https://keycloak.test/

## 2. Create your own realm

## ▶Give your realm a name

## ▶Create clients and secrets for them

## ▶Create roles

## ▶Create users, passwords, and assign roles

## 3. Export your realm configuration (UI does export secrets!)

## ▶kubectl config set-context --current --namespace=keycloak

## ▶kubectl exec -it keycloak-0 -- bash -c "/opt/bitnami/keycloak/bin/kc.sh export --file /tmp/quarkus.json --realm 'Quarkus'"

## ▶kubectl cp keycloak-0:/tmp/quarkus.json quarkus.json

# Create your realm

# Create client

# Set client capability

# Set client secret

# Create user

# Set password for user

# Export a realm

## 1 kubectl config set-context --current --namespace=keycloak

## 2 kubectl exec -it keycloak-0 -- \

## 3 bash -c "/opt/bitnami/keycloak/bin/kc.sh \

## 4 export --file /tmp/quarkus.json --realm 'Quarkus'"

## 5 kubectl cp keycloak-0:/tmp/quarkus.json quarkus.json

# Keycloak, Quarkus, and development

## Quarkus offers DevServices for Keycloak. This means it starts a Docker container with Keycloak for development and testing and supports configuring these instances with a custom realm. DevServices can be shared between multiple running Quarkus applications.

## 1 quarkus.keycloak.devservices.realm-path=quarkus.json

## 2 quarkus.keycloak.devservices.enabled=true

## 3 quarkus.keycloak.devservices.image-name=quay.io/keycloak/keycloak:24.0.5

## 4 quarkus.keycloak.devservices.shared=true

## 5 quarkus.keycloak.devservices.service-name=quarkus-example

## 6 quarkus.keycloak.devservices.create-realm=false

## 7 quarkus.keycloak.devservices.realm-name=Quarkus

# application.properties - Keycloak client configuration

```
1 %prod.quarkus.kubernetes-config.secrets=hello-backend-tls-secret,hello-secrets
2 %dev.oidc-token=JijM1zLg5By3xEyOBTiwZ8wNe1t9zZXT
3 %test.oidc-token=JijM1zLg5By3xEyOBTiwZ8wNe1t9zZXT

5 #quarkus.log.category."io.quarkus.oidc".level=TRACE

7 # Configure the client that talks with Keycloak.
8 # Note: keycloak.url for dev/test mode will be configured by Quarkus.

10 quarkus.oidc.client-id=hello

12 %prod.keycloak.url=http://keycloak.keycloak.svc.cluster.local
13 %prod.quarkus.oidc.auth-server-url=${keycloak.url}/realms/Quarkus

15 quarkus.oidc.credentials.secret=${oidc-token}

17 %test.quarkus.oidc.token.issuer=any
18 %dev.quarkus.oidc.token.issuer=any
19 %prod.quarkus.oidc.token.issuer=${keycloak.url}/realms/Quarkus
```

# @RolesAllowed and @PermitAll - Restricting access (or not)

```
1 @Path("hello")
2 @ApplicationScoped
3 @DenyAll
4 public class HelloWorldResource {
5...
6 @GET
7 @Produces(MediaType.TEXT_PLAIN)
8 @Path("world")
9 @RolesAllowed("Receptionist")
10 public Uni<String> hello(@Context SecurityContext ctx) {
11...
12 }

14 @GET
15 @Produces(MediaType.TEXT_PLAIN)
16 @Path("/world-msg")
17 @PermitAll
18 public Uni<String> helloMessage(@Context SecurityContext ctx) {
19...
20 }
21 }
```

# REST API Client with Quarkus and Authorization header

```
1 @Path("hello")
2 @RegisterRestClient
3 @OidcClientFilter()
4 public interface HelloWorldClient {

6 @GET
7 @Produces(MediaType.TEXT_PLAIN)
8 @Path("/world")
9 public Uni<String> hello(@HeaderParam("Authorization") String authorization);
10 }
```

# application.properties - Do not do this - setting username and password for the client user

```
1 quarkus.oidc-client.grant.type=password
2 %prod.quarkus.oidc-client.grant-options.password.username=${username}
3 %prod.quarkus.oidc-client.grant-options.password.password=${password}
4 %dev.quarkus.oidc-client.grant-options.password.username=motd
5 %dev.quarkus.oidc-client.grant-options.password.password=changeme
6 %test.quarkus.oidc-client.grant-options.password.username=motd
7 %test.quarkus.oidc-client.grant-options.password.password=changeme
```
