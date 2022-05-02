package com.constrsoft.controllers.interfaces;

import com.constrsoft.dtos.httpsclients.keycloak.AutenticationDTO;
import com.constrsoft.dtos.httpsclients.keycloak.KeycloakAuthenticationResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "authentications")
public interface AuthenticationsController {

    @PostMapping
    ResponseEntity<KeycloakAuthenticationResponseDTO> authenticate(
            @RequestBody AutenticationDTO autenticationDTO
    );

}
