package com.constrsoft.dtos.httpsclients.keycloak;

import lombok.Data;

@Data
public class KeycloakAccessDTO {

    private Boolean manageGroupMembership;

    private Boolean view;

    private Boolean mapRoles;

    private Boolean impersonate;

    private Boolean manage;

}
