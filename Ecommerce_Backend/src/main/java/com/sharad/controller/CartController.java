package com.sharad.controller;

import com.sharad.dto.CartResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
//@CrossOrigin(origins = "http://localhost:3000")
public class CartController {
	

	private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


	// http://localhost:8080/api/cart/view/1
	@GetMapping("/view/{customerId}")
	public ResponseEntity<CartResponseDto> viewCart(@PathVariable Long customerId) {
		return new ResponseEntity<>(cartService.viewCart(customerId), HttpStatus.OK);
	}

	// http://localhost:8080/api/cart/1/add?productId=5&quantity=2
	@PostMapping("/{customerId}/add")
	public ResponseEntity<CartResponseDto> addProductToCart(@PathVariable Long customerId,
											@RequestParam Long productId,
											@RequestParam int quantity) {
		return new ResponseEntity<>(
				cartService.addProductToCart(customerId, productId, quantity),
				HttpStatus.OK);
	}
	

	//http://localhost:8080/api/cart/customer/1/removeProduct?productId=5		   										--> API Endpoint
	@DeleteMapping("/customer/{customerId}/removeProduct")
	public ResponseEntity<CartResponseDto> removeProductFromCart(@PathVariable Long customerId,@RequestParam Long productId) {
		return new ResponseEntity<>(cartService.removeProductFromCart(customerId, productId), HttpStatus.OK);
	}
	

	//http://localhost:8080/api/cart/customer/1/updateQuantity?productId=5&quantity=3
	@PutMapping("/customer/{customerId}/updateQuantity")
	public ResponseEntity<CartResponseDto> updateProQuantity(@PathVariable Long customerId,
															 @RequestParam Long productId,
															 @RequestParam int quantity) {
		CartResponseDto cartResponse = cartService.updateProductQuantity(customerId, productId, quantity);
		return new ResponseEntity<>(cartResponse, HttpStatus.OK);
	}
	

	// http://localhost:8080/api/cart/customer/1/clear
	@DeleteMapping("/customer/{customerId}/clear")
	public ResponseEntity<Cart >clearCart(@PathVariable Long customerId) {
		return new ResponseEntity<>(cartService.clearCart(customerId),HttpStatus.OK);
	}

}
