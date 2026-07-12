package br.com.davi.spring_boot_first.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter

@AllArgsConstructor


public class EditProductResponse {
    private Long id;
    private String name;
    private BigDecimal price;
}