package com.olufunmi.Orderservice.data.repositories;

import com.olufunmi.Orderservice.data.models.OrderLineItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLineItems, Long> {
}
