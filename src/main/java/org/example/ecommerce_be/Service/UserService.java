package org.example.ecommerce_be.Service;

import org.example.ecommerce_be.Entity.Role;
import org.example.ecommerce_be.Entity.User;
import org.example.ecommerce_be.Enum.ERole;
import org.example.ecommerce_be.Repository.UserRepository;
import org.example.ecommerce_be.Response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> getAllUser(int page , int size){
        Pageable pageable = PageRequest.of(page, size);
       Optional<Page<User>> users = userRepository.findAllByRole(ERole.USER.name(), pageable);
       List<String> roles = new LinkedList<>();
       roles.add(ERole.USER.name());
       if(users.isPresent()){
           List<UserResponse> userDTOs =  users.get().stream().map(users1 -> new UserResponse(users1.getId(),users1.getUserName(), users1.getEmail(), roles)).collect(Collectors.toList());

           return ResponseEntity.ok(userDTOs);
       }
        return ResponseEntity.status(404).body("No users found");
    }

    public ResponseEntity<?> deleteUserById(String id){
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
