JWKS-Aggregator
---

Exposes a merged JWKS document based on collected JWKS documents from multiple sources.

# Build
```
mvn clean package
```

# Run
```
java -jar target/*.jar
```

# Config

JWKS resources can be specified via Resource locations or URIs
```yaml

jwksaggregator:
  fixedJwksLocations:
    - classpath:jwks/customer-jwks.json
  remoteJwksUris:
    - http://localhost:8080/auth/realms/demo/protocol/openid-connect/certs
    - http://localhost:8081/auth/realms/acme-apps/protocol/openid-connect/certs

```