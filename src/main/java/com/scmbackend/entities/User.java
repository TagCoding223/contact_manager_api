package com.scmbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id;

    @Column(nullable = false)
    @NotBlank(message = "Name is required!") // this is handle by global exception class inside exception package
    @Length(min = 3, max = 30, message = "Name must be 3 characters and not exceed 30 characters.")
    private String name;

    @Column(unique = true, nullable = false)
    @Email(message = "Email must be a valid email address.")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Password is required.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;

//    @Column(columnDefinition = "text")
    @Lob
    @Size(max = 2000, message = "About must not exceed 2000 charaters.")
    private String about;

//    @Column(columnDefinition = "text")
    @Lob
    private String profilePicture;

    @Column(nullable = false)
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be valid.")
    private String phoneNumber;

    private boolean enabled = true;

    private boolean emailVerified = false;

    private boolean phoneVerified = false;

    @Enumerated(value = EnumType.STRING)
    private Providers provider = Providers.SELF;

    private String emailToken;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new LinkedHashSet<>(); // Use LinkedHashSet for consistency with JPA

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Contact> contacts = new LinkedHashSet<>();
}
