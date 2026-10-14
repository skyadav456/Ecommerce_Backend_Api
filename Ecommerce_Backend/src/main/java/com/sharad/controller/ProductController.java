package com.sharad.controller;

import java.util.List;

import com.sharad.dto.ProductRequestDto;
import com.sharad.dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ProductResponseDto> addProduct(
            @Valid @RequestBody ProductRequestDto requestDto) {
        ProductResponseDto savedProduct = productService.addProduct(requestDto);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> productList = productService.getAllProducts();
        return new ResponseEntity<>(productList,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        ProductResponseDto productById = productService.getProductById(id);
        return new ResponseEntity<>(productById,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDto>> getByCategory(@PathVariable Long categoryId) {
        List<ProductResponseDto> productsByCategory = productService.getProductsByCategory(categoryId);
        return new ResponseEntity<>(productsByCategory,HttpStatus.OK);
    }
    
    @GetMapping("/page")
    public Page<ProductResponseDto> getProductsWithPagination(
                                    @RequestParam(defaultValue = "0") int pageNumber,
                                    @RequestParam(defaultValue = "5") int pageSize,
                                    @RequestParam(defaultValue = "dateCreated") String sortBy,
                                    @RequestParam(defaultValue = "desc") String sortDirection) {
        Page<ProductResponseDto> productsWithPagination = productService.getProductsWithPagination(
                        pageNumber, pageSize, sortBy, sortDirection);
        return new ResponseEntity<>(productsWithPagination, HttpStatus.OK).getBody();
	}
    
	/*Search Products by Name*/
    @GetMapping("/search")      //   /search?keyword=iphone
    public ResponseEntity<List<ProductResponseDto>>getProductByName(@RequestParam String name){
        List<ProductResponseDto> product = productService.getProductByName(name);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> deleteProductById(@PathVariable Long productId){
        ProductResponseDto deletedProduct = productService.deleteProductById(productId);
        return new ResponseEntity<>(deletedProduct,HttpStatus.OK);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long productId,
                                                            @RequestBody ProductRequestDto productRequestDto){
        ProductResponseDto updatedProduct = productService.updateProduct(productId,productRequestDto);
        return new  ResponseEntity<>(updatedProduct,HttpStatus.OK);
    }
}

