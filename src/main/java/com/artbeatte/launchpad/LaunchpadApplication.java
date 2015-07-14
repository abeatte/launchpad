package com.artbeatte.launchpad;

import com.artbeatte.launchpad.authentication.ExampleAuthenticator;
import com.artbeatte.launchpad.core.User;
import com.artbeatte.launchpad.health.TemplateHealthCheck;
import com.artbeatte.launchpad.resources.HelloWorldResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.ChainedAuthFactory;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.auth.oauth.OAuthFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * @author art.beatte
 * @version 7/14/15
 */
public class LaunchpadApplication extends Application<HelloWorldConfiguration> {
    public static void main(String[] args) throws Exception {
        new LaunchpadApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(HelloWorldConfiguration configuration,
                    Environment environment) {
        ChainedAuthFactory<User> chainedFactory =
                new ChainedAuthFactory<>(
                        new BasicAuthFactory<>(new ExampleAuthenticator(), "SUPER SECRET STUFF", User.class));//,
//                        new OAuthFactory<>(new ExampleOAuthAuthenticator(), "SUPER SECRET STUFF", User.class));
        environment.jersey().register(AuthFactory.binder(chainedFactory));



        final HelloWorldResource resource = new HelloWorldResource(configuration.getTemplate(), configuration.getDefaultName());
        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
    }
}
