package br.com.davi.spring_boot_first.repository;

import br.com.davi.spring_boot_first.entity.CartItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CartRepository extends JpaRepository<CartItemsEntity, Long> {

    Optional<CartItemsEntity> findByOrderIdAndProductId(Long orderId, Long productId);

    List<CartItemsEntity> findByOrderId(Long orderId);

    void deleteByOrderId(Long orderId);

    boolean existsByOrderId(Long orderId);

}