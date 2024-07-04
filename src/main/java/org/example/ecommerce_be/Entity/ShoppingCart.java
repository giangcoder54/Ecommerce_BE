package org.example.ecommerce_be.Entity;


import lombok.*;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;



}
