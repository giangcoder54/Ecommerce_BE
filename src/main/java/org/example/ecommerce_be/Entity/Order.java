package org.example.ecommerce_be.Entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date")
    private String orderDate;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "delivery_date")
    private String deliveryDate;

    @Column(name = "shipping_fee")
    private double shippingFee;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "notes")
    private String notes;

    @Column(name = "user_id")
    private int user_id;



}

