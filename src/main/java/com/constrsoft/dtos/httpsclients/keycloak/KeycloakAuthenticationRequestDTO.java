package com.constrsoft.dtos.httpsclients.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KeycloakAuthenticationRequestDTO {

    private String client_id;

    private String username;

    private String password;

    private String grant_type;
}
