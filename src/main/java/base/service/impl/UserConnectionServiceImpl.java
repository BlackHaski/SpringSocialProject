package base.service.impl;

import base.dao.UserConnectionDao;
import base.entity.UserConnection;
import base.service.UserConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserConnectionServiceImpl implements UserConnectionService {
    @Autowired
    UserConnectionDao userConnectionDao;
    @Override
    public int countByProviderUserId(String providerUserId) {
        return userConnectionDao.countByProviderUserId(providerUserId);
    }

    @Override
    public void save(UserConnection userConnection) {
        userConnectionDao.save(userConnection);
    }

    @Override
    public UserConnection findByProviderUserId(String id) {
        return userConnectionDao.findByProviderUserId(id);
    }
}
