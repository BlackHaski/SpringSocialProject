package base.dao;

import base.entity.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserConnectionDao extends JpaRepository<UserConnection,Integer>{
    int countByProviderUserId(String providerUserId);

    UserConnection findByProviderUserId(String id);
}
