package com.sharad.serviceImpl;

import java.util.List;

import com.sharad.dto.CategoryRequestDto;
import com.sharad.dto.CategoryResponseDto;
import com.sharad.exception.CategoryNotFoundException;
import com.sharad.repository.ProductRepository;
import org.springframework.stereotype.Service;
import com.sharad.entity.ProductCategory;
import com.sharad.repository.ProductCategoryRepository;
import com.sharad.service.ProductCategoryService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository categoryRepo;
    private final ProductRepository productRepo;



    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {

        String name =categoryRequestDto.getCategoryName();
        if(categoryRepo.existsByCategoryNameIgnoreCase(name))
            throw new IllegalArgumentException(
                    "Category with name " + name + " already exists"
            );
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName(name);
        ProductCategory savedCategory = categoryRepo.save(productCategory);
         return mapToResponse(savedCategory);
    }

    @Override
    public List<ProductCategory> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public CategoryResponseDto getCategoryById(Long categoryId) {
         ProductCategory category=categoryRepo.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException(
                        "Category not found with id : " + categoryId
                        ));
         return mapToResponse(category);
    }

    @Override
    public CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto requestDto) {
        ProductCategory category=categoryRepo.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException(
                        "Category not found with id : " + categoryId
                ));
        String categoryName= requestDto.getCategoryName();
        category.setCategoryName(categoryName);
        ProductCategory savedCategory = categoryRepo.save(category);
        return mapToResponse(savedCategory);

    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        ProductCategory category= categoryRepo.findById(categoryId)
                .orElseThrow(()->new CategoryNotFoundException(
                        "Category not found with id : " + categoryId
                ));
        if(category.getProducts()!=null
                && !category.getProducts().isEmpty()){
            throw new IllegalArgumentException(
                    "Cannot delete this category because products"
                            +" are associated with it");

        }
    }


    // helper class
    private CategoryResponseDto mapToResponse(
            ProductCategory category) {

        CategoryResponseDto response = new CategoryResponseDto();

        response.setCategoryId(category.getCategoryId());
        response.setCategoryName(category.getCategoryName());

        return response;
    }
}
