package com.scmbackend.repositories;

import com.scmbackend.entities.User;
// import com.scmbackend.repositories.projections.UserProjection;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
// @RepositoryRestResource //(without excerptProjection) it use to get custom data columns when we hit /users/user-projection and get all data columns when hit /users
// @RepositoryRestResource(excerptProjection = UserProjection.class) // by this we get projection based output by defualt in /users url (projection use to get custom data they reqcuert)
public interface UserRepository extends JpaRepository<User,String> {
}
