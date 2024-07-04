package org.example.ecommerce_be.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_price")
    private double totalPrice;


    @Column(name = "order_id") // many to one
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;
}
