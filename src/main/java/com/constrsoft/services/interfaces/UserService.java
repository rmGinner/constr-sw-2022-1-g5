package com.constrsoft.services.interfaces;

import com.constrsoft.dtos.httpsclients.keycloak.KeycloakUserDTO;

import java.util.List;

public interface UserService {

    KeycloakUserDTO createUser(String authorization, KeycloakUserDTO keycloakUser);

    List<KeycloakUserDTO> geAllUsers(String authorization);

    KeycloakUserDTO getOneUser(String authorization, String id);

    void deleteUser(String authorization, String id);

    KeycloakUserDTO updateSomeUserInformation(String authorization, String id, KeycloakUserDTO keycloakUserDTO);

    KeycloakUserDTO updateAllUserInformation(String authorization, String id, KeycloakUserDTO keycloakUserDTO);
}
