package com.example.recipe_sharing_platform.Request;

import lombok.Data;

@Data
public class LogoutRequest {
    private String refreshToken;
}
