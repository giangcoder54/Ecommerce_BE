package org.example.ecommerce_be.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class LoginRequest {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
