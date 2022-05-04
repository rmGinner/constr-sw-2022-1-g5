package com.constrsoft.controllers;


import com.constrsoft.controllers.interfaces.UsersController;
import com.constrsoft.dtos.httpsclients.keycloak.KeycloakUserDTO;
import com.constrsoft.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "users")
public class UsersControllerImpl implements UsersController {

    @Autowired
    private UserService service;

    @Override
    public ResponseEntity<KeycloakUserDTO> createUser(String authorization, KeycloakUserDTO keycloakUser) {
        final var createdUser = this.service.createUser(authorization, keycloakUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Override
    public ResponseEntity<List<KeycloakUserDTO>> getAllUsers(String authorization) {
        List<KeycloakUserDTO> users = this.service.geAllUsers(authorization);

        return this.buildNoContentOrOkResponse(users);
    }

    @Override
    public ResponseEntity<KeycloakUserDTO> getOneUser(String authorization, String id) {
        KeycloakUserDTO user = this.service.getOneUser(authorization, id);

        return this.buildNoContentOrOkResponse(user);
    }

    @Override
    public ResponseEntity<Void> deleteUser(String authorization, String id) {
        this.service.deleteUser(authorization, id);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<KeycloakUserDTO> updateSomeUserInformation(String authorization, String id, KeycloakUserDTO keycloakUserDTO) {
        KeycloakUserDTO user = this.service.updateSomeUserInformation(authorization, id, keycloakUserDTO);

        return this.buildNoContentOrOkResponse(user);
    }

    @Override
    public ResponseEntity<KeycloakUserDTO> updateAllUserInformation(String authorization, String id, KeycloakUserDTO keycloakUserDTO) {
        KeycloakUserDTO user = this.service.updateAllUserInformation(authorization, id, keycloakUserDTO);

        return this.buildNoContentOrOkResponse(user);
    }

    private <T> ResponseEntity<T> buildNoContentOrOkResponse(T dto) {
        if (dto instanceof List) {
            final var listDto = (List) dto;
            return CollectionUtils.isEmpty(listDto) ? ResponseEntity.noContent().build() : ResponseEntity.ok(dto);
        }

        return Objects.isNull(dto) ? ResponseEntity.noContent().build() : ResponseEntity.ok(dto);
    }
}
