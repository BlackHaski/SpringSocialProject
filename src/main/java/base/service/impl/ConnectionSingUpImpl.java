package base.service.impl;

import base.entity.Role;
import base.entity.User;
import base.entity.UserConnection;
import base.service.UserConnectionService;
import base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class ConnectionSingUpImpl implements ConnectionSignUp {
    @Autowired
    UserService userService;
    @Autowired
    UserConnectionService userConnectionService;

    @Override
    public String execute(Connection<?> connection) {
        String userId = "";

        UserConnection connectionData = new UserConnection(connection.createData());

        UserConnection userConnectionData =
                userConnectionService.findByProviderUserId(connectionData.getProviderUserId());
        User user;
        if (userConnectionData == null) {
            Facebook facebook = (Facebook) connection.getApi();
            org.springframework.social.facebook.api.User userDto =
                    facebook.fetchObject("me",
                            org.springframework.social.facebook.api.User.class,
                            "id", "name", "email");
            user = new User(userDto.getId(), userDto.getName(),
                    null,
                    userDto.getEmail(),
                    Role.ROLE_USER, connectionData);
            connectionData.setUser(user);
            userService.save(user);
            userConnectionService.save(connectionData);
            userId = user.getUserId();
        } else {
            user = userService.findByUserConnectionIn(userConnectionData);
        }
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return userId;
    }
}
