package com.constrsoft.services.interfaces;

import com.constrsoft.dtos.httpsclients.keycloak.KeycloakAuthenticationRequestDTO;
import com.constrsoft.dtos.httpsclients.keycloak.KeycloakAuthenticationResponseDTO;
import org.springframework.stereotype.Service;


public interface AuthenticationService {

    KeycloakAuthenticationResponseDTO authenticate(KeycloakAuthenticationRequestDTO keycloakAuthenticationRequestDTO);
}
