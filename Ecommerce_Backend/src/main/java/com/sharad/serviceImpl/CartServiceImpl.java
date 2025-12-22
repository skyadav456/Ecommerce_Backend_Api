package com.sharad.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sharad.entity.Cart;
import com.sharad.entity.CartItem;
import com.sharad.entity.Customer;
import com.sharad.entity.Product;
import com.sharad.exception.CustomerNotFoundException;
import com.sharad.exception.ProductNotFoundException;
import com.sharad.repository.CartItemRepository;
import com.sharad.repository.CartRepository;
import com.sharad.repository.CustomerRepository;
import com.sharad.repository.ProductRepository;
import com.sharad.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	
	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private CartItemRepository cartItemRepo;
	@Autowired
	private CustomerRepository custRepo;
	@Autowired
	private ProductRepository productRepo;

	
	
	

	@Override
	public Cart getOrCreateCart(Long customerId) {
		Customer customer = custRepo.findById(customerId).orElseThrow(()->
																	new CustomerNotFoundException("Customer not found"+customerId));
		
		// Check for active cart if not found create new cart
		Optional<Cart> existCart = cartRepo.findByCustomer_CustomerIdAndStatus(customerId,"ACTIVE");
		if(existCart.isPresent()) {
			return existCart.get();
		} else {
			Cart newCart = new Cart();
			newCart.setCustomer(customer);
			newCart.setTotalPrice(BigDecimal.ZERO);
			newCart.setStatus("ACTIVE");
			return cartRepo.save(newCart);
		}
	}

	@Override
	public Cart addProductToCart(Long customerId, Long productId, int quantity) {
		// check quantity >0
		if(quantity <=0) {
			throw new IllegalArgumentException("Quantity must be greater than zero");
		}
		
		// get the cart
		Cart cart = getOrCreateCart(customerId);
		if (cart.getItems() == null) {
	        cart.setItems(new ArrayList<>());
	    }
		// get the product
		Product product =productRepo.findById(productId).orElseThrow(()->
				new ProductNotFoundException( productId));
		
//		Optional<CartItem> existCartItem = cartItemRepo.findByCartAndProduct(cart,product);
		Optional<CartItem> existCartItem =
	            cart.getItems().stream()
	                .filter(item ->
	                    item.getProduct().getProductId().equals(productId))
	                .findFirst();
		
		if(existCartItem.isPresent()) {
			//Product already in cart → increase quantity and subtotal
			CartItem item =existCartItem.get();
			int newQuantity=item.getQuantity()+quantity;
			item.setQuantity(newQuantity);
			item.setSubtotal(item.getUnitPrice().multiply(BigDecimal.valueOf(newQuantity)));
			//existCartItem.get().setQuantity(existCartItem.get().getQuantity()+quantity);
		}else {
			 //Product not in cart  New Product --> create new cart item
			CartItem newItem= new CartItem();
			newItem.setCart(cart);
			newItem.setProduct(product);
			newItem.setQuantity(quantity);
			newItem.setUnitPrice(product.getUnitPrice());
			newItem.setSubtotal(product.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
			cart.getItems().add(newItem);
		}
		// Update cart total price
		calculateNewCartTotal(cart);
		// Save cart and return
		return cartRepo.save(cart);
	}

	// Remove product from cart
	@Override
	public Cart removeProductFromCart(Long customerId, Long productId) {
		Cart cart = getOrCreateCart(customerId);
		cart.getItems().removeIf(item->item.getProduct().getProductId().equals(productId));
		// Update cart total price
		calculateNewCartTotal(cart);
		// Save cart and return
		return cartRepo.save(cart);
	}

	@Override
	public Cart viewCart(Long customerId) {
		Cart cart = getOrCreateCart(customerId);
		if(cart!=null) {
			return cart;
		}
		throw new CustomerNotFoundException("Cart not found for customer id: "+customerId);
	}
	
	// Update cart total price
	 private void calculateNewCartTotal(Cart cart) {
		 BigDecimal totalPrice = cart.getItems().stream()
					.map(CartItem::getSubtotal)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			cart.setTotalPrice(totalPrice);

	    }

	 //  Update product quantity in cart
	 @Override
	 public Cart updateProductQuantity(Long customerId, Long productId, int quantity) {
		Cart cart = getOrCreateCart(customerId);
		Optional<CartItem> item = cart.getItems().stream().filter(items->items.getProduct().getProductId().equals(productId)).findFirst();
		if(item.isEmpty()) {
			throw new ProductNotFoundException(productId);
		}else {
			if(quantity<=0) {
				throw new IllegalArgumentException("Quantity can not be negative or zero");
			}else {
				CartItem cartItem = item.get();
				cartItem.setQuantity(quantity);
				cartItem.setSubtotal(cartItem.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
				// Update cart total price
				calculateNewCartTotal(cart);
				return cartRepo.save(cart);
			}
		}
	 }

	 // Clear cart
	 @Override
	 public Cart clearCart(Long customerId) {
		Cart cart = getOrCreateCart(customerId);
		cart.getItems().clear();
		cart.setTotalPrice(BigDecimal.ZERO);
		return cartRepo.save(cart);
	 }

}
