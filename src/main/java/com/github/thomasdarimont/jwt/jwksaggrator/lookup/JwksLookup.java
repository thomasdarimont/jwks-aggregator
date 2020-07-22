package com.github.thomasdarimont.jwt.jwksaggrator.lookup;

import com.nimbusds.jose.jwk.JWKSet;

public interface JwksLookup {

    JWKSet lookup();
}
