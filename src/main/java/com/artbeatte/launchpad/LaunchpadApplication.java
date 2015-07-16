package com.artbeatte.launchpad;

import com.artbeatte.launchpad.authentication.ExampleAuthenticator;
import com.artbeatte.launchpad.core.Person;
import com.artbeatte.launchpad.core.Template;
import com.artbeatte.launchpad.core.User;
import com.artbeatte.launchpad.db.PersonDAO;
import com.artbeatte.launchpad.health.TemplateHealthCheck;
import com.artbeatte.launchpad.resources.HelloWorldResource;
import com.artbeatte.launchpad.resources.PeopleResource;
import com.artbeatte.launchpad.resources.PersonResource;
import com.artbeatte.launchpad.resources.ProtectedResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * @author art.beatte
 * @version 7/14/15
 */
public class LaunchpadApplication extends Application<LaunchpadConfiguration> {
    public static void main(String[] args) throws Exception {
        new LaunchpadApplication().run(args);
    }

    private final HibernateBundle<LaunchpadConfiguration> hibernateBundle =
            new HibernateBundle<LaunchpadConfiguration>(Person.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(LaunchpadConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<LaunchpadConfiguration> bootstrap) {
        // Enable variable substitution with environment variables.
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)));

        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new MigrationsBundle<LaunchpadConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(LaunchpadConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(LaunchpadConfiguration configuration, Environment environment) {
        final PersonDAO dao = new PersonDAO(hibernateBundle.getSessionFactory());
        final Template template = configuration.buildTemplate();

        environment.healthChecks().register("template", new TemplateHealthCheck(template));

        environment.jersey().register(AuthFactory.binder(
                new BasicAuthFactory<>(new ExampleAuthenticator(), "SUPER SECRET STUFF", User.class)));
        environment.jersey().register(new HelloWorldResource(template));
        environment.jersey().register(new ProtectedResource());
        environment.jersey().register(new PeopleResource(dao));
        environment.jersey().register(new PersonResource(dao));
    }
}
