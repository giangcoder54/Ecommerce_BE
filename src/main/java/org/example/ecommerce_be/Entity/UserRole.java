package org.example.ecommerce_be.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_role")
public class UserRole  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_id")
    private  Long roleId;

    @Column(name = "user_id")
    private  String userId;

    public UserRole( Long roleId, String userId) {
        this.roleId = roleId;
        this.userId = userId;
    }
}
