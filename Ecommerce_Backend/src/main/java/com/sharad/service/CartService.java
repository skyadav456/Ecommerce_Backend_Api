package com.sharad.service;

import com.sharad.entity.Cart;

public interface CartService {
	
	Cart getOrCreateCart(Long customerId);
   Cart addProductToCart(Long customerId, Long productId, int quantity);
   Cart removeProductFromCart(Long customerId, Long productId);
   Cart viewCart(Long customerId);

}
