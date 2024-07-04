package org.example.ecommerce_be.Repository;

import org.example.ecommerce_be.Entity.Role;
import org.example.ecommerce_be.Enum.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Long> {
    Optional<Role> findById(Long id);
    Role findByName(ERole name);
}
