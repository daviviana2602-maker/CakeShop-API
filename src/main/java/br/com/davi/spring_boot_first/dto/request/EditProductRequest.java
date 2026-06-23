package br.com.davi.spring_boot_first.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter


public class EditProductRequest {

    private String name;

    private BigDecimal price;

    private Integer quantity;

}