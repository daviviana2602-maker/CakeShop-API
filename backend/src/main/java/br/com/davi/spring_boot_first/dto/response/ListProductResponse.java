package br.com.davi.spring_boot_first.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter

@AllArgsConstructor


public class ListProductResponse {
    private Long id;
    private String name;
    private BigDecimal price;
}
