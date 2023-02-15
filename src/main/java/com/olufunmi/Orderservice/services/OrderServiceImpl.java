package com.olufunmi.Orderservice.services;

import com.olufunmi.Orderservice.data.models.Order;
import com.olufunmi.Orderservice.data.models.OrderLineItems;
import com.olufunmi.Orderservice.data.repositories.OrderLineRepository;
import com.olufunmi.Orderservice.data.repositories.OrderRepository;
import com.olufunmi.Orderservice.dtos.requests.OrderRequest;
import com.olufunmi.Orderservice.dtos.responses.InventoryResponse;
import com.olufunmi.Orderservice.dtos.responses.OrderLineItemsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final WebClient webClient;



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

        List <String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode).toList();

       InventoryResponse[] inventoryResponses = webClient.get()
                .uri("http://localhost:8081/api/v1/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        assert inventoryResponses != null;
        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if(allProductsInStock){
        orderRepository.save(order);}
        else {
            throw new IllegalArgumentException("Product not in stock, check back later");
        }

    }

    private OrderLineItems mapToOrderDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItem = new OrderLineItems();
        orderLineItem.setPrice(orderLineItemsDto.getPrice());
        orderLineItem.setSkuCode(orderLineItem.getSkuCode());
        orderLineItem.setQuantity(orderLineItemsDto.getQuantity());

        return orderLineItem;
    }
}
