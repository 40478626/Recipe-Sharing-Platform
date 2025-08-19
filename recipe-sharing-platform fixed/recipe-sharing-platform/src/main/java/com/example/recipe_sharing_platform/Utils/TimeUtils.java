package com.example.recipe_sharing_platform.Utils;

import java.sql.Timestamp;

public class TimeUtils {
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}