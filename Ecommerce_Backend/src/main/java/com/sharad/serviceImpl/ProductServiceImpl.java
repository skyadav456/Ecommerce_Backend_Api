package com.sharad.serviceImpl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sharad.entity.Product;
import com.sharad.entity.ProductCategory;
import com.sharad.exception.CategoryNotFoundException;
import com.sharad.exception.ProductNotFoundException;
import com.sharad.repository.ProductCategoryRepository;
import com.sharad.repository.ProductRepository;
import com.sharad.service.ProductService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;
    private final ProductCategoryRepository categoryRepo;

    @Override
    public Product addProduct(Product product) {

        Long categoryId = product.getCategory().getCategoryId();
        ProductCategory category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        product.setCategory(category);
        return productRepo.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepo.findByCategory_CategoryId(categoryId);
    }

	@Override
	public Page<Product> getProductswithPagination(int pageNumber, int pageSize) {
		return productRepo.findAll(PageRequest.of(pageNumber, pageSize));
		
	}

	@Override
	public List<Product> getProductByName(String name) {
		List<Product> byName = productRepo.findByNameContainingIgnoreCase(name);
		return  byName;
	}
}

