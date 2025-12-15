package com.sharad.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sharad.entity.Product;
import com.sharad.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {

	
    private final ProductService productService;

    @PostMapping
    public Product addProduct(@Valid @RequestBody Product product) {
        return productService.addProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getByCategory(@PathVariable Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }
    
    @GetMapping("/page")
    public Page<Product> getProductswithPagination(@RequestParam(defaultValue = "0") int pageNumber,
    																					@RequestParam(defaultValue = "5") int pageSize) {
		return productService.getProductswithPagination(pageNumber, pageSize);
	}
    
	/*Search Products by Name*/
    @GetMapping("/search")      //   /search?keyword=iphone
    public List<Product>getProductByName(@RequestParam String name){
    	return productService.getProductByName(name);
    }
}

