package com.pokemon.pokemon;

import org.springframework.security.core.GrantedAuthority;

public  enum TypeDeRole implements GrantedAuthority {
    ROLE_USER( "USER" ),
    ROLE_ADMIN( "ADMIN" );

    private String value;

    TypeDeRole(String value) {
        this .value = value;
    }

    public String getValue () {
        return  this .value;
    }

    @Override
    public String getAuthority () {
        return name();
    }
}