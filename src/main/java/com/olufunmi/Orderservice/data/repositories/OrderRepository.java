package com.olufunmi.Orderservice.data.repositories;

import com.olufunmi.Orderservice.data.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface OrderRepository extends JpaRepository<Order, Long> {

}
