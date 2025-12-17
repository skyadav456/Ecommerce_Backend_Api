package com.sharad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	// View Cart
	@GetMapping("/view/{customerId}")
	public Cart viewCart(@PathVariable Long customerId) {
		return cartService.viewCart(customerId);
	}
	
	// add Product to Cart
	@PostMapping("/add/{customerId}/addProduct")
	public Cart addProductToCart(@PathVariable Long customerId,@RequestParam Long productId, @RequestParam int quantity) {
		return cartService.addProductToCart(customerId, productId, quantity);
	}
	
	// remove Product from Cart
	@DeleteMapping("/remove/{customerId}/removeProduct")
	public Cart removeProductFromCart(@PathVariable Long customerId,@RequestParam Long productId) {
		return cartService.removeProductFromCart(customerId, productId);
	}

}
