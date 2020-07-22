package com.github.thomasdarimont.jwt.jwksaggrator.lookup;

import com.nimbusds.jose.jwk.JWKSet;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StaticJwksLookup implements JwksLookup {

    private final JWKSet jwks;

    @Override
    public JWKSet lookup() {
        return jwks;
    }

    @Override
    public String toString() {
        return "StaticJwksLookup{}";
    }
}
