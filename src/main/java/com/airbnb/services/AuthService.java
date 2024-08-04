package com.airbnb.services;

import com.airbnb.dto.SignUp;
import com.airbnb.entities.User;

public interface AuthService {
    User signUp(SignUp signUpRequest);
}
