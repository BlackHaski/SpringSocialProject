package base.repository;

import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @Autowired
    Datastore datastore;

    public UserDetails loadUserByUsername(String username){
        return (UserDetails) datastore.find(User.class).filter("username",username).asList();
    }
}
