package com.airbnb.controller;


import com.airbnb.configuration.JwtHelper;
import com.airbnb.dto.PropertiesDto;
import com.airbnb.services.PropertiesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/properties")
public class PropertiesController {
    private PropertiesService propertiesService;
    private JwtHelper jwtHelper;

    public PropertiesController(PropertiesService propertiesService, JwtHelper jwtHelper) {
        this.propertiesService = propertiesService;
        this.jwtHelper = jwtHelper;
    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestHeader("Authorization") String token,@Valid @RequestBody PropertiesDto
            properties, BindingResult bindingResult
    ) throws Exception {
        Map<String,Object> res =new HashMap<>();
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder("Validation errors: ");
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("; "));
            res.put("message", errorMessage.toString());
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
        String userNameFromToken = jwtHelper.getUserNameFromToken(token);
        propertiesService.create(properties,userNameFromToken);
        res.put("message","Property added successfully");
        return new ResponseEntity<>(res,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        propertiesService.getAllProperties();
        return new ResponseEntity<>(propertiesService.getAllProperties(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        return new ResponseEntity<>(propertiesService.getPropertyById(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String token ,@PathVariable Long id,@Valid @RequestBody PropertiesDto propertiesDto,BindingResult bindingResult) throws Exception {
        Map<String,Object> res =new HashMap<>();
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder("Validation errors: ");
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("; "));
            res.put("message", errorMessage.toString());
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        String userNameFromToken = jwtHelper.getUserNameFromToken(token);
        propertiesService.update(propertiesDto,id,userNameFromToken);
        res.put("message","Property updated successfully");
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestHeader("Authorization") String token , @PathVariable Long id) throws Exception {

        String userNameFromToken = jwtHelper.getUserNameFromToken(token);
        propertiesService.delete(id,userNameFromToken);
        Map<String,Object> res =new HashMap<>();
        res.put("message","Property deleted successfully");
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
