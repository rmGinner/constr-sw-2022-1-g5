package com.constrsoft.services;

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
    public KeycloakAuthenticationResponseDTO authenticate(KeycloakAuthenticationRequestDTO keycloakAuthenticationRequestDTO) {
        final var response = this.keycloakFeignClient.authenticate(keycloakAuthenticationRequestDTO);

        return response.getBody();
    }
}
