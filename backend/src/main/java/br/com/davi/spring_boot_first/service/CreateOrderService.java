package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.OrderResponse;
import br.com.davi.spring_boot_first.entity.OrderEntity;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.OrderStatusEnum;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.OrderRepository;
import br.com.davi.spring_boot_first.repository.UserRepository;
import br.com.davi.spring_boot_first.security.AuthenticatedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class CreateOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AuthenticatedService authenticatedService;


    public CreateOrderService(OrderRepository orderRepository,
                              UserRepository userRepository,
                              AuthenticatedService authenticatedService)
    {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.authenticatedService = authenticatedService;
    }


    private UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }


    private Optional<OrderEntity> findPendingOrderByUserIdAndStatus(Long userId, OrderStatusEnum status) {
        return orderRepository.findByUserIdAndStatus(userId, status);
    }


    @Transactional
    public OrderResponse createOrder(){

        Long userId = authenticatedService.getAuthenticatedUserId();


        Optional<OrderEntity> currentOrder = findPendingOrderByUserIdAndStatus(userId, OrderStatusEnum.PENDING);


        if (currentOrder.isPresent()) {
            OrderEntity order = currentOrder.get();

            return new OrderResponse(
                    order.getId(),
                    order.getUser().getId(),
                    order.getUser().getName(),
                    order.getStatus(),
                    order.getPrice()
            );
        }


        UserEntity user = findUserById(userId);

        OrderEntity order = new OrderEntity();

        order.setUser(user);
        order.setStatus(OrderStatusEnum.PENDING);

        orderRepository.save(order);

        return new OrderResponse(
            order.getId(),
            order.getUser().getId(),
            user.getName(),
            order.getStatus(),
            order.getPrice()
        );

    }

}
