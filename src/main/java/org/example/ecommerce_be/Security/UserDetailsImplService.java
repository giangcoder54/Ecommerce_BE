package org.example.ecommerce_be.Security;


import org.example.ecommerce_be.Entity.Role;
import org.example.ecommerce_be.Entity.User;
import org.example.ecommerce_be.Entity.UserRole;
import org.example.ecommerce_be.Repository.RoleRepository;
import org.example.ecommerce_be.Repository.UserRepository;
import org.example.ecommerce_be.Repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsImplService implements UserDetailsService {



    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired()
    private UserRoleRepository userRoleRepository;

    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());

            List<Role> roles = userRoles.stream()
                    .map(userRole -> roleRepository.findById(userRole.getRoleId()).orElseThrow(() -> new RuntimeException("Role not found")))
                    .collect(Collectors.toList());

            return new UserDetailsImpl(user, roles);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}