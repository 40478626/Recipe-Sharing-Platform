package com.example.recipe_sharing_platform.Service;

import com.example.recipe_sharing_platform.Entity.Image;
import com.example.recipe_sharing_platform.Request.ImageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    Image saveImage(ImageRequest imageRequest, MultipartFile file) throws IOException;
    Image getImage(Long imageId) throws IOException;
    List<Image> getAllImages() throws IOException;
    ResponseEntity<?> deleteImage(Long imageId);
}
