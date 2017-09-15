package base.dao;

import base.entity.User;
import base.entity.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetails;

public interface UserDao extends JpaRepository<User,Integer>{
    @Query("select u from User u where u.username=:username")
    UserDetails loadUserByUsername(@Param("username") String username);

    @Query("select u from User u where u.userId=:userId")
    SocialUserDetails loadUserByUserId(@Param("userId") String userId);

    User findByUserConnectionIn(UserConnection userConnection);
}
