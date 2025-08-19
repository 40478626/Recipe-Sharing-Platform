package com.example.recipe_sharing_platform.Controller;

import com.example.recipe_sharing_platform.Entity.Image;
import com.example.recipe_sharing_platform.Request.ImageRequest;
import com.example.recipe_sharing_platform.Response.Basic;
import com.example.recipe_sharing_platform.Response.ResponseFactory;
import com.example.recipe_sharing_platform.Service.ImageService;
import com.example.recipe_sharing_platform.Utils.Translator;
import com.example.recipe_sharing_platform.constant.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ResponseFactory responseFactory;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(
            @RequestPart("metadata") ImageRequest imageRequest,
            @RequestPart("file") MultipartFile image) {
        try {
            Image savedImage = imageService.saveImage(imageRequest, image);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedImage);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }

    @PostMapping("/get-images/{imageId}")
    public ResponseEntity<?> getImage(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImage(imageId); // Fetch image data from DB
            File imageFile = new File(image.getImageUrl()); // Get the actual file from local storage

            if (!imageFile.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Convert the file to a Resource
            Path path = Paths.get(imageFile.getAbsolutePath());
            Resource resource = new UrlResource(path.toUri());

            // Return the image with proper content type
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Set content type to image/jpeg (or use image/png if applicable)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/get-all-images")
    public ResponseEntity<?> getAllImages() {
        try {
            return ResponseEntity.ok(imageService.getAllImages());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch images.");
        }
    }

    @PostMapping("/delete-image/{imageId}")
    public ResponseEntity<Basic> deleteImage(@PathVariable Long imageId) {
       try {
              imageService.deleteImage(imageId);
           return responseFactory.buildSuccess(HttpStatus.OK, "Deleted!", ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
       } catch (Exception e) {
           return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
       }
    }

}
