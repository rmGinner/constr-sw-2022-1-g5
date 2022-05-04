package com.constrsoft.services;

import com.constrsoft.dtos.httpsclients.keycloak.KeycloakUserDTO;
import com.constrsoft.httpclients.KeycloakFeignClient;
import com.constrsoft.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private KeycloakFeignClient feignClient;

    @Override
    public KeycloakUserDTO createUser(String authorization, KeycloakUserDTO keycloakUser) {
        keycloakUser.setEnabled(true);

        this.feignClient.createUser(authorization, keycloakUser);
        final var createdKeycloakUser = this.feignClient.getOneUserByName(authorization, keycloakUser.getUsername());

        return CollectionUtils.isEmpty(createdKeycloakUser.getBody()) ? null : createdKeycloakUser.getBody().get(0);
    }

    @Override
    public List<KeycloakUserDTO> geAllUsers(String authorization) {
        final var response = this.feignClient.getAllEnabledUsers(authorization, true);

        return response.getBody();
    }

    @Override
    public KeycloakUserDTO getOneUser(String authorization, String id) {
        final var responseBody = this.feignClient.getOneUser(authorization, id).getBody();

        return Objects.nonNull(responseBody) && responseBody.getEnabled() ? responseBody : null;
    }

    @Override
    public void deleteUser(String authorization, String id) {
        final var response = this.feignClient.getOneUser(authorization, id);

        if (Objects.nonNull(response.getBody())) {
            final var user = response.getBody();

            user.setEnabled(false);

            this.feignClient.updateAllUserInformation(authorization, id, user);
        }
    }

    @Override
    public KeycloakUserDTO updateSomeUserInformation(String authorization, String id, KeycloakUserDTO keycloakUserDTO) {
        final var foundUser = this.getOneUser(authorization, id);

        if (Objects.nonNull(foundUser)) {
            this.feignClient.updateSomeUserInformation(authorization, id, keycloakUserDTO);

            return this.getOneUser(authorization, id);
        }

        return null;
    }

    @Override
    public KeycloakUserDTO updateAllUserInformation(String authorization, String id, KeycloakUserDTO keycloakUserDTO) {
        final var foundUser = this.getOneUser(authorization, id);

        if (Objects.nonNull(foundUser)) {
            this.feignClient.updateAllUserInformation(authorization, id, keycloakUserDTO);

            return this.getOneUser(authorization, id);
        }

        return null;
    }
}
