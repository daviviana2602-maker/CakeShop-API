package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.ConcludeOrderResponse;
import br.com.davi.spring_boot_first.entity.CartEntity;
import br.com.davi.spring_boot_first.entity.ConcludedEntity;
import br.com.davi.spring_boot_first.entity.OrderEntity;
import br.com.davi.spring_boot_first.enums.OrderStatusEnum;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.CartRepository;
import br.com.davi.spring_boot_first.repository.ConcludedRepository;
import br.com.davi.spring_boot_first.repository.OrderRepository;
import br.com.davi.spring_boot_first.security.OwnershipService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class ConcludeOrderService {

    private final ConcludedRepository concludedRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OwnershipService ownershipService;


    public ConcludeOrderService(ConcludedRepository concludedRepository,
                                CartRepository cartRepository,
                                OrderRepository orderRepository,
                                OwnershipService ownershipService) {
        this.concludedRepository = concludedRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.ownershipService = ownershipService;
    }


    public OrderEntity findOrderId(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new NotFoundException("Order not found"));
    }

    
    @Transactional
    public List<ConcludeOrderResponse> concludeOrder(Long orderId) {

        OrderEntity order = findOrderId(orderId);

        ownershipService.checkOwnership(order.getUserId());

        
        order.setStatus(OrderStatusEnum.CONCLUDED);
        orderRepository.save(order);


        List<CartEntity> cart = cartRepository.findByOrderId(orderId);

        List<ConcludeOrderResponse> response = new ArrayList<>();

        for (CartEntity car : cart) {

            ConcludedEntity concluded = new ConcludedEntity();

            concluded.setOrderId(orderId);
            concluded.setProductId(car.getProductId());
            concluded.setQuantity(car.getQuantity());
            concluded.setUnitPrice(car.getUnitPrice());
            concluded.setFullPrice(car.getFullPrice());

            ConcludedEntity saved = concludedRepository.save(concluded);

            response.add(
                    new ConcludeOrderResponse(
                            saved.getId(),
                            saved.getOrderId(),
                            saved.getProductId(),
                            saved.getQuantity(),
                            saved.getUnitPrice(),
                            saved.getFullPrice()
                    )
            );

        }

        cartRepository.deleteByOrderId(orderId);

        return response;

    }

}
