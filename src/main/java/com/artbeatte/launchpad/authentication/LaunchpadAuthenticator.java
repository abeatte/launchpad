package com.artbeatte.launchpad.authentication;

import com.artbeatte.launchpad.core.User;
import com.artbeatte.launchpad.db.UserDAO;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class LaunchpadAuthenticator implements Authenticator<BasicCredentials, User> {

    private UserDAO userDAO;

    public LaunchpadAuthenticator(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        // TODO: sanitize credentials before checking SQL.
        User user = userDAO.findUser(credentials.getUsername(), credentials.getPassword());
        return Optional.fromNullable(user);
    }
}
