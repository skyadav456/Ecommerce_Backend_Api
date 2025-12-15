package com.sharad.serviceImpl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.sharad.entity.ProductCategory;
import com.sharad.repository.ProductCategoryRepository;
import com.sharad.service.ProductCategoryService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository categoryRepo;

    @Override
    public ProductCategory createCategory(ProductCategory category) {
        return categoryRepo.save(category);
    }

    @Override
    public List<ProductCategory> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public ProductCategory getCategoryById(Long id) {
        return categoryRepo.findById(id).orElse(null);
    }
}
