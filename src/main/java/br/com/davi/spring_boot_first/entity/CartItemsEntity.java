package br.com.davi.spring_boot_first.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "cart_items")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class CartItemsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice  = BigDecimal.ZERO;

    @Column(name = "full_price", nullable = false)
    private BigDecimal fullPrice  = BigDecimal.ZERO;
}