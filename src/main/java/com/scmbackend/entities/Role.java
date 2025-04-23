package com.scmbackend.entities;

import com.scmbackend.configuration.AppConstants;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    private Long id;

    private String role = AppConstants.ROLE_USER;

    
}
