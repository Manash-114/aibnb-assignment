package com.airbnb.services.impl;


import com.airbnb.dto.SignUp;
import com.airbnb.entities.User;
import com.airbnb.repository.UserRepository;
import com.airbnb.services.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public User signUp(SignUp signUpRequest) {
        User user = null;
        user = userRepository.findByUserName(signUpRequest.getUsername());
        if(user != null)
            return null;

        user = userRepository.findByEmail(signUpRequest.getEmail());
        if(user !=null)
            return  null;

        User saveUser = User.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .fullName(signUpRequest.getFullName())
                .userName(signUpRequest.getUsername())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .build();
        return userRepository.save(saveUser);
    }
}
