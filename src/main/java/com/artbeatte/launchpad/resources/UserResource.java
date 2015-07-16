package com.artbeatte.launchpad.resources;


import com.artbeatte.launchpad.core.User;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/user_info")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private final String template;
    private final AtomicLong counter;

    public UserResource(String template) {
        this.template = template;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public User getUserInfo(@Auth User user) {
        return new User(user.getName(), user.getPassword());
    }
}
