package com.airbnb.services;

import com.airbnb.entities.User;
import com.airbnb.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CustomUserService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);
        if(user == null){
            return null;
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(), Collections.emptyList());
    }
}
