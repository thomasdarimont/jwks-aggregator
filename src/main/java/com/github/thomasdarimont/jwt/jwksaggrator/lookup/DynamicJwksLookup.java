package com.github.thomasdarimont.jwt.jwksaggrator.lookup;

import com.nimbusds.jose.jwk.JWKSet;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RequiredArgsConstructor
public abstract class DynamicJwksLookup implements JwksLookup {

    private final Duration cacheDuration;

    private final AtomicReference<JwksHolder> reference = new AtomicReference<>(new JwksHolder(null, 0L));

    @Data
    static class JwksHolder {

        private final JWKSet jwks;

        private final long updatedAt;
    }

    @Override
    public JWKSet lookup() {

        JwksHolder jwksHolder = this.reference.get();

        long dt = System.currentTimeMillis() - jwksHolder.getUpdatedAt();
        if (dt < cacheDuration.toMillis()) {
            log.debug("Skipping jwks lookup: cacheDuration. lookup={}", this);
            return jwksHolder.getJwks();
        }

        JWKSet jwks = fetch();

        long updatedAt = System.currentTimeMillis();
        if (jwks == null) {
            log.debug("Skipping empty jwks update. lookup={}", this);
            jwks = jwksHolder.getJwks();
        } else {
            log.info("Updated jwks. lookup={}", this);
        }
        this.reference.compareAndSet(jwksHolder, new JwksHolder(jwks, updatedAt));

        return jwks;
    }

    public abstract JWKSet fetch();
}
