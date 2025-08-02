package com.nik.manager.config;

import com.nik.manager.client.RestClientProductsRestClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfigBeans {

    @Bean
    public RestClientProductsRestClientImpl productsRestClient(@Value("${selmag.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUri) {
        return new RestClientProductsRestClientImpl(RestClient.builder()
                .baseUrl(catalogueBaseUri)
                .build());

    }

}
