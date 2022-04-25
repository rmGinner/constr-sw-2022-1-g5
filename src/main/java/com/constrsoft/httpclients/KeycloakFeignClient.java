package com.constrsoft.httpclients;

import com.constrsoft.CustomFeignConfiguration;
import com.constrsoft.dtos.httpsclients.keycloak.KeycloakAuthenticationRequestDTO;
import com.constrsoft.dtos.httpsclients.keycloak.KeycloakAuthenticationResponseDTO;
import com.constrsoft.dtos.httpsclients.keycloak.KeycloakUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(url = "${keycloak.url}", name = "keycloak")
public interface KeycloakFeignClient {

    @PostMapping(
            path = "/auth/realms/aulas-keycloak/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<KeycloakAuthenticationResponseDTO> authenticate(
            @RequestBody KeycloakAuthenticationRequestDTO keycloakAuthenticationRequestDTO
    );

    @PostMapping(path = "/auth/admin/realms/aulas-keycloak/users")
    ResponseEntity<Void> createUser(
            @RequestHeader("Authorization") String authorization,
            @RequestBody KeycloakUserDTO keycloakUser
    );

    @GetMapping(path = "/auth/admin/realms/aulas-keycloak/users")
    ResponseEntity<List<KeycloakUserDTO>> getAllUsers(
            @RequestHeader("Authorization") String authorization
    );

    @GetMapping(path = "/auth/admin/realms/aulas-keycloak/users/{id}")
    ResponseEntity<KeycloakUserDTO> getOneUser(
            @RequestHeader("Authorization") String authorization,
            @PathVariable String id
    );

    @PatchMapping(path = "/auth/admin/realms/aulas-keycloak/users/{id}")
    ResponseEntity<KeycloakUserDTO> updateSomeUserInformation(
            @RequestHeader("Authorization") String authorization,
            @PathVariable String id,
            @RequestBody KeycloakUserDTO keycloakUserDTO
    );

    @PutMapping(path = "/auth/admin/realms/aulas-keycloak/users/{id}")
    ResponseEntity<KeycloakUserDTO> updateAllUserInformation(
            @RequestHeader("Authorization") String authorization,
            @PathVariable String id,
            @RequestBody KeycloakUserDTO keycloakUserDTO
    );
}
