package com.constrsoft.dtos.httpsclients.keycloak;

import lombok.Data;

@Data
public class AutenticationDTO {

    private String clientId;

    private String userName;

    private String password;

    private String grantType;
}
