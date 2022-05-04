package com.constrsoft.dtos.httpsclients.keycloak;

import lombok.Data;

import java.util.List;

@Data
public class KeycloakUserDTO {

    private String id;

    private String username;

    private String firstName;

    private String lastName;

    private Boolean enabled;

    private Long createdTimestamp;

    private Boolean emailVerified;

    private List disableableCredentialTypes;

    private List requiredActions;

    private List federatedIdentities;

    private KeycloakAccessDTO access;
}
