package br.com.davi.spring_boot_first.controllers;


import br.com.davi.spring_boot_first.dto.request.CartRequest;
import br.com.davi.spring_boot_first.dto.response.CartResponse;
import br.com.davi.spring_boot_first.dto.response.ConcludeOrderResponse;
import br.com.davi.spring_boot_first.dto.response.OrderResponse;
import br.com.davi.spring_boot_first.enums.OrderStatusEnum;
import br.com.davi.spring_boot_first.service.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final ListCartService listCartService;
    private final ListOrdersService listOrdersService;


    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    public OrderResponse generateOrder()
    {
        return createOrderService.createOrder();
    }


    @PostMapping("{orderId}/items")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    public CartResponse Cart(
        @PathVariable Long orderId,
        @Valid @RequestBody CartRequest cartRequest
    )
    {
        return cartService.editCart(
            orderId,
            cartRequest.getProductId(),
            cartRequest.getQuantity()
        );
    }


    @PostMapping("{orderId}/cancel")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    public Long excludeOrder(
        @PathVariable Long orderId
    )
    {
        return cancelOrderService.cancelOrder(orderId);
    }


    @PostMapping("{orderId}/conclude")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    public List<ConcludeOrderResponse> buyOrder(
        @PathVariable Long orderId
    )
    {
        return concludeOrderService.concludeOrder(orderId);
    }


    @GetMapping("{orderId}/list")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "bearerAuth")
    public List<CartResponse> listOrder(
        @PathVariable Long orderId
    )
    {
        return listCartService.listItems(orderId);
    }


    @GetMapping("{status}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public Page<OrderResponse> listOrdersStatus(
            @PathVariable OrderStatusEnum status,
            @RequestParam(defaultValue = "0") int page
    )
    {
        return listOrdersService.listOrders(status, page);
    }


}
