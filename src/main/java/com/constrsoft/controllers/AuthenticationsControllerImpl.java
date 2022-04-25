package com.constrsoft.controllers;

import com.constrsoft.controllers.interfaces.AuthenticationsController;
import com.constrsoft.dtos.httpsclients.keycloak.KeycloakAuthenticationRequestDTO;
import com.constrsoft.dtos.httpsclients.keycloak.KeycloakAuthenticationResponseDTO;
import com.constrsoft.services.interfaces.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationsControllerImpl implements AuthenticationsController {

    @Autowired
    private AuthenticationService service;

    @Override
    public ResponseEntity<KeycloakAuthenticationResponseDTO> authenticate(KeycloakAuthenticationRequestDTO keycloakAuthenticationRequestDTO) {
        return ResponseEntity.ok(this.service.authenticate(keycloakAuthenticationRequestDTO));
    }
}
