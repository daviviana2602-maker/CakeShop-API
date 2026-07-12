package br.com.davi.spring_boot_first.repository;

import br.com.davi.spring_boot_first.entity.OrderEntity;
import br.com.davi.spring_boot_first.enums.OrderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    boolean existsByUserIdAndStatus(Long userId, OrderStatusEnum status);

}