package com.artbeatte.launchpad.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.id.GUIDGenerator;

import javax.persistence.*;
import java.security.Principal;
import java.util.Objects;
import java.util.UUID;

/**
 * @author art.beatte
 * @version 7/14/15
 */
@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "com.artbeatte.launchpad.core.User.findAll", query = "SELECT u FROM User u"),
        @NamedQuery(name = "com.artbeatte.launchpad.core.User.findUser", query = "SELECT u FROM User u WHERE userName = :uname AND password = :pwd")
})
public class User implements Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "userId", nullable = false)
    private UUID userId;

    @Column(name = "userName", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        // TODO: salt, store the salt, and calculate a hashed version of the pwd.
        this.password = password;
        this.userId = UUID.randomUUID();
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public UUID getUserId() {
        return userId;
    }

    public long getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;

        final User that = (User) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.userId, that.userId) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.password, that.password);
    }
}
