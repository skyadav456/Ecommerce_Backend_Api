package com.sharad.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.sharad.entity.Product;

public interface ProductService {

	Product addProduct(Product product);
	List<Product> getAllProducts();
	Product getProductById(Long id);
	List<Product> getProductsByCategory(Long categoryId);
	Page<Product> getProductswithPagination(int pageNumber, int pageSize);
	List<Product>getProductByName(String name);

}
