package com.nik.manager.config;

import com.nik.manager.client.RestClientProductsRestClientImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ClientConfigBeansTest {

    @Mock
    ClientRegistrationRepository clientRegistrationRepository;

    @Mock
    OAuth2AuthorizedClientRepository authorizedClientRepository;

    @Test
    @DisplayName("productsRestClient создастся с валидными параметрами")
    void productsRestClient_ValidParameters_CreatesClient() {
        var configBeans = new ClientConfigBeans();
        var catalogueBaseUri = "http://localhost:8081";
        var registrationId = "keycloak";

        var result = configBeans.productsRestClient(catalogueBaseUri, clientRegistrationRepository, authorizedClientRepository, registrationId);

        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(RestClientProductsRestClientImpl.class);
    }

    @Test
    @DisplayName("productsRestClient создастся с пустым URI")
    void productsRestClient_EmptyUri_CreatesClient() {
        var configBeans = new ClientConfigBeans();
        var catalogueBaseUri = "";
        var registrationId = "keycloak";

        var result = configBeans.productsRestClient(catalogueBaseUri, clientRegistrationRepository, authorizedClientRepository, registrationId);

        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(RestClientProductsRestClientImpl.class);
    }

    @Test
    @DisplayName("productsRestClient создастся с null URI")
    void productsRestClient_NullUri_CreatesClient() {
        var configBeans = new ClientConfigBeans();
        String catalogueBaseUri = null;
        var registrationId = "keycloak";

        var result = configBeans.productsRestClient(catalogueBaseUri, clientRegistrationRepository, authorizedClientRepository, registrationId);

        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(RestClientProductsRestClientImpl.class);
    }
} 