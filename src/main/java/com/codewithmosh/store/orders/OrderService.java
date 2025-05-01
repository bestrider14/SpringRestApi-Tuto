package com.codewithmosh.store.orders;

import com.codewithmosh.store.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AuthService authService;

    public List<OrderDto> getAllOrders() {
        var customer = authService.getCurrentUser();
        var orders = orderRepository.getOrdersByCustomer(customer);

        var orderDtos = new ArrayList<OrderDto>();
        for (var order : orders) {
            System.out.println(order);
            orderDtos.add(orderMapper.toDto(order));
            System.out.println(orderMapper.toDto(order));
        }

        return orderDtos;
    }

    public OrderDto getOrder(Long orderId) {
        var order = orderRepository.getOrderWithItems(orderId).orElseThrow(OrderNotFoundException::new);
        var customer = authService.getCurrentUser();

        if(!order.isPlacedBy(customer))
            throw new AccessDeniedException("Access denied");

        return orderMapper.toDto(order);
    }
}
