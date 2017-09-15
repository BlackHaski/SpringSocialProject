package base.service.impl;

import base.dao.UserDao;
import base.entity.User;
import base.entity.UserConnection;
import base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, SocialUserDetailsService, UserService {
    @Autowired
    UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.loadUserByUsername(username);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        return userDao.loadUserByUserId(userId);
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public User findByUserConnectionIn(UserConnection userConnection) {
        return userDao.findByUserConnectionIn(userConnection);
    }
}
