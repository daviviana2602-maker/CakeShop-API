package br.com.davi.spring_boot_first.controllers;


import br.com.davi.spring_boot_first.dto.request.CartRequest;
import br.com.davi.spring_boot_first.dto.response.CartResponse;
import br.com.davi.spring_boot_first.dto.response.ConcludeOrderResponse;
import br.com.davi.spring_boot_first.dto.response.OrderResponse;
import br.com.davi.spring_boot_first.service.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/order")
@RequiredArgsConstructor


public class OrderController {


    private final CreateOrderService createOrderService;
    private final CartService cartService;
    private final CancelOrderService cancelOrderService;
    private final ConcludeOrderService concludeOrderService;
    private final ListOrderService listOrderService;


    @PostMapping("/create/{userId}")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    public OrderResponse generateOrder(
        @PathVariable Long userId
    )
    {
        return createOrderService.createOrder(userId);
    }


    @PostMapping("/items")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    public CartResponse Cart(
        @Valid @RequestBody CartRequest cartRequest
    )
    {
        return cartService.editCart(
            cartRequest.getOrderId(),
            cartRequest.getProductId(),
            cartRequest.getQuantity()
        );
    }


    @PostMapping("/cancel/{orderId}")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    public Long excludeOrder(
        @PathVariable Long orderId
    )
    {
        return cancelOrderService.cancelOrder(orderId);
    }


    @PostMapping("/conclude/{orderId}")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    public List<ConcludeOrderResponse> buyOrder(
        @PathVariable Long orderId
    )
    {
        return concludeOrderService.concludeOrder(orderId);
    }


    @GetMapping("/list/{orderId}")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    public List<CartResponse> listOrder(
        @PathVariable Long orderId
    )
    {
        return listOrderService.listItems(orderId);
    }


}
