package br.com.davi.spring_boot_first.service;


import br.com.davi.spring_boot_first.dto.response.OrderResponse;
import br.com.davi.spring_boot_first.entity.OrderEntity;
import br.com.davi.spring_boot_first.enums.OrderStatusEnum;
import br.com.davi.spring_boot_first.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ListOrdersService {

    private final OrderRepository orderRepository;


    public ListOrdersService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    private Page<OrderEntity> findByStatus(OrderStatusEnum status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable);
    }


    public Page<OrderResponse> listOrders(OrderStatusEnum status, int page) {


        Pageable pageable = PageRequest.of(page, 10);

        Page<OrderEntity> pageOrders = findByStatus(status, pageable);


        return pageOrders .map(item -> new OrderResponse(
                        item.getId(),
                        item.getUser().getId(),
                        item.getUser().getName(),
                        item.getUser().getEmail(),
                        item.getStatus(),
                        item.getPrice()
                ));

    }

}