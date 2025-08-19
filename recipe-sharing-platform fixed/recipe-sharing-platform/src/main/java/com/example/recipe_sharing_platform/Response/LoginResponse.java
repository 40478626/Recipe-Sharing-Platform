package com.example.recipe_sharing_platform.Response;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private Long expiresIn;
    private String userId;
}
