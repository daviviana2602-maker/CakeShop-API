package br.com.davi.spring_boot_first.repository;

import br.com.davi.spring_boot_first.entity.OrderEntity;
import br.com.davi.spring_boot_first.enums.OrderStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByUserIdAndStatus(Long userId, OrderStatusEnum status);

    Page<OrderEntity> findByStatus(OrderStatusEnum status, Pageable pageable);

}