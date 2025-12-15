package com.sharad.service;

import java.util.List;

import com.sharad.entity.ProductCategory;

public interface ProductCategoryService {

    ProductCategory createCategory(ProductCategory category);
    List<ProductCategory> getAllCategories();
    ProductCategory getCategoryById(Long id);
}
