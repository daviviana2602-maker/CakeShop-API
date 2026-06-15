package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.entity.CartEntity;
import br.com.davi.spring_boot_first.entity.OrderEntity;
import br.com.davi.spring_boot_first.enums.OrderStatusEnum;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.CartRepository;
import br.com.davi.spring_boot_first.repository.OrderRepository;
import br.com.davi.spring_boot_first.security.OwnershipService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CancelOrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OwnershipService ownershipService;


    public CancelOrderService(OrderRepository orderRepository,
                              CartRepository cartRepository,
                              OwnershipService ownershipService) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.ownershipService = ownershipService;
    }


    private OrderEntity findOrderId(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("order not found"));
    }


    @Transactional
    public Long cancelOrder(Long orderId) {

        OrderEntity order = findOrderId(orderId);

        ownershipService.checkOwnership(order.getUserId());


        order.setStatus(OrderStatusEnum.CANCELED);


        List<CartEntity> cart = cartRepository.findByOrderId(orderId);

        for (CartEntity item : cart) {
            cartRepository.delete(item);
        }

       return orderId;

    }

}
