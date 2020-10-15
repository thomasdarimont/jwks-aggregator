package com.github.thomasdarimont.jwt.jwksaggrator.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties("jwksaggregator")
public class JwksAggregatorProperties {

    private List<Resource> fixedJwksLocations = new ArrayList<>();

    private List<URI> remoteJwksUris = new ArrayList<>();

    private JwksFetchingProperties fetch = new JwksFetchingProperties();

    private boolean eagerFetch = true;

    @Getter
    @Setter
    public static class JwksFetchingProperties {

        Duration cacheDuration = Duration.ofSeconds(30);

        int connectTimeoutMillis = 2000;

        int readTimeoutMillis = 5000;
    }
}
