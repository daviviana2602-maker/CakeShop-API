package br.com.davi.spring_boot_first.entity;

import br.com.davi.spring_boot_first.enums.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "orders")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class OrderEntity {

    @OneToMany(mappedBy = "order")
    private List<CartItemsEntity> cartItems;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    private BigDecimal price  = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatusEnum status;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

}