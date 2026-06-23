package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.CartResponse;
import br.com.davi.spring_boot_first.entity.CartEntity;
import br.com.davi.spring_boot_first.entity.OrderEntity;
import br.com.davi.spring_boot_first.repository.CartRepository;
import br.com.davi.spring_boot_first.repository.OrderRepository;
import br.com.davi.spring_boot_first.security.OwnershipService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ListCartService {

    private final CartRepository cartRepository;
    private final OwnershipService ownershipService;
    private final OrderRepository orderRepository;


    public ListCartService(CartRepository cartRepository,
                           OwnershipService ownershipService,
                           OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.ownershipService = ownershipService;
        this.orderRepository = orderRepository;
    }


    public List<CartResponse> listItems(Long orderId) {

        List<CartEntity> cart = cartRepository.findByOrderId(orderId);

        OrderEntity order = orderRepository.findById(orderId).get();

        ownershipService.checkOwnership(order.getUserId());


        return cart.stream()
                .map(item -> new CartResponse(
                        item.getId(),
                        item.getOrderId(),
                        item.getProductId(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getFullPrice()
                ))
                .toList();

    }

}