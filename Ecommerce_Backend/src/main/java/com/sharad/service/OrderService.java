package com.sharad.service;
import java.util.List;

import com.sharad.entity.Order;

public interface OrderService {
	
	// Place a new order for a customer with the given address
    Order placeOrder(Long customerId, Long addressId);
    List<Order> getOrdersByCustomerId(Long customerId);
    Order orderDetails(Long orderId);
    List<Order> getAllOrders();
}
