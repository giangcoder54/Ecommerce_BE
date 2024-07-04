package org.example.ecommerce_be.Repository;

import org.example.ecommerce_be.Entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("select ur from UserRole ur where ur.userId = :userId ")
    List<UserRole> findByUserId(@Param("userId") String userId);




}
