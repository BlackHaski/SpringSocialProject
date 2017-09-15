package base.service;

import base.entity.User;
import base.entity.UserConnection;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetails;

public interface UserService {
    UserDetails loadUserByUsername(String username);

    SocialUserDetails loadUserByUserId(String userId);

    void save(User user);

    User findByUserConnectionIn(UserConnection userConnection);

}
