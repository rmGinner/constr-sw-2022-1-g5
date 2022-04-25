package com.constrsoft.services;

import com.constrsoft.dtos.httpsclients.keycloak.AutenticationDTO;
import com.constrsoft.dtos.httpsclients.keycloak.KeycloakAuthenticationRequestDTO;
import com.constrsoft.dtos.httpsclients.keycloak.KeycloakAuthenticationResponseDTO;
import com.constrsoft.httpclients.KeycloakFeignClient;
import com.constrsoft.services.interfaces.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private KeycloakFeignClient keycloakFeignClient;

    @Override
    public KeycloakAuthenticationResponseDTO authenticate(AutenticationDTO autenticationDTO) {
        final var keycloakRequest = KeycloakAuthenticationRequestDTO.builder()
                .client_id(autenticationDTO.getClientId())
                .grant_type(autenticationDTO.getGrantType())
                .password(autenticationDTO.getPassword())
                .username(autenticationDTO.getUserName())
                .build();

        final var response = this.keycloakFeignClient.authenticate(
                keycloakRequest
        );

        return response.getBody();
    }
}
