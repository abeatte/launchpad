package com.artbeatte.launchpad.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.security.Principal;

public class User implements Principal {
    private final String name;
    private final String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    public int getId() {
        return (int) (Math.random() * 100);
    }
}
