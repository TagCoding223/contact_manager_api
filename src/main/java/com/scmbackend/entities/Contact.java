package com.scmbackend.entities;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    private String id;

    @NotBlank(message = "Name is required!") // this is handle by global exception class inside exception package
    @Length(min = 3, max = 30, message = "Name must be 3 characters and not exceed 30 characters.")
    private String name;

    @Column(unique = true, nullable = false)
    @Email(message = "Email must be a valid email address.")
    private String email;
    
    @Size(max = 500, message = "Address must not exceed 2000 charaters.")
    private String address;

    @Column(nullable = false)
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be valid.")
    private String phoneNumber;

    @Pattern(regexp = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w.-]*)*/?$", message = "PictureUrl link must be valid.")
    private String pictureUrl;

    @Lob
    @NotBlank(message = "Description is required.")
    private String description;

    @Pattern(regexp = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w.-]*)*/?$", message = "Facebook link must be valid.")
    private String facebookLink;

    @Pattern(regexp = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w.-]*)*/?$", message = "Linkedin link must be valid.")
    private String linkedinLink;
    private boolean favourite = false;

    private String cloudinaryImagePublicId;

    @ManyToOne
    private User user;
}
