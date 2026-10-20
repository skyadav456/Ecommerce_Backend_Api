package com.sharad.service;

import com.sharad.dto.CartResponseDto;
import com.sharad.entity.Cart;

public interface CartService {
	
   Cart getOrCreateCart(Long customerId);
   CartResponseDto addProductToCart(Long customerId, Long productId, int quantity);
   CartResponseDto removeProductFromCart(Long customerId, Long productId);
   CartResponseDto viewCart(Long customerId);
   // Enhancement: Update product quantity in cart
   CartResponseDto updateProductQuantity(Long customerId, Long productId, int quantity);
   Cart clearCart(Long customerId);

}
