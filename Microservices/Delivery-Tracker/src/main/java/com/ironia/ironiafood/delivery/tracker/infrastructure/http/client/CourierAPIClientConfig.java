package com.ironia.ironiafood.delivery.tracker.infrastructure.http.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class CourierAPIClientConfig {

    @Value("${api-client.baseUrl}")
    private String baseUrl;

    @Bean
    public CourierAPIClient courierAPIClient(RestClient.Builder builder) {

        RestClient restClient =  builder.baseUrl(baseUrl).build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory proxyFactory =  HttpServiceProxyFactory.builderFor(adapter).build();

        return proxyFactory.createClient(CourierAPIClient.class);
    }
}
