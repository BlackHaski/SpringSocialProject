package base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.facebook.security.FacebookAuthenticationService;
import org.springframework.social.security.*;

import javax.sql.DataSource;

@Configuration
@EnableSocial
@PropertySource("classpath:config.properties")
public class SocialConfig implements SocialConfigurer {
    @Autowired
    Environment env;
    @Autowired
    DataSource dataSource;
    @Autowired
    ConnectionSignUp signUp;
    @Autowired
    SocialUserDetailsService socialUserDetailsService;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfconfigurer, Environment env) {
        cfconfigurer.addConnectionFactory(new FacebookConnectionFactory(
                env.getProperty("facebook.app.id"),
                env.getProperty("facebook.app.secret")));
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator cfl) {
        JdbcUsersConnectionRepository repository =
                new JdbcUsersConnectionRepository(dataSource, cfl, Encryptors.noOpText());
        repository.setConnectionSignUp(signUp);
        return repository;
    }

    @Bean
    ConnectionFactoryLocator factoryLocator() {
        SocialAuthenticationServiceRegistry factoryLocator = new SocialAuthenticationServiceRegistry();
        factoryLocator.addAuthenticationService(new FacebookAuthenticationService(
                env.getProperty("facebook.app.id"),
                env.getProperty("facebook.app.secret"),
                env.getProperty("facebook.app.namespace")
        ));
        return factoryLocator;
    }

    @Bean
    public SocialAuthenticationProvider socialAuthenticationProvider() {
        SocialAuthenticationProvider provider = new SocialAuthenticationProvider(
                getUsersConnectionRepository(factoryLocator()),
                socialUserDetailsService
        );
        return provider;
    }

}
