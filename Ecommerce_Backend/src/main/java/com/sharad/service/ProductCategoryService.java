package com.sharad.service;

import java.util.List;

import com.sharad.dto.CategoryRequestDto;
import com.sharad.dto.CategoryResponseDto;
import com.sharad.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCategoryService {

    CategoryResponseDto createCategory(CategoryRequestDto requestDto);
    //Page<CategoryResponseDto> getAllCategories(Pageable pageable);
    CategoryResponseDto getCategoryById(Long categoryId);
    CategoryResponseDto updateCategory(Long categoryId,CategoryRequestDto requestDto);
    void deleteCategoryById(Long categoryId);
    List<ProductCategory> getAllCategories();


   /* ProductCategory createCategory(ProductCategory category);
    List<ProductCategory> getAllCategories();
    ProductCategory getCategoryById(Long id);*/
}
