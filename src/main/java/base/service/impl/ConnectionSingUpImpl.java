package base.service.impl;

import base.entity.Role;
import base.entity.User;
import base.entity.UserConnection;
import base.service.UserConnectionService;
import base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;

@Service
public class ConnectionSingUpImpl implements ConnectionSignUp {
    @Autowired
    UserService userService;
    @Autowired
    UserConnectionService userConnectionService;

    @Override
    public String execute(Connection<?> connection) {
        UserConnection connectionData = new UserConnection(connection.createData());

        UserConnection userConnectionData =
                userConnectionService.findByProviderUserId(connectionData.getProviderUserId());

        if (userConnectionData == null) {
            Facebook facebook = (Facebook) connection.getApi();
            org.springframework.social.facebook.api.User userDto =
                    facebook.fetchObject("me",
                            org.springframework.social.facebook.api.User.class,
                            "id", "name", "email");
            User user = new User(userDto.getId(), userDto.getName(),
                    null,
                    userDto.getEmail(),
                    Role.ROLE_USER, connectionData);
            connectionData.setUser(user);
            userService.save(user);
            userConnectionService.save(connectionData);
            return user.getUserId();
        }else {
            return userService.findByUserConnectionIn(userConnectionData).getUserId();
        }
    }
}
