package com.shopping.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.orderservice.model.Order;

public interface OrderServiceRepository extends JpaRepository<Order, Long>{

}
