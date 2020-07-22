package com.github.thomasdarimont.jwt.jwksaggrator.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties("jwksaggregator")
public class JwksAggregatorProperties {

    private List<Resource> fixedJwksLocations = new ArrayList<>();

    private List<URI> remoteJwksUris = new ArrayList<>();
}
