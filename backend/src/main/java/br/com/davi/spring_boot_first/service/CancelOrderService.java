package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.entity.OrderEntity;
import br.com.davi.spring_boot_first.enums.OrderStatusEnum;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.CartRepository;
import br.com.davi.spring_boot_first.repository.OrderRepository;
import br.com.davi.spring_boot_first.security.AuthenticatedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CancelOrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final AuthenticatedService authenticatedService;


    public CancelOrderService(OrderRepository orderRepository,
                              CartRepository cartRepository,
                              AuthenticatedService authenticatedService) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.authenticatedService = authenticatedService;
    }


    private OrderEntity findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("order not found"));
    }


    @Transactional
    public Long cancelOrder(Long orderId) {

        OrderEntity order = findOrderById(orderId);

        authenticatedService.checkOwnership(order.getUser().getId());


        order.setStatus(OrderStatusEnum.CANCELED);


        cartRepository.deleteAll(order.getCartItems());


       return orderId;

    }

}
