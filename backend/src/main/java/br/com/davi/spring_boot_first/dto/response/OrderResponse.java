package br.com.davi.spring_boot_first.dto.response;

import br.com.davi.spring_boot_first.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor


public class OrderResponse {
    private Long id;
    private Long userId;
    private String name;
    private String Email;
    private OrderStatusEnum status;
    private BigDecimal price;
}