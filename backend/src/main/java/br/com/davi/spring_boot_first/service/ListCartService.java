package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.CartResponse;
import br.com.davi.spring_boot_first.entity.CartItemsEntity;
import br.com.davi.spring_boot_first.entity.OrderEntity;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.CartRepository;
import br.com.davi.spring_boot_first.repository.OrderRepository;
import br.com.davi.spring_boot_first.security.AuthenticatedService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ListCartService {

    private final CartRepository cartRepository;
    private final AuthenticatedService authenticatedService;
    private final OrderRepository orderRepository;


    public ListCartService(CartRepository cartRepository,
                           AuthenticatedService authenticatedService,
                           OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.authenticatedService = authenticatedService;
        this.orderRepository = orderRepository;
    }


    private OrderEntity findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }


    public List<CartResponse> listItems(Long orderId) {

        List<CartItemsEntity> cart = cartRepository.findByOrderId(orderId);

        OrderEntity order = findOrderById(orderId);

        authenticatedService.checkOwnership(order.getUser().getId());


        return cart.stream()
                .map(item -> new CartResponse(
                        item.getId(),
                        item.getOrder().getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getFullPrice()
                ))
                .toList();

    }

}