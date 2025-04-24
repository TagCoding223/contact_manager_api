package com.scmbackend.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scmbackend.configuration.AppConstants;
import com.scmbackend.dto.ImageInfo;

@Service
public class ImageUploadService {
    private Cloudinary cloudinary;

    public ImageUploadService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }
    
    public ImageInfo upload(MultipartFile file) throws IOException{
        Map resultMap = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder","scm"));
        ImageInfo imageInfo = new ImageInfo(resultMap.get("public_id").toString(), resultMap.get("secure_url").toString(), resultMap.get("format").toString());
        return imageInfo;
    }

    public String generateImageUrl(String publicId){
        return cloudinary.url().generate(publicId);
    }

    public String generateTransformImageUrl(String publicId){
        return cloudinary.url().transformation(
            new Transformation().width(AppConstants.CLOUDINARY_IMAGE_WIDTH).height(AppConstants.CLOUDINARY_IMAGE_HEIGHT).crop(AppConstants.CLOUDINARY_IMAGE_CROP)
        ).generate(publicId);
    }
}
