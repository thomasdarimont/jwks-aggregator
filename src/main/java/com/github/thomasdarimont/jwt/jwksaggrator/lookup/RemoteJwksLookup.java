package com.github.thomasdarimont.jwt.jwksaggrator.lookup;

import com.nimbusds.jose.jwk.JWKSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.text.ParseException;
import java.time.Duration;

@Slf4j
public class RemoteJwksLookup extends DynamicJwksLookup {

    private final URI jwksUri;

    private final RestTemplate restTemplate;

    public RemoteJwksLookup(URI jwksUri, Duration cacheDuration, RestTemplate restTemplate) {
        super(cacheDuration);
        this.jwksUri = jwksUri;
        this.restTemplate = restTemplate;
    }

    @Override
    public JWKSet fetch() {

        try {
            String json = restTemplate.getForObject(jwksUri, String.class);
            return JWKSet.parse(json);
        } catch (ParseException e) {
            log.warn("Failed to parse JWKS response. error={}", e.getMessage());
            return null;
        } catch (ResourceAccessException e) {
            log.warn("Failed to fetch JWKS. error={}", e.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return "RemoteJwksLookup{" +
                "jwksUri=" + jwksUri +
                '}';
    }
}
