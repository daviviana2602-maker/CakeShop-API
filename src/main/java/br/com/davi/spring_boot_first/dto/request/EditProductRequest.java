package br.com.davi.spring_boot_first.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter


public class EditProductRequest {

    @Size(max = 50, message = "Product name cannot exceed 50 characters")
    private String name;

    @DecimalMin(value = "0.01", message = "Price must be greater than zero")
    @DecimalMax(value = "5000", message = "Price must be less than or equal to 5000")
    private BigDecimal price;

    @Min(value = 0, message = "Quantity cannot be negative")
    @Max(value = 100, message = "Quantity cannot exceed 100")
    private Integer quantity;

}