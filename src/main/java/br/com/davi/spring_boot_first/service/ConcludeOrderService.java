package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.ConcludeOrderResponse;
import br.com.davi.spring_boot_first.entity.CartItemsEntity;
import br.com.davi.spring_boot_first.entity.ConcludedItemsEntity;
import br.com.davi.spring_boot_first.entity.OrderEntity;
import br.com.davi.spring_boot_first.enums.OrderStatusEnum;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.CartRepository;
import br.com.davi.spring_boot_first.repository.ConcludedRepository;
import br.com.davi.spring_boot_first.repository.OrderRepository;
import br.com.davi.spring_boot_first.security.OwnershipService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

        ownershipService.checkOwnership(order.getUser().getId());


        boolean cartCheck = cartRepository.existsByOrderId(orderId);

        if (!cartCheck) {
            throw new BadRequestException("No products");
        }

        
        order.setStatus(OrderStatusEnum.CONCLUDED);


        List<CartItemsEntity> cart = cartRepository.findByOrderId(orderId);

        List<ConcludeOrderResponse> response = new ArrayList<>();

        for (CartItemsEntity item : cart) {

            ConcludedItemsEntity concluded = new ConcludedItemsEntity();

            concluded.setOrderId(orderId);
            concluded.setProductId(item.getProductId());
            concluded.setQuantity(item.getQuantity());
            concluded.setUnitPrice(item.getUnitPrice());
            concluded.setFullPrice(item.getFullPrice());

            ConcludedItemsEntity saved = concludedRepository.save(concluded);

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

        List<ConcludedItemsEntity> boughtProducts = concludedRepository.findByOrderId(orderId);

        BigDecimal orderPrice = BigDecimal.ZERO;

        for (ConcludedItemsEntity boughtItem : boughtProducts) {

            orderPrice = orderPrice.add(boughtItem.getFullPrice());

        }

        order.setPrice(orderPrice);


        cartRepository.deleteByOrderId(orderId);

        return response;

    }

}
