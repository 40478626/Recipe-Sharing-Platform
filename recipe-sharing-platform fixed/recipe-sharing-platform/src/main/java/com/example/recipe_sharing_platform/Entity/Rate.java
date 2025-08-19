package com.example.recipe_sharing_platform.Entity;

import com.example.recipe_sharing_platform.Utils.TimeUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name="RATE")
public class Rate {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "RECIPE_ID")
    private Long recipeId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "RATE")
    private Integer rate;

    @Column(name = "CREATED_AT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Rangoon")
    private Timestamp createdAt = TimeUtils.getCurrentTimestamp();

    @Column(name = "UPDATED_AT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Rangoon")
    private Timestamp updatedAt = TimeUtils.getCurrentTimestamp();
}
