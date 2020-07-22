package com.github.thomasdarimont.jwt.jwksaggrator;

import com.github.thomasdarimont.jwt.jwksaggrator.config.JwksAggregatorProperties;
import com.github.thomasdarimont.jwt.jwksaggrator.lookup.JwksLookup;
import com.github.thomasdarimont.jwt.jwksaggrator.lookup.RemoteJwksLookup;
import com.github.thomasdarimont.jwt.jwksaggrator.lookup.ResourceBasedJwksLookup;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class DefaultJwksService implements JwksService {

    private final RestTemplate restTemplate;

    private final JwksAggregatorProperties jwksAggregatorProperties;

    private final List<JwksLookup> lookups = new ArrayList<>();

    @PostConstruct
    public void init() {

        List<Resource> fixedJwksLocations = jwksAggregatorProperties.getFixedJwksLocations();
        fixedJwksLocations.stream()
                .map(ResourceBasedJwksLookup::new)
                .peek(ResourceBasedJwksLookup::init)
                .collect(Collectors.toCollection(() -> lookups));

        List<URI> remoteJwksUris = jwksAggregatorProperties.getRemoteJwksUris();
        remoteJwksUris.stream()
                // TODO make timeout configurable
                .map(uri -> new RemoteJwksLookup(uri, Duration.ofSeconds(30), restTemplate))
//                .peek(RemoteJwksLookup::fetch)
                .collect(Collectors.toCollection(() -> lookups));
    }

    @Override
    public JWKSet getJwks(String channel) {

        List<JWK> jwk = new ArrayList<>();

        for (JwksLookup l : lookups) {
            JWKSet result = l.lookup();
            if (result != null) {
                jwk.addAll(result.getKeys());
            }
        }

        return new JWKSet(jwk);
    }
}
