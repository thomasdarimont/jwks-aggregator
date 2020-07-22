package com.github.thomasdarimont.jwt.jwksaggrator.lookup;

import com.nimbusds.jose.jwk.JWKSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

@Slf4j
@RequiredArgsConstructor
public class ResourceBasedJwksLookup implements JwksLookup {

    private final Resource resource;

    private JWKSet jwkSet;

    @Override
    public JWKSet lookup() {
        return jwkSet;
    }

    public void init() {

        try (InputStream in = resource.getInputStream()) {
            jwkSet = JWKSet.parse(StreamUtils.copyToString(in, StandardCharsets.UTF_8));
        } catch (IOException | ParseException e) {
            log.warn("Could not load JWKS from resource. resource={} error={}", resource.getFilename(), e.getMessage());
            jwkSet = null;
        }
    }

    @Override
    public String toString() {
        return "ResourceBasedJwksLookup{" +
                "resource=" + resource.getFilename() +
                '}';
    }
}
