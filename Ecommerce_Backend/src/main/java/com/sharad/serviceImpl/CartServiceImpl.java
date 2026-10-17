package com.sharad.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.sharad.dto.CartItemResponseDto;
import com.sharad.dto.CartResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class CartServiceImpl implements CartService {



	private final CartRepository cartRepo;
	private final CartItemRepository cartItemRepo;
	private final CustomerRepository customerRepository;
	private final ProductRepository productRepo;

    public CartServiceImpl(CartRepository cartRepo, CartItemRepository cartItemRepo,
						   CustomerRepository customerRepository,
						   ProductRepository productRepo) {
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
        this.customerRepository = customerRepository;
        this.productRepo = productRepo;
    }


	@Transactional
    @Override
	public Cart getOrCreateCart(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(()-> new CustomerNotFoundException(
						"Customer not found"+customerId));

		// Check for active cart if not found create new cart
		Cart existingCart = cartRepo.findByCustomer_CustomerId(customerId).orElse(null);
		if(existingCart!=null) {
			return existingCart;
		}
			Cart newCart = new Cart();
			newCart.setCustomer(customer);
			//newCart.setItems(new  ArrayList<>());
			newCart.setTotalPrice(BigDecimal.ZERO);
			newCart.setStatus("ACTIVE");
            return cartRepo.save(newCart);

	}



	@Override
	public CartResponseDto addProductToCart(Long customerId, Long productId, int quantity) {
		// check quantity >0
		if(quantity <=0 ) {
			throw new IllegalArgumentException(
					"Quantity must be greater than zero");
		}

		// get the product
		Product product =productRepo.findById(productId)
				.orElseThrow(()-> new ProductNotFoundException(
						"Product not found with id:"+productId));
		// get the cart
		Cart cart = getOrCreateCart(customerId);
		if (cart.getItems() == null) {
			cart.setItems(new ArrayList<>());
		}
		// getting existing cart item
		Optional<CartItem> existCartItem = cartItemRepo.findByCart_CartIdAndProduct_ProductId(
				cart.getCartId(), product.getProductId());

		if (existCartItem.isPresent()) {
			// Product already in cart → increase quantity and subtotal
			CartItem item = existCartItem.get();
			int newQuantity = item.getQuantity() + quantity;
			item.setQuantity(newQuantity);
			item.setSubtotal(item.getUnitPrice().multiply(BigDecimal.valueOf(newQuantity)));
			// persist updated cart item
			cartItemRepo.save(item);
			// ensure the item is in the cart items collection
			if (!cart.getItems().contains(item)) {
				cart.getItems().add(item);
			}
		} else {
			// Product not in cart → New CartItem
			CartItem newItem = new CartItem();
			newItem.setCart(cart);
			newItem.setProduct(product);
			newItem.setQuantity(quantity);
			newItem.setUnitPrice(product.getUnitPrice());
			newItem.setSubtotal(product.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
			// persist new cart item
			CartItem savedItem = cartItemRepo.save(newItem);
			// add to cart's items collection (keep both sides in sync)
			cart.getItems().add(savedItem);
		}
		// Update cart total price
		calculateNewCartTotal(cart);
		// Save cart and return
		Cart savedCart = cartRepo.save(cart);
		return mapToCartResponse(savedCart);
	}

	// Remove product from cart
	@Override
	public CartResponseDto removeProductFromCart(Long customerId, Long productId) {
		Cart cart = getOrCreateCart(customerId);
		CartItem cartItem = cart.getItems().stream().
				filter(item -> item.getProduct()
						.getProductId().equals(productId))
				.findFirst()
				.orElseThrow(() -> new ProductNotFoundException(
						"product not found in cart with id:" + productId));
		cart.getItems().remove(cartItem);
		/* here you can use custom query  method also or below code
		* cart.getItems().removeIf(item->item.getProduct().getProductId().equals(productId));
		* */

		// Update cart total price
		calculateNewCartTotal(cart);
		// Save cart and return
		return mapToCartResponse(cartRepo.save(cart));
	}

	@Override
	public CartResponseDto viewCart(Long customerId) {
		Cart cart = getOrCreateCart(customerId);
		if(cart!=null) {
			return mapToCartResponse(cart);
		}
		throw new CustomerNotFoundException("Cart not found for customer id: "+customerId);
	}


	 //  Update product quantity in cart
	 @Override
	 public CartResponseDto updateProductQuantity(Long customerId, Long productId, int quantity) {

		Cart cart = getOrCreateCart(customerId);
		Optional<CartItem> item = cart.getItems().stream()
				.filter(items->items.getProduct()
						.getProductId().equals(productId))
				.findFirst() ;
		if(item.isEmpty()) {
			throw new ProductNotFoundException("product "+ productId+ " not present in cart ");
		}else {
			if(quantity<=0) {
				throw new IllegalArgumentException("Quantity can not be negative or zero");
			}else {
				CartItem cartItem = item.get();
				cartItem.setQuantity(quantity);
				cartItem.setSubtotal(cartItem.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
				// Update cart total price
				calculateNewCartTotal(cart);

				return mapToCartResponse(cartRepo.save(cart));
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

	// Helper method

	// Update cart total price
	private void calculateNewCartTotal(Cart cart) {
		BigDecimal totalPrice = cart.getItems().stream()
				.map(CartItem::getSubtotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);     // add each subtotal
		cart.setTotalPrice(totalPrice);

	}
	private CartResponseDto mapToCartResponse(Cart cart) {
		List<CartItemResponseDto> itemResponse = cart.getItems().stream()
				.map(item -> CartItemResponseDto.builder()
						.cartItemId(item.getCartItemId())
						.productId(item.getProduct().getProductId())
						.productName(item.getProduct().getName())
						.quantity(item.getQuantity())
						.unitPrice(item.getUnitPrice())
						.subtotal(item.getSubtotal())
						.build()
				).toList();

		return CartResponseDto.builder()
				.cartId(cart.getCartId())
				.customerId(cart.getCustomer().getCustomerId())
				.status(cart.getStatus())
				.items(itemResponse)
				.totalPrice(cart.getTotalPrice()!=null ? cart.getTotalPrice():BigDecimal.ZERO)
				.build();

	}

}
