package com.airbnb.controller;

import com.airbnb.configuration.JwtHelper;
import com.airbnb.entities.Property;
import com.airbnb.entities.User;
import com.airbnb.repository.UserRepository;
import com.airbnb.services.PropertiesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private PropertiesService propertiesService;

    public UserController(PropertiesService propertiesService, UserRepository userRepository, JwtHelper jwtHelper) {
        this.propertiesService = propertiesService;
    }

    @GetMapping("/{userId}/properties")
    public ResponseEntity<?> getAllProperties(@PathVariable Long userId){
        List<Property> allPropertiesByUser = propertiesService.getAllPropertiesByUser(userId);
        return new ResponseEntity<>(allPropertiesByUser, HttpStatus.OK);
    }
}
