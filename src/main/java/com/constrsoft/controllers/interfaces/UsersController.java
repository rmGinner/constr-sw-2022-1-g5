package com.constrsoft.controllers.interfaces;

import com.constrsoft.dtos.httpsclients.keycloak.KeycloakUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/users")
public interface UsersController {

    @PostMapping
    ResponseEntity<Void> createUser(
            @RequestHeader("Authorization") String authorization,
            @RequestBody KeycloakUserDTO keycloakUser
    );

    @GetMapping
    ResponseEntity<List<KeycloakUserDTO>> getAllUsers(
            @RequestHeader("Authorization") String authorization
    );

    @GetMapping(path = "{id}")
    ResponseEntity<KeycloakUserDTO> getOneUser(
            @RequestHeader("Authorization") String authorization,
            @PathVariable String id
    );

    @DeleteMapping(path = "{id}")
    ResponseEntity<Void> deleteUser(
            @RequestHeader("Authorization") String authorization,
            @PathVariable String id
    );

    @PatchMapping(path = "{id}")
    ResponseEntity<KeycloakUserDTO> updateSomeUserInformation(
            @RequestHeader("Authorization") String authorization,
            @PathVariable String id,
            @RequestBody KeycloakUserDTO keycloakUserDTO
    );

    @PutMapping(path = "{id}")
    ResponseEntity<KeycloakUserDTO> updateAllUserInformation(
            @RequestHeader("Authorization") String authorization,
            @PathVariable String id,
            @RequestBody KeycloakUserDTO keycloakUserDTO
    );
}
