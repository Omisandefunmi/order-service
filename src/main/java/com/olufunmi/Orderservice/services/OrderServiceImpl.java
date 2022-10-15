package com.olufunmi.Orderservice.services;

import com.olufunmi.Orderservice.data.models.Order;
import com.olufunmi.Orderservice.data.models.OrderLineItems;
import com.olufunmi.Orderservice.data.repositories.OrderLineRepository;
import com.olufunmi.Orderservice.data.repositories.OrderRepository;
import com.olufunmi.Orderservice.dtos.requests.OrderRequest;
import com.olufunmi.Orderservice.dtos.responses.OrderLineItemsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
//    private final OrderLineRepository orderLineRepository;


    @Override
    public void placeOrder(OrderRequest orderRequest) {


        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .toList()
                .stream()
                .map(this::mapToOrderDto).toList();

//        Order order = Order.builder()
//                .orderNumber(UUID.randomUUID().toString())
//                .orderLineItemsList(orderLineItemsList)
//                .build();

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

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
