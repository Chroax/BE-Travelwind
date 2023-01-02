package com.binar.finalproject.BEFlightTicket.security.oauth2;

import com.binar.finalproject.BEFlightTicket.controller.OAuthController;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomAuthorizedClientService implements OAuth2AuthorizedClientService {

    private final GoogleAccountService googleAccountService;
    private final OAuthController oAuthController;

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
        return null;
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication authentication) {
       this.googleAccountService.findOrRegisterAccount(
               authentication.getName(),
               authentication.getName().split("\\|")[0],
               ((DefaultOidcUser) authentication.getPrincipal()).getClaims()
       );
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
    }

}