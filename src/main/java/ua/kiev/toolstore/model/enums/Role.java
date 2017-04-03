package ua.kiev.toolstore.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ROLE_CUSTOMER,
    ROLE_USER,
    ROLE_ADMIN;

    public static final Role[] ALL = {ROLE_CUSTOMER, ROLE_USER, ROLE_ADMIN};

    public String getAuthority() {
        return name();
    }

}
