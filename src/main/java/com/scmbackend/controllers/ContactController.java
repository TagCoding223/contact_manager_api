package com.scmbackend.controllers;

import java.io.IOException;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.scmbackend.dto.ImageInfo;
import com.scmbackend.entities.Contact;
import com.scmbackend.exceptions.ResourceNotFoundException;
import com.scmbackend.repositories.ContactRepository;
import com.scmbackend.services.ImageUploadService;

@RepositoryRestController
public class ContactController {

    private ImageUploadService imageUploadService;
    
    private ContactRepository contactRepository;

    public ContactController(ImageUploadService imageUploadService, ContactRepository contactRepository) {
        this.imageUploadService = imageUploadService;
        this.contactRepository = contactRepository;
    }



    @PostMapping("/contacts/{contactId}/upload")
    public ResponseEntity <ImageInfo> uploadImage (
    @PathVariable String contactId,    
    @RequestParam("contactImage") MultipartFile file) throws IOException{

        // file validation come here

        Contact contact = contactRepository.findById(contactId).orElseThrow(() -> new ResourceNotFoundException("Contact not found."));
        ImageInfo upload = this.imageUploadService.upload(file);

        contact.setCloudinaryImagePublicId(upload.publicId());
        contact.setPictureUrl(upload.secureUrl());

        contactRepository.save(contact);

        return ResponseEntity.ok(upload);
    }
}
