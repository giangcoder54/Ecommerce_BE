package org.example.ecommerce_be.Entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cart_id") // many to one
    private int cartId;
    @Column(name = "product_id")
    private int productId;
    @Column(name = "quantity")
    private int quantity;
}
