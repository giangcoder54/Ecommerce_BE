package org.example.ecommerce_be.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ecommerce_be.Enum.EGender;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String userName;

    private String fullName;


    private String firstName;


    private String password;

    private String mobile;

    private String email;
    private String address;


    private String birthDate;


    private EGender gender;




}

