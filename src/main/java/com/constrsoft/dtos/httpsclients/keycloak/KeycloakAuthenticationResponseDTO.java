package com.constrsoft.dtos.httpsclients.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KeycloakAuthenticationResponseDTO {

    @JsonProperty("access_token")
    private String accessToken;

}
