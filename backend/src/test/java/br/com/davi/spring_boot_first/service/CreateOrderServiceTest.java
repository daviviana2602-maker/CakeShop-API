package br.com.davi.spring_boot_first.service;


import br.com.davi.spring_boot_first.dto.response.OrderResponse;
import br.com.davi.spring_boot_first.entity.OrderEntity;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.OrderStatusEnum;
import br.com.davi.spring_boot_first.exception.ConflictException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
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

        when(orderRepository.existsByUserIdAndStatus(1L, OrderStatusEnum.PENDING))
                .thenReturn(false);

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
    void shouldThrowConflictWhenUserAlreadyHasPendingOrder() {

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("Davi");

        when(authenticatedService.getAuthenticatedUserId())
                .thenReturn(1L);

        when(orderRepository.existsByUserIdAndStatus(1L, OrderStatusEnum.PENDING))
                .thenReturn(true);


        assertThrows(
                ConflictException.class,
                () -> createOrderService.createOrder()
        );


        verify(userRepository, never())
                .findById(1L);

        verify(orderRepository, never())
                .save(any(OrderEntity.class));

    }


}
