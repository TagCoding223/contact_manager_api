package com.scmbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scmbackend.entities.Role;

@Repository
public interface RoleRepositry extends JpaRepository<Role,Long>{

}
