package com.olufunmi.Orderservice.web.controllers;

import com.olufunmi.Orderservice.dtos.requests.OrderRequest;
import com.olufunmi.Orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
private final OrderService orderService;
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "order placed successfully";
    }
}
