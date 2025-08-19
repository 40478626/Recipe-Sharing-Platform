package com.example.recipe_sharing_platform.Entity;

import com.example.recipe_sharing_platform.Utils.TimeUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name="IMAGE")
public class Image {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "RECIPE_ID")
    private String recipeId;

    @Column(name = "FILE_NAME")
    private String fileName;
    
    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "CREATED_AT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Rangoon")
    private Timestamp createdAt = TimeUtils.getCurrentTimestamp();

    @Column(name = "UPDATED_AT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Rangoon")
    private Timestamp updatedAt = TimeUtils.getCurrentTimestamp();
}
