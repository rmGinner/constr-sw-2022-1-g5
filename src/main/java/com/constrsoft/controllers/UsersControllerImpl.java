package com.constrsoft.controllers;


import com.constrsoft.controllers.interfaces.UsersController;
import com.constrsoft.dtos.httpsclients.keycloak.KeycloakUserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class UsersControllerImpl implements UsersController {


    @Override
    public ResponseEntity<Void> createUser(String authorization, KeycloakUserDTO keycloakUser) {
        return null;
    }

    @Override
    public ResponseEntity<List<KeycloakUserDTO>> getAllUsers(String authorization) {
        return null;
    }

    @Override
    public ResponseEntity<KeycloakUserDTO> getOneUser(String authorization, String id) {
        return null;
    }

    @Override
    public ResponseEntity<KeycloakUserDTO> updateSomeUserInformation(String authorization, String id, KeycloakUserDTO keycloakUserDTO) {
        return null;
    }

    @Override
    public ResponseEntity<KeycloakUserDTO> updateAllUserInformation(String authorization, String id, KeycloakUserDTO keycloakUserDTO) {
        return null;
    }
}
