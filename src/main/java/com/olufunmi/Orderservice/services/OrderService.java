package com.olufunmi.Orderservice.services;


import com.olufunmi.Orderservice.dtos.requests.OrderRequest;

public interface OrderService {
    void placeOrder(OrderRequest orderRequest);
}
