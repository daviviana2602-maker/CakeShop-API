package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.CartResponse;
import br.com.davi.spring_boot_first.entity.CartItemsEntity;
import br.com.davi.spring_boot_first.entity.OrderEntity;
import br.com.davi.spring_boot_first.entity.ProductEntity;
import br.com.davi.spring_boot_first.entity.UserEntity;
import br.com.davi.spring_boot_first.enums.OrderStatusEnum;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.ConflictException;
import br.com.davi.spring_boot_first.repository.CartRepository;
import br.com.davi.spring_boot_first.repository.OrderRepository;
import br.com.davi.spring_boot_first.repository.ProductRepository;
import br.com.davi.spring_boot_first.security.AuthenticatedService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AuthenticatedService authenticatedService;

    @InjectMocks
    private CartService cartService;


    @Test
    void shouldAddNewItemToCart() {

        UserEntity user = new UserEntity();
        user.setId(1L);

        OrderEntity order = new OrderEntity();
        order.setId(1L);
        order.setUser(user);
        order.setStatus(OrderStatusEnum.PENDING);

        ProductEntity product = new ProductEntity();
        product.setId(10L);
        product.setPrice(BigDecimal.valueOf(50));


        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(productRepository.findById(10L))
                .thenReturn(Optional.of(product));

        when(cartRepository.findByOrderIdAndProductId(1L, 10L))
                .thenReturn(Optional.empty());


        CartResponse response =
                cartService.editCart(
                        1L,
                        10L,
                        2
                );


        assertEquals(2, response.getQuantity());
        assertEquals(0, BigDecimal.valueOf(100).compareTo(response.getFullPrice()));


        verify(cartRepository).
                save(any(CartItemsEntity.class));

    }


    @Test
    void shouldIncreaseExistingItemQuantity() {

        UserEntity user = new UserEntity();
        user.setId(1L);

        OrderEntity order = new OrderEntity();
        order.setId(1L);
        order.setUser(user);
        order.setStatus(OrderStatusEnum.PENDING);


        ProductEntity product = new ProductEntity();
        product.setId(10L);
        product.setPrice(BigDecimal.valueOf(50));


        CartItemsEntity cart = new CartItemsEntity();
        cart.setProduct(product);
        cart.setOrder(order);
        cart.setQuantity(5);
        cart.setUnitPrice(BigDecimal.valueOf(50));
        cart.setFullPrice(BigDecimal.valueOf(250));


        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(productRepository.findById(10L))
                .thenReturn(Optional.of(product));

        when(cartRepository.findByOrderIdAndProductId(1L, 10L))
                .thenReturn(Optional.of(cart));


        CartResponse response =
                cartService.editCart(1L, 10L, 2);


        assertEquals(7, response.getQuantity());
        assertEquals(0, BigDecimal.valueOf(350)
                .compareTo(response.getFullPrice()));
    }


    @Test
    void shouldThrowConflictWhenOrderIsConcluded() {

        UserEntity user = new UserEntity();
        user.setId(1L);

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setStatus(OrderStatusEnum.CONCLUDED);


        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));


        assertThrows(
                ConflictException.class,
                () -> cartService.editCart(
                        1L,
                        10L,
                        1
                )
        );
    }


    @Test
    void shouldThrowBadRequestWhenFinalQuantityIsNegative() {

        UserEntity user = new UserEntity();
        user.setId(1L);

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setStatus(OrderStatusEnum.PENDING);

        ProductEntity product = new ProductEntity();
        product.setPrice(BigDecimal.valueOf(50));

        CartItemsEntity cart = new CartItemsEntity();
        cart.setQuantity(2);


        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        when(productRepository.findById(10L))
                .thenReturn(Optional.of(product));

        when(cartRepository.findByOrderIdAndProductId(1L, 10L))
                .thenReturn(Optional.of(cart));


        assertThrows(
                BadRequestException.class,
                () -> cartService.editCart(
                        1L,
                        10L,
                        -5
                )
        );

    }

}