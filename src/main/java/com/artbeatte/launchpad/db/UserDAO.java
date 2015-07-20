package com.artbeatte.launchpad.db;

import com.artbeatte.launchpad.core.User;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * @author art.beatte
 * @version 7/16/15
 */
public class UserDAO extends AbstractDAO<User> {
    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<User> findById(Long id) {
        return Optional.fromNullable(get(id));
    }

    public User create(User user) {
        return persist(user);
    }

    public List<User> findAll() {
        return list(namedQuery("com.artbeatte.launchpad.core.User.findAll"));
    }

    public User findUser(String userName, String password) {
        return uniqueResult(namedQuery("com.artbeatte.launchpad.core.User.findUser")
                .setParameter("uname", userName)
                .setParameter("pwd", password));
    }
}
