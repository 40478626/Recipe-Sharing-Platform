package com.example.recipe_sharing_platform.Request;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private String role;
}
