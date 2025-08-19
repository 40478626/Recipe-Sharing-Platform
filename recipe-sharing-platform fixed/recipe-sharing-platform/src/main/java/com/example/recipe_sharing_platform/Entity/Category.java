package com.example.recipe_sharing_platform.Entity;

import com.example.recipe_sharing_platform.Utils.TimeUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name="CATEGORY")
public class Category {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CREATED_AT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Rangoon")
    private Timestamp createdAt = TimeUtils.getCurrentTimestamp();

    @Column(name = "UPDATED_AT")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Rangoon")
    private Timestamp updatedAt = TimeUtils.getCurrentTimestamp();
}
