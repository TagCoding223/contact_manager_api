package com.scmbackend.controllers;

import java.io.IOException;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.scmbackend.dto.ImageInfo;
import com.scmbackend.entities.User;
import com.scmbackend.exceptions.ResourceNotFoundException;
import com.scmbackend.repositories.UserRepository;
import com.scmbackend.services.ImageUploadService;

@RepositoryRestController
// @CrossOrigin // it allow to access this resource from other port and url
public class UserController {

    private ImageUploadService imageUploadService;

    private UserRepository userRepository;

    public UserController(ImageUploadService imageUploadService, UserRepository userRepository) {
        this.imageUploadService = imageUploadService;
        this.userRepository = userRepository;
    }

    @PostMapping("/users/{userId}/upload")
    @CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<ImageInfo> uploadImage(
            @PathVariable String userId,
            @RequestParam("userImage") MultipartFile file) throws IOException {

        // Validate the file
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // Find the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        ImageInfo upload = null;
        try {
            upload = this.imageUploadService.upload(file);
            // Update user or contact with the uploaded image info
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image: " + e.getMessage());
        }

        // Update the user's profile picture
        user.setCloudinaryImagePublicId(upload.publicId());
        user.setProfilePicture(upload.secureUrl());
        userRepository.save(user);

        return ResponseEntity.ok(upload);
    }
}
