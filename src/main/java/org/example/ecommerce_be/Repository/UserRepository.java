package org.example.ecommerce_be.Repository;

import org.example.ecommerce_be.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {

    // dung Optional de tranh loi NullPointerException
    @Query("Select u from User  u where  u.userName = :username")
    Optional<User> findByUsername(@Param("username") String username);

    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
}
