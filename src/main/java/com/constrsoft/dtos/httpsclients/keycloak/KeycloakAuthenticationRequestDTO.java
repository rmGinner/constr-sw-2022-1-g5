package com.constrsoft.dtos.httpsclients.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KeycloakAuthenticationRequestDTO {

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("username")
    private String userName;

    private String password;

    @JsonProperty("grant_type")
    private String grantType;
}
