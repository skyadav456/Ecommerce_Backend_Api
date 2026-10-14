package com.sharad.service;

import java.util.List;

import com.sharad.dto.ProductRequestDto;
import com.sharad.dto.ProductResponseDto;
import org.springframework.data.domain.Page;

import com.sharad.entity.Product;

public interface ProductService {

	ProductResponseDto addProduct(ProductRequestDto product);
	List<ProductResponseDto> getAllProducts();
	ProductResponseDto getProductById(Long id);
	List<ProductResponseDto> getProductsByCategory(Long categoryId);
	// Page<ProductResponseDto> getProductswithPagination(int pageNumber, int pageSize);

	Page<ProductResponseDto> getProductsWithPagination(int pageNumber,
	                                                   int pageSize,
	                                                   String sortBy, String sortDirection);

	List<ProductResponseDto>getProductByName(String name);
	ProductResponseDto deleteProductById(Long productId);
	ProductResponseDto updateProduct(Long productId,ProductRequestDto product);


}
