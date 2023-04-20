package com.accenture.codingtest.springbootcodingtest.service;

import com.accenture.codingtest.springbootcodingtest.dto.UserToUserDetail;
import com.accenture.codingtest.springbootcodingtest.entity.User;
import com.accenture.codingtest.springbootcodingtest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        return user.map(UserToUserDetail::new).orElseThrow(() -> new UsernameNotFoundException("User name not found " + username));
    }
}
