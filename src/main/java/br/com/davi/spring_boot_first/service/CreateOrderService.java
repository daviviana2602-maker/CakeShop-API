package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.OrderResponse;
import br.com.davi.spring_boot_first.entity.OrderEntity;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.OrderStatusEnum;
import br.com.davi.spring_boot_first.exception.ConflictException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.OrderRepository;
import br.com.davi.spring_boot_first.repository.UserRepository;
import br.com.davi.spring_boot_first.security.OwnershipService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CreateOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OwnershipService ownershipService;


    public CreateOrderService(OrderRepository orderRepository,
                              UserRepository userRepository,
                              OwnershipService ownershipService)
    {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.ownershipService = ownershipService;
    }


    private UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }


    private boolean existsPendingOrderByUserIdAndStatus(Long userId, OrderStatusEnum status) {
        return orderRepository.existsByUserIdAndStatus(userId, status);
    }


    @Transactional
    public OrderResponse createOrder(Long userId){

        ownershipService.checkOwnership(userId);


        if (existsPendingOrderByUserIdAndStatus(userId, OrderStatusEnum.PENDING)) {
            throw new ConflictException("pending order already exists");
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
            order.getStatus()
        );

    }

}
