package org.example.ecommerce_be.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.example.ecommerce_be.Enum.EGender;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class User implements Serializable {

    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private String id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    //----------------------------------------------------------------------
    @Column(name = "USER_NAME", nullable = false, length = 100,unique = true)
    private String userName;

    @Column(name = "FULL_NAME", nullable = false, length = 100)
    private String fullName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "PASSWORD", length = 100)
    private String password;

    @Column(name = "MOBILE", nullable = false, length = 20)
    private String mobile;

    @Column(name = "EMAIL", nullable = false, length = 100)
    private String email;
    @Column(name = "ADDRESS", nullable = false, length = 100)
    private String address;

    @Lob
    //@Column(name = "PHOTO", nullable = false)
    @Column(name = "PHOTO")
    private String photo;

    @Column(name = "BIRTH_DATE")

    private String birthDate;

    @Column(name = "GENDER")
    private String gender;


    @Column(name = "STATUS", nullable = false)
    private String status;

    public User(String userName, String fullName, String firstName, String password, String mobile, String email, String address, String birthDate, EGender gender) {
        this.userName = userName;
        this.firstName = firstName;
        this.fullName = fullName;
        this.password = password;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.photo = null;
        this.birthDate = birthDate;
        this.gender = gender.name();
        this.status = "ACTIVE";
        this.id = UUID.randomUUID().toString();

    }


}