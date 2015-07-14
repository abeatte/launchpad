package com.artbeatte.launchpad.core;

import java.security.Principal;

/**
 * @author art.beatte
 * @version 7/14/15
 */
public class User implements Principal {
        private final String name;

        public User(String name) {
            this.name = name;
        }

    public String getName() {
        return name;
    }

    public int getId() {
        return (int) (Math.random() * 100);
    }
}
