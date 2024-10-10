package com.example.springsecurity.LearnSpringSecurityByDoing.config;

import com.example.springsecurity.LearnSpringSecurityByDoing.Repository.UserInfoRepository;
import com.example.springsecurity.LearnSpringSecurityByDoing.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    UserInfoRepository userInfoRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

         Optional< UserInfo > userInfo = userInfoRepository.findByName(username);
       return userInfo.map(UserDetailsUserInfo::new).orElseThrow(()-> new UsernameNotFoundException("user not found"));
    }
}
