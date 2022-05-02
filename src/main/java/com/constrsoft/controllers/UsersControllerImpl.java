package com.constrsoft.controllers;


import com.constrsoft.controllers.interfaces.UsersController;
import com.constrsoft.dtos.httpsclients.keycloak.KeycloakUserDTO;
import com.constrsoft.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersControllerImpl implements UsersController {

    @Autowired
    private UserService service;

    @Override
    public ResponseEntity<Void> createUser(String authorization, KeycloakUserDTO keycloakUser) {
        this.service.createUser(authorization, keycloakUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<List<KeycloakUserDTO>> getAllUsers(String authorization) {
        List<KeycloakUserDTO> users = this.service.geAllUsers(authorization);

        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<KeycloakUserDTO> getOneUser(String authorization, String id) {
        KeycloakUserDTO user = this.service.getOneUser(authorization, id);

        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<KeycloakUserDTO> updateSomeUserInformation(String authorization, String id, KeycloakUserDTO keycloakUserDTO) {
        KeycloakUserDTO user = this.service.updateSomeUserInformation(authorization, id, keycloakUserDTO);

        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<KeycloakUserDTO> updateAllUserInformation(String authorization, String id, KeycloakUserDTO keycloakUserDTO) {
        KeycloakUserDTO user = this.service.updateAllUserInformation(authorization, id, keycloakUserDTO);

        return ResponseEntity.ok(user);
    }
}
