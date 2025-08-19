package com.example.recipe_sharing_platform.Controller;

import com.example.recipe_sharing_platform.Entity.User;
import com.example.recipe_sharing_platform.Request.LoginRequest;
import com.example.recipe_sharing_platform.Request.LogoutRequest;
import com.example.recipe_sharing_platform.Request.UserRequest;
import com.example.recipe_sharing_platform.Response.Basic;
import com.example.recipe_sharing_platform.Response.LoginResponse;
import com.example.recipe_sharing_platform.Response.ResponseFactory;
import com.example.recipe_sharing_platform.Service.UserService;
import com.example.recipe_sharing_platform.Utils.Translator;
import com.example.recipe_sharing_platform.constant.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private ResponseFactory responseFactory;

    @Autowired
    private UserService userService;

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.grant-type:client_credentials}")
    private String grantType;

    @PostMapping("/register-user")
    ResponseEntity<Basic> registerUser(@RequestBody UserRequest userRequest) {
        try {
            // Implement logic to register a new user
            User user = userService.registerUser(userRequest);
            return responseFactory.buildSuccess(HttpStatus.OK, user, ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
        } catch (Exception e) {
            log.error("create user error", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Implement logic to authenticate a user
            LoginResponse response = userService.loginUser(loginRequest);
            return responseFactory.buildSuccess(HttpStatus.OK, response, ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
        } catch (Exception e) {
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest logoutRequest) {
        try {
            // Implement logic to log out a user
            ResponseEntity<?> response = userService.logoutUser(logoutRequest);
            return responseFactory.buildSuccess(HttpStatus.OK, "Logged out successfully", ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
        } catch (Exception e) {
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }

}
