package br.com.davi.spring_boot_first.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor


public class CartRequest {

    @NotNull(message = "Product ID is required")
    Long productId;

    @NotNull(message = "Quantity is required")
    Integer quantity;

}