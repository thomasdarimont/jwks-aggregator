package com.github.thomasdarimont.jwt.jwksaggrator.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(JwksAggregatorProperties.class)
class JwksAggregatorConfig {

    @Bean
    RestTemplate restTemplate(JwksAggregatorProperties jwksAggregatorProperties) {

        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(jwksAggregatorProperties.getFetch().getConnectTimeoutMillis());
        requestFactory.setReadTimeout(jwksAggregatorProperties.getFetch().getReadTimeoutMillis());
        restTemplate.setRequestFactory(requestFactory);

        return restTemplate;
    }
}
