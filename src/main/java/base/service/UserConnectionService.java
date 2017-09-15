package base.service;

import base.entity.UserConnection;

public interface UserConnectionService {
    int countByProviderUserId(String providerUserId);

    void save(UserConnection userConnection);

    UserConnection findByProviderUserId(String id);

}
