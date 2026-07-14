package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.entity.OrderEntity;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.OrderStatusEnum;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.CartRepository;
import br.com.davi.spring_boot_first.repository.OrderRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CancelOrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private AuthenticatedService authenticatedService;

    @InjectMocks
    private CancelOrderService cancelOrderService;


    @Test
    void shouldCancelOrderSuccessfully() {

        UserEntity user = new UserEntity();
        user.setId(1L);

        OrderEntity order = new OrderEntity();
        order.setId(1L);
        order.setUser(user);
        order.setStatus(OrderStatusEnum.PENDING);


        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));


        Long response =
                cancelOrderService.cancelOrder(1L);

        assertEquals(1L, response);
        assertEquals(OrderStatusEnum.CANCELED, order.getStatus());


        verify(cartRepository)
                .deleteAll(any());
    }


    @Test
    void shouldThrowNotFoundWhenOrderDoesNotExist() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> cancelOrderService.cancelOrder(1L)
        );

    }

}