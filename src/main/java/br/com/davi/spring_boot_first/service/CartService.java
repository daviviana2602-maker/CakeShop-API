package br.com.davi.spring_boot_first.service;

import br.com.davi.spring_boot_first.dto.response.CartResponse;
import br.com.davi.spring_boot_first.entity.CartEntity;
import br.com.davi.spring_boot_first.entity.OrderEntity;
import br.com.davi.spring_boot_first.entity.ProductEntity;
import br.com.davi.spring_boot_first.enums.OrderStatusEnum;
import br.com.davi.spring_boot_first.exception.BadRequestException;
import br.com.davi.spring_boot_first.exception.ConflictException;
import br.com.davi.spring_boot_first.exception.NotFoundException;
import br.com.davi.spring_boot_first.repository.CartRepository;
import br.com.davi.spring_boot_first.repository.OrderRepository;
import br.com.davi.spring_boot_first.repository.ProductRepository;
import br.com.davi.spring_boot_first.security.OwnershipService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;


@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OwnershipService ownershipService;


    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository,
                       OrderRepository orderRepository,
                       OwnershipService ownershipService) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.ownershipService = ownershipService;
    }


    private OrderEntity findOrderId(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("order not found"));
    }


    private ProductEntity findProductId(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("product not found"));
    }

    // discovering if the item already exists in the order
    private Optional<CartEntity> findItemId(Long orderId, Long productId) {
        return cartRepository.findByOrderIdAndProductId(orderId, productId);
    }


    @Transactional
    public CartResponse editCart(Long orderId, Long productId, Integer quantity) {

        OrderEntity order = findOrderId(orderId);

        ownershipService.checkOwnership(order.getUserId());


        if (order.getStatus() == OrderStatusEnum.CANCELED || order.getStatus() == OrderStatusEnum.CONCLUDED) {
            throw new ConflictException("order status is " + order.getStatus());
        }


        ProductEntity product = findProductId(productId);

        Optional<CartEntity> item = findItemId(orderId, productId);

        BigDecimal unitPrice = product.getPrice();


        CartEntity cart;

        if (item.isPresent()) {
            cart = item.get();

            cart.setQuantity(cart.getQuantity() + quantity);

            if (cart.getQuantity() < 0) {
                throw new BadRequestException("quantity is negative");
            }

        }
        else{
            cart = new CartEntity();

            if (quantity < 1) {
                throw new BadRequestException("quantity must be greater than 0");
            }

            cart.setQuantity(quantity);
        }


        cart.setOrderId(orderId);
        cart.setProductId(productId);
        cart.setUnitPrice(unitPrice);
        cart.setFullPrice(cart.getUnitPrice().multiply(new BigDecimal(cart.getQuantity())));

        cartRepository.save(cart);


        return new CartResponse(
                cart.getId(),
                cart.getOrderId(),
                cart.getProductId(),
                cart.getQuantity(),
                cart.getUnitPrice(),
                cart.getFullPrice()
        );

    }

}