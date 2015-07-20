package com.artbeatte.launchpad.resources;


import com.artbeatte.launchpad.core.User;
import com.artbeatte.launchpad.db.UserDAO;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author art.beatte
 * @version 7/20/15
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private final UserDAO userDAO;

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GET
    @Timed(name = "get-requests")
    @UnitOfWork
    @Path("{userId}/user_info")
    public User getUserInfo(@Auth User user, @PathParam("userId") LongParam userId) {
        return findSafely(userId.get());
    }

    @POST
    @UnitOfWork
    @Path("{userId}")
    public User createUser(@Auth User activeUser, @PathParam("userId") LongParam userId, User user) {
        return userDAO.create(user);
    }

    @GET
    @Timed(name = "get-requests")
    @UnitOfWork
    public List<User> listUsers() { // TODO: lock with @Auth later
        return userDAO.findAll();
    }

    @GET
    @Timed(name = "get-requests")
    @UnitOfWork
    @Path("userId")
    public User getUser(@Auth User user, @PathParam("userId") LongParam userId) {
        return findSafely(userId.get());
    }

    private User findSafely(long userId) {
        final Optional<User> user = userDAO.findById(userId);
        if (!user.isPresent()) {
            throw new NotFoundException("No such user.");
        }
        return user.get();
    }
}
