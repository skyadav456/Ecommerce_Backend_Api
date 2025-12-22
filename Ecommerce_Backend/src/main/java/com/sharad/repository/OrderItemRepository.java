package com.sharad.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sharad.entity.Order;
import com.sharad.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    //Fetch items for a given order
    List<OrderItem> findByOrder(Order order);
}
