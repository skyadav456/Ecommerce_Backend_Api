package com.sharad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sharad.entity.Cart;
import com.sharad.service.CartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	/*View Cart
		http://localhost:8080/api/cart/view/1            --> API Endpoint
		*/
	@GetMapping("/view/{customerId}")
	public Cart viewCart(@PathVariable Long customerId) {
		return cartService.viewCart(customerId);
	}
	
	/*
	 * add Product to Cart
		http://localhost:8080/api/cart/1/add?productId=5&quantity=2           --> API Endpoint
	*/
	@PostMapping("/{customerId}/add")
	public Cart addProductToCart(@PathVariable Long customerId,@RequestParam Long productId, @RequestParam int quantity) {
		return cartService.addProductToCart(customerId, productId, quantity);
	}
	
	/*remove Product from Cart
	 * 	 http://localhost:8080/api/cart/customer/1/removeProduct?productId=5		   										--> API Endpoint	
	*/
	@DeleteMapping("/customer/{customerId}/removeProduct")
	public Cart removeProductFromCart(@PathVariable Long customerId,@RequestParam Long productId) {
		return cartService.removeProductFromCart(customerId, productId);
	}
	
	/*Update Product Quantity in Cart
			http://localhost:8080/api/cart/customer/1/updateQuantity?productId=5&quantity=3            --> API Endpoint
	*/
	@PutMapping("/customer/{customerId}/updateQuantity")
	public Cart updateProQuantity(@PathVariable Long customerId, @RequestParam Long productId, @RequestParam int quantity) {
		return cartService.updateProductQuantity(customerId, productId, quantity);
	}
	
	/*Clear Cart
	http://localhost:8080/api/cart/customer/1/clear		--> API Endpoint
	*/
	@DeleteMapping("/customer/{customerId}/clear")
	public Cart clearCart(@PathVariable Long customerId) {
		return cartService.clearCart(customerId);
	}

}
