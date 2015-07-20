package com.artbeatte.launchpad.cli;

import com.artbeatte.launchpad.LaunchpadConfiguration;
import com.artbeatte.launchpad.core.User;
import com.artbeatte.launchpad.db.UserDAO;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author art.beatte
 * @version 7/16/15
 */
public class AddUserCommand extends ConfiguredCommand<LaunchpadConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddUserCommand.class);

    private final UserDAO userDAO;

    public AddUserCommand(UserDAO userDAO) {
        super("addUser", "Adds a User to the DB");
        this.userDAO = userDAO;
    }

    @Override
    public void configure(Subparser subparser) {
        super.configure(subparser);
        subparser.addArgument("-un", "--user-name")
                .required(true)
                .action(Arguments.append())
                .dest("user-name")
                .help("The userName");
        subparser.addArgument("-pwd", "--password")
                .required(true)
                .action(Arguments.append())
                .dest("password")
                .help("The password");
    }

    @Override
    protected void run(Bootstrap<LaunchpadConfiguration> bootstrap, Namespace namespace,
                       LaunchpadConfiguration configuration) throws Exception {
        String un = namespace.getString("user-name");
        String pwd = namespace.getString("password");

        User user = userDAO.create(new User(un, pwd));

        LOGGER.info("User ( " + user.getName() + ", " + user.getPassword() +") added to the DB.");
    }
}
