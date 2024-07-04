package org.example.ecommerce_be.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private List<String> roles;
}
