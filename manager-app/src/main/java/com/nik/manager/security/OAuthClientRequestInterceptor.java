package com.nik.manager.security;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import java.io.IOException;

@RequiredArgsConstructor
public class OAuthClientRequestInterceptor implements ClientHttpRequestInterceptor {
    private final OAuth2AuthorizedClientManager authorizedClientManager;
    private final String registrationId;

    @Setter
    private SecurityContextHolderStrategy contextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            var authorize = this.authorizedClientManager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId(this.registrationId)
                    .principal(this.contextHolderStrategy.getContext().getAuthentication())
                    .build());
            request.getHeaders().setBearerAuth(authorize.getAccessToken().getTokenValue());
        }

        return execution.execute(request, body);
    }
}
