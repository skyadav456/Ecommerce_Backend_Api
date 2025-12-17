package com.sharad.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharad.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Optional<Cart> findByCustomer_CustomerId(Long customerId);
	Optional<Cart> findByCustomer_CustomerIdAndStatus(Long customerId, String status);
}
