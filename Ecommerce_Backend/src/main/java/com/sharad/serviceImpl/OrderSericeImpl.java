package com.sharad.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharad.entity.Address;
import com.sharad.entity.Cart;
import com.sharad.entity.CartItem;
import com.sharad.entity.Customer;
import com.sharad.entity.Order;
import com.sharad.entity.OrderItem;
import com.sharad.enums.OrderStatus;
import com.sharad.exception.AddressNotFoundException;
import com.sharad.exception.CartNotFoundException;
import com.sharad.exception.CustomerNotFoundException;
import com.sharad.repository.AddressRepository;
import com.sharad.repository.CartRepository;
import com.sharad.repository.CustomerRepository;
import com.sharad.repository.OrderRepository;
import com.sharad.service.OrderService;

@Service
public class OrderSericeImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private CartRepository cartRepository;
	
	
	

	@Override
	public Order placeOrder(Long customerId, Long addressId) {
		// validate customer
		Customer costomer = customerRepository.findById(customerId).orElseThrow(()-> 
																		new CustomerNotFoundException("Customer not found"));
		
		// validate address
		Address address = addressRepository.findById(addressId).orElseThrow(()->
																			new AddressNotFoundException("Address not found"));
		
		// fetch active cart and validate empty or not
		Cart cart = cartRepository.findByCustomer_CustomerIdAndStatus(customerId,"ACTIVE").orElseThrow(()->
																				new CartNotFoundException("Active cart not found"));
		if(cart.getItems().isEmpty() || cart.getItems()==null) {
			throw new IllegalStateException("Cart is empty");
		}
		
		//create order and set details
		Order order= new Order();
		order.setCustomer(costomer);
		order.setDeliveryAddress(address);
		order.setOrderDate(LocalDateTime.now());
		order.setStatus(OrderStatus.CREATED);
		
		BigDecimal totalAmount= BigDecimal.ZERO;
		// convert cart items to order items  and calculate total amount
		for(CartItem cartItem: cart.getItems()){
			OrderItem orderItem= new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setUnitPrice(cartItem.getProduct().getUnitPrice());
			orderItem.setSubtotal(cartItem.getSubtotal());
			// calculate total amount
//			totalAmount= totalAmount.add(cartItem.getSubtotal());
			totalAmount= totalAmount.add(orderItem.getSubtotal());
			order.setTotalAmount(totalAmount);
			order.getItems().add(orderItem);    // add order item to order
		}
		// save order
		Order savedOrder = orderRepository.save(order);
		// close cart
		cart.getItems().clear();
		cart.setStatus("CLOSED");
		cart.setTotalPrice(BigDecimal.ZERO);
		
		cartRepository.save(cart);
		// return order
		return savedOrder;
	}




	@Override
	public List<Order> getOrdersByCustomerId(Long customerId) {
		return orderRepository.findByCustomer_CustomerId(customerId);
		
	}


	@Override
	public Order orderDetails(Long orderId) {
		return orderRepository.findById(orderId).orElseThrow(()-> 
												new IllegalArgumentException("Order not found"));
	
	}


	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

}
