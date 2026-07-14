package br.com.davi.spring_boot_first.service;


import br.com.davi.spring_boot_first.dto.response.OrderResponse;
import br.com.davi.spring_boot_first.entity.OrderEntity;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.OrderStatusEnum;
import br.com.davi.spring_boot_first.repository.OrderRepository;
import br.com.davi.spring_boot_first.repository.UserRepository;
import br.com.davi.spring_boot_first.security.AuthenticatedService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CreateOrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticatedService authenticatedService;

    @InjectMocks
    private CreateOrderService createOrderService;


    @Test
    void shouldCreateOrderSuccessfully() {

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("Davi");

        when(authenticatedService.getAuthenticatedUserId())
                .thenReturn(1L);

        when(orderRepository.findByUserIdAndStatus(1L, OrderStatusEnum.PENDING))
                .thenReturn(Optional.empty());

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));


        OrderResponse response =
                createOrderService.createOrder();


        assertEquals(1L, response.getUserId());
        assertEquals("Davi", response.getName());
        assertEquals(OrderStatusEnum.PENDING, response.getStatus());


        verify(orderRepository)
                .save(any(OrderEntity.class));

    }



    @Test
    void shouldReturnExistingPendingOrder() {

        OrderEntity order = new OrderEntity();

        order.setId(10L);
        order.setStatus(OrderStatusEnum.PENDING);


        UserEntity user = new UserEntity();

        user.setId(1L);
        user.setName("Davi");

        order.setUser(user);


        when(authenticatedService.getAuthenticatedUserId())
                .thenReturn(1L);


        when(orderRepository.findByUserIdAndStatus(1L, OrderStatusEnum.PENDING))
                .thenReturn(Optional.of(order));



        OrderResponse response =
                createOrderService.createOrder();



        assertEquals(10L, response.getId());
        assertEquals(1L, response.getUserId());
        assertEquals("Davi", response.getName());
        assertEquals(OrderStatusEnum.PENDING, response.getStatus());


        verify(userRepository, never())
                .findById(anyLong());


        verify(orderRepository, never())
                .save(any(OrderEntity.class));

    }


}
