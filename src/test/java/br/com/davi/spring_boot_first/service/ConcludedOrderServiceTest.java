package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.ConcludeOrderResponse;
import br.com.davi.spring_boot_first.entity.CartEntity;
import br.com.davi.spring_boot_first.entity.ConcludedEntity;
import br.com.davi.spring_boot_first.entity.OrderEntity;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.CartRepository;
import br.com.davi.spring_boot_first.repository.ConcludedRepository;
import br.com.davi.spring_boot_first.repository.OrderRepository;
import br.com.davi.spring_boot_first.security.OwnershipService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ConcludedOrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ConcludedRepository concludedRepository;

    @Mock
    private OwnershipService ownershipService;

    @InjectMocks
    private ConcludeOrderService concludeOrderService;


    @Test
    void shouldConcludeOrderSuccessfully() {

        OrderEntity order = new OrderEntity();
        order.setId(1L);
        order.setUserId(1L);


        CartEntity cart = new CartEntity();
        cart.setProductId(10L);
        cart.setQuantity(2);
        cart.setUnitPrice(BigDecimal.valueOf(50));
        cart.setFullPrice(BigDecimal.valueOf(100));


        ConcludedEntity concluded = new ConcludedEntity();
        concluded.setId(1L);
        concluded.setOrderId(1L);
        concluded.setProductId(10L);
        concluded.setQuantity(2);
        concluded.setUnitPrice(BigDecimal.valueOf(50));
        concluded.setFullPrice(BigDecimal.valueOf(100));


        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(cartRepository.findByOrderId(1L))
                .thenReturn(List.of(cart));

        when(concludedRepository.save(any(ConcludedEntity.class)))
                .thenReturn(concluded);


        List<ConcludeOrderResponse> response =
                concludeOrderService.concludeOrder(1L);


        assertEquals(1, response.size());   // Size of the list
        assertEquals(10L, response.get(0).getProductId());


        verify(concludedRepository)
                .save(any(ConcludedEntity.class));

        verify(cartRepository)
                .deleteByOrderId(1L);

    }


    @Test
    void shouldThrowNotFoundWhenOrderDoesNotExist() {

        when(orderRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> concludeOrderService.concludeOrder(1L)
        );

    }

}