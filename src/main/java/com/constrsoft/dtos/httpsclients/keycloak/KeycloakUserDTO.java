package com.constrsoft.dtos.httpsclients.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KeycloakUserDTO {

    private String id;

    private String username;

    private Boolean enabled;

}
