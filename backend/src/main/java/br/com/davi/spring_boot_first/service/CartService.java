package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.CartResponse;
import br.com.davi.spring_boot_first.entity.CartItemsEntity;
import br.com.davi.spring_boot_first.entity.OrderEntity;
import br.com.davi.spring_boot_first.entity.ProductEntity;
import br.com.davi.spring_boot_first.enums.OrderStatusEnum;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.ConflictException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.CartRepository;
import br.com.davi.spring_boot_first.repository.OrderRepository;
import br.com.davi.spring_boot_first.repository.ProductRepository;
import br.com.davi.spring_boot_first.security.AuthenticatedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;


@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final AuthenticatedService authenticatedService;


    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository,
                       OrderRepository orderRepository,
                       AuthenticatedService authenticatedService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.authenticatedService = authenticatedService;
    }


    private OrderEntity findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("order not found"));
    }


    private ProductEntity findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("product not found"));
    }


    private Optional<CartItemsEntity> findItemInOrderById(Long orderId, Long productId) {
        return cartRepository.findByOrderIdAndProductId(orderId, productId);
    }


    @Transactional
    public CartResponse editCart(Long orderId, Long productId, Integer quantity) {

        OrderEntity order = findOrderById(orderId);

        authenticatedService.checkOwnership(order.getUser().getId());


        if (order.getStatus() == OrderStatusEnum.CANCELED || order.getStatus() == OrderStatusEnum.CONCLUDED) {
            throw new ConflictException("order status is " + order.getStatus());
        }


        ProductEntity product = findProductById(productId);

        Optional<CartItemsEntity> item = findItemInOrderById(orderId, productId);

        BigDecimal unitPrice = product.getPrice();


        CartItemsEntity cart;

        if (item.isPresent()) {
            cart = item.get();

            cart.setQuantity(cart.getQuantity() + quantity);

            if (cart.getQuantity() < 0) {
                throw new BadRequestException("quantity is negative");
            }

            if (cart.getQuantity() == 0) {

                cartRepository.delete(cart);

                return new CartResponse(
                        cart.getId(),
                        cart.getOrder().getId(),
                        cart.getProductId(),
                        cart.getQuantity(),
                        cart.getUnitPrice(),
                        cart.getFullPrice()
                );
            }
        }
        else{
            cart = new CartItemsEntity();

            if (quantity < 1) {
                throw new BadRequestException("quantity must be greater than 0");
            }

            cart.setQuantity(quantity);
        }


        cart.setOrder(order);
        cart.setProductId(productId);
        cart.setUnitPrice(unitPrice);
        cart.setFullPrice(cart.getUnitPrice().multiply(new BigDecimal(cart.getQuantity())));

        cartRepository.save(cart);


        return new CartResponse(
                cart.getId(),
                cart.getOrder().getId(),
                cart.getProductId(),
                cart.getQuantity(),
                cart.getUnitPrice(),
                cart.getFullPrice()
        );

    }

}