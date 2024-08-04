package com.airbnb.controller;

import com.airbnb.configuration.JwtHelper;
import com.airbnb.dto.Login;
import com.airbnb.dto.SignUp;
import com.airbnb.entities.User;
import com.airbnb.exception.LoginException;
import com.airbnb.services.AuthService;
import com.airbnb.services.CustomUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;
    private JwtHelper jwtHelper;
    private CustomUserService customUserService;
    private PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService, JwtHelper jwtHelper, CustomUserService customUserService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.jwtHelper = jwtHelper;
        this.customUserService = customUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signUpHandler(@Valid @RequestBody SignUp signUpRequest,
                                                             BindingResult bindingResult){
        Map<String,Object> res =new HashMap<>();

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors: ");
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("; "));
            res.put("message", errorMessage.toString());
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        User user = authService.signUp(signUpRequest);
        if(user == null){
            res.put("message","user already exists with given username or email");
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        res.put("message","signup successful");
        return new ResponseEntity<>(res,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> singInhanlder(@RequestBody Login loginRequest){
        String userName = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        Authentication authentication = authenticate(userName,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtHelper.generateToken(authentication);
        Map<String,String> res = new HashMap<>();
        res.put("token",token);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    public Authentication authenticate(String username,String password){
        UserDetails userDetails = customUserService.loadUserByUsername(username);
        if(userDetails == null){
            throw new LoginException("user not found with username = "+username);
        } else if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new LoginException("invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
