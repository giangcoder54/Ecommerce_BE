package org.example.ecommerce_be.Controller.Admin;

import org.example.ecommerce_be.Enum.ERole;
import org.example.ecommerce_be.Repository.UserRepository;
import org.example.ecommerce_be.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class ManageUserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getAllUser/paged")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUser(@RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "10") int size){
            return userService.getAllUser(page, size);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id) {
       return userService.deleteUserById(id);
    }

}
