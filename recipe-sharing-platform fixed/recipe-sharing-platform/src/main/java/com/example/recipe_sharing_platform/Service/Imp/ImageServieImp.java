package com.example.recipe_sharing_platform.Service.Imp;

import com.example.recipe_sharing_platform.Entity.Image;
import com.example.recipe_sharing_platform.Repository.ImageRepo;
import com.example.recipe_sharing_platform.Request.ImageRequest;
import com.example.recipe_sharing_platform.Service.ImageService;
import com.example.recipe_sharing_platform.Utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServieImp implements ImageService {

    @Autowired
    private ImageRepo imageRepo;

    private static final String IMAGE_UPLOAD_DIR = "D:/Amara/Freelance Project/Project 4 Recepie Sharing Platform/Codings/recipe-sharing-platform (6)/recipe-sharing-platform/uploads/images/";

    public Image saveImage(ImageRequest imageRequest, MultipartFile image) throws IOException {
        // Save the file to local storage
        String uploadDir = IMAGE_UPLOAD_DIR;
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        String filePath = uploadDir + image.getOriginalFilename();
        File file = new File(filePath);
        image.transferTo(file);

        // Save metadata in the database
        Image imageEntity = new Image();
        imageEntity.setRecipeId(imageRequest.getRecipeId());
        imageEntity.setFileName(image.getOriginalFilename());
        imageEntity.setImageUrl(filePath);
        imageEntity.setCreatedAt(TimeUtils.getCurrentTimestamp());
        imageEntity.setUpdatedAt(TimeUtils.getCurrentTimestamp());

        return imageRepo.save(imageEntity);
    }

    @Override
    public Image getImage(Long imageId) throws IOException {
        Image image = imageRepo.findById(imageId).orElse(null);;
        if (image!= null) {
            return image;
        } else {
            throw new RuntimeException("Image not found");
        }
    }

    @Override
    public List<Image> getAllImages() throws IOException {
        List<Image> images = imageRepo.findAll();
        if (images.isEmpty()) {
            throw new RuntimeException("No images found");
        }
        return images;
    }

    @Override
    public ResponseEntity<?> deleteImage(Long imageId) {
        try {
            Image image = getImage(imageId); // Fetch the image from the DB

            // Delete the image file from local storage
            File imageFile = new File(image.getImageUrl());
            if (imageFile.exists()) {
                boolean isDeleted = imageFile.delete(); // Delete the file
                if (!isDeleted) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            }

            // Delete the image record from the database
            imageRepo.deleteById(imageId);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Successfully deleted
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Image not found
        }
    }
}
