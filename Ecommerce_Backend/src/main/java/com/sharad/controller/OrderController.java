package com.sharad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sharad.entity.Order;
import com.sharad.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	// http ://localhost:8080/orders/place/1?addressId=2
	@PostMapping("/place/{customerId}")	
	public Order placeOrder(@PathVariable Long customerId, @RequestParam Long addressId) {
		return orderService.placeOrder(customerId, addressId);
	}
	
	/*
	 * http://localhost:8080/orders/customer/1
	*/	
	@GetMapping("/customer/{customerId}")
	public List<Order> getOrdersByCustomerId(@PathVariable Long customerId) {
		return orderService.getOrdersByCustomerId(customerId);
	}
	
	/*
	 * http://localhost:8080/orders/1
	*/
	@GetMapping("/{orderId}")
	public Order orderDetails(@PathVariable Long orderId) {
		return orderService.orderDetails(orderId);
	}
	
	/*
	 * http://localhost:8080/orders
	*/
	@GetMapping
	public List<Order> getAllOrders(){
		return	orderService.getAllOrders();
	}

}
