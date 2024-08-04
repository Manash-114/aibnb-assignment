package com.airbnb.controller;

import com.airbnb.configuration.JwtHelper;
import com.airbnb.entities.Property;
import com.airbnb.entities.User;
import com.airbnb.repository.UserRepository;
import com.airbnb.services.PropertiesService;
import io.jsonwebtoken.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private PropertiesService propertiesService;
    private JwtHelper jwtHelper;

    public UserController(PropertiesService propertiesService, JwtHelper jwtHelper) {
        this.propertiesService = propertiesService;
        this.jwtHelper = jwtHelper;
    }

    @GetMapping("/{userId}/properties")
    public ResponseEntity<?> getAllProperties(@RequestHeader("Authorization")String token ,@PathVariable Long userId) throws Exception {
        String userNameFromToken = jwtHelper.getUserNameFromToken(token);
        List<Property> allPropertiesByUser = propertiesService.getAllPropertiesByUser(userId,userNameFromToken);
        return new ResponseEntity<>(allPropertiesByUser, HttpStatus.OK);
    }
}
