package com.constrsoft.services;

import com.constrsoft.dtos.httpsclients.keycloak.KeycloakUserDTO;
import com.constrsoft.httpclients.KeycloakFeignClient;
import com.constrsoft.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private KeycloakFeignClient feignClient;

    @Override
    public void createUser(String authorization, KeycloakUserDTO keycloakUser) {
        this.feignClient.createUser(authorization, keycloakUser);
    }

    @Override
    public List<KeycloakUserDTO> geAllUsers(String authorization) {
        final var response = this.feignClient.getAllUsers(authorization);

        return response.getBody();
    }

    @Override
    public KeycloakUserDTO getOneUser(String authorization, String id) {
        final var response = this.feignClient.getOneUser(authorization, id);

        return response.getBody();
    }

    @Override
    public void deleteUser(String authorization, String id) {
        final var response = this.feignClient.getOneUser(authorization, id);

        if (Objects.nonNull(response.getBody())) {
            final var user = response.getBody();

            user.setEnabled(false);

            this.feignClient.updateSomeUserInformation(authorization, id, user);
        }
    }

    @Override
    public KeycloakUserDTO updateSomeUserInformation(String authorization, String id, KeycloakUserDTO keycloakUserDTO) {
        final var response = this.feignClient.updateSomeUserInformation(authorization, id, keycloakUserDTO);

        return response.getBody();
    }

    @Override
    public KeycloakUserDTO updateAllUserInformation(String authorization, String id, KeycloakUserDTO keycloakUserDTO) {
        final var response = this.feignClient.updateAllUserInformation(authorization, id, keycloakUserDTO);

        return response.getBody();
    }
}
