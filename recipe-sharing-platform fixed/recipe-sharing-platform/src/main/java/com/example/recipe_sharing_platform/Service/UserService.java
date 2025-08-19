package com.example.recipe_sharing_platform.Service;

import com.example.recipe_sharing_platform.Entity.User;
import com.example.recipe_sharing_platform.Request.LoginRequest;
import com.example.recipe_sharing_platform.Request.LogoutRequest;
import com.example.recipe_sharing_platform.Request.UserRequest;
import com.example.recipe_sharing_platform.Response.LoginResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User registerUser(UserRequest userRequest);

    LoginResponse loginUser(LoginRequest loginRequest);

    ResponseEntity<?> logoutUser(LogoutRequest logoutRequest);
}
