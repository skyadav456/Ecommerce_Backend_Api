package com.sharad.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sharad.entity.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}