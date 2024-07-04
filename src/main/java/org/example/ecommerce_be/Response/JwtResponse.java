package org.example.ecommerce_be.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ecommerce_be.Entity.User;
import org.example.ecommerce_be.Entity.UserRole;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private UserResponse user;

}
