package org.example.ecommerce_be.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import org.example.ecommerce_be.Enum.ERole;


@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "role")

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole name;
}
