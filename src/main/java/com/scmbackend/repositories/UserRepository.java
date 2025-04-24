package com.scmbackend.repositories;

import com.scmbackend.entities.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

@Repository
// @RepositoryRestResource //(without excerptProjection) it use to get custom data columns when we hit /users/user-projection and get all data columns when hit /users
// @RepositoryRestResource(excerptProjection = UserProjection.class) // by this we get projection based output by defualt in /users url (projection use to get custom data they reqcuert)
public interface UserRepository extends JpaRepository<User,String> {
    @RestResource(path = "by-email", rel = "by-email")
    List<User> findByEmailContainingIgnoreCase(@Param("email") String email);

    Optional<User> findByEmail(String email);
}
