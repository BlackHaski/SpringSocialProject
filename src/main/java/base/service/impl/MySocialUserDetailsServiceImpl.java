package base.service.impl;

import base.repository.SocialUserDetailsRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class MySocialUserDetailsServiceImpl implements SocialUserDetailsService {
    SocialUserDetailsRepository userDetailsRepository;
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        return userDetailsRepository.loadUserByUserId(userId);
    }
}
