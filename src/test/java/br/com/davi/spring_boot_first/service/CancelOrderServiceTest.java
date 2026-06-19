package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.entity.CartEntity;
import br.com.davi.spring_boot_first.entity.OrderEntity;
import br.com.davi.spring_boot_first.enums.OrderStatusEnum;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.CartRepository;
import br.com.davi.spring_boot_first.repository.OrderRepository;
import br.com.davi.spring_boot_first.security.OwnershipService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CancelOrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private OwnershipService ownershipService;

    @InjectMocks
    private CancelOrderService cancelOrderService;


    @Test
    void shouldCancelOrderSuccessfully() {

        OrderEntity order = new OrderEntity();
        order.setId(1L);
        order.setUserId(1L);

        CartEntity item = new CartEntity();


        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(cartRepository.findByOrderId(1L))
                .thenReturn(List.of(item));

        Long response =
                cancelOrderService.cancelOrder(1L);

        assertEquals(1L, response);
        assertEquals(OrderStatusEnum.CANCELED, order.getStatus());


        verify(cartRepository)
                .delete(item);
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