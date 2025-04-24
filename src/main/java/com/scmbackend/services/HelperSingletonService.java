package com.scmbackend.services;

import org.springframework.beans.factory.annotation.Autowired;

public class HelperSingletonService {
    private static ImageUploadService imageUploadService;

    public static ImageUploadService getImageUploadService() {
        return imageUploadService;
    }

    @Autowired
    public static void setImageUploadService(ImageUploadService imageUploadService) {
        HelperSingletonService.imageUploadService = imageUploadService;
    }

    
}
