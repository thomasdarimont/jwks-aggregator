package com.github.thomasdarimont.jwt.jwksaggrator;

import com.nimbusds.jose.jwk.JWKSet;

public interface JwksService {
    JWKSet getJwks(String channel);
}
