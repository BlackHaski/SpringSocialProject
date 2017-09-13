package base.repository;

import base.entiity.MySocialUserDetails;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.stereotype.Repository;

@Repository
public class SocialUserDetailsRepository {
    @Autowired
    Datastore datastore;

    public SocialUserDetails loadUserByUserId(String userId) {
        return (SocialUserDetails) datastore
                .find(MySocialUserDetails.class)
                .filter("userId", userId)
                .asList();
    }
}
