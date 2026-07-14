package br.com.davi.spring_boot_first.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "products")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class ProductEntity {

    @OneToMany(mappedBy = "product")
    private List<CartItemsEntity> cartItems;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

}