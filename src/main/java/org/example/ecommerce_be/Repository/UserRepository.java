package org.example.ecommerce_be.Repository;

import org.example.ecommerce_be.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {

    // dung Optional de tranh loi NullPointerException
    @Query("Select u from User  u where  u.userName = :username")
    Optional<User> findByUsername(@Param("username") String username);

    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);


    @Query(value = "select * from user u join user_role ur on u.id = ur.user_id " +
            "join role r on r.role_id = ur.role_id where r.name = :role ", nativeQuery = true)
    Optional<Page<User>> findAllByRole(String role , Pageable pageable);
}
