package com.olufunmi.Orderservice.services;

import com.olufunmi.Orderservice.data.models.Order;
import com.olufunmi.Orderservice.data.models.OrderLineItems;
import com.olufunmi.Orderservice.data.repositories.OrderRepository;
import com.olufunmi.Orderservice.dtos.requests.OrderRequest;
import com.olufunmi.Orderservice.dtos.responses.OrderLineItemsDto;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;


    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .toList()
                .stream()
                .map(this::mapToOrderDto).toList();

        order.setOrderLineItemsList(orderLineItemsList);
        orderRepository.save(order);

    }

    private OrderLineItems mapToOrderDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItem = new OrderLineItems();
        orderLineItem.setPrice(orderLineItemsDto.getPrice());
        orderLineItem.setSkuCode(orderLineItem.getSkuCode());
        orderLineItem.setQuantity(orderLineItemsDto.getQuantity());

        return orderLineItem;
    }
}
