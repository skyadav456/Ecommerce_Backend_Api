package com.sharad.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sharad.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Fetch all orders of a customer
    List<Order> findByCustomer_CustomerId(Long customerId);
}

