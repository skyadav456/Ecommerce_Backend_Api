package com.sharad.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharad.entity.Cart;
import com.sharad.entity.CartItem;
import com.sharad.entity.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

		Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
		List<CartItem> findByCart(Cart cart);

}
