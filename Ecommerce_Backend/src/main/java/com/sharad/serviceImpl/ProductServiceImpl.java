package com.sharad.serviceImpl;

import java.util.List;
import java.util.Set;

import com.sharad.dto.ProductRequestDto;
import com.sharad.dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sharad.entity.Product;
import com.sharad.entity.ProductCategory;
import com.sharad.exception.CategoryNotFoundException;
import com.sharad.exception.ProductNotFoundException;
import com.sharad.repository.ProductCategoryRepository;
import com.sharad.repository.ProductRepository;
import com.sharad.repository.CartItemRepository;
import com.sharad.service.ProductService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;
    private final ProductCategoryRepository categoryRepo;
    private final CartItemRepository cartItemRepo;

    @Override
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) {

        Long categoryId = productRequestDto.getCategoryId();
        ProductCategory category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(
                        "Category Not Found with id : "+categoryId));

        Product product = new Product();
        mapRequestToEntity(productRequestDto, product, category);
        if(productRequestDto.getActive()==null) {
            product.setActive(true);
        }
        Product savedProduct = productRepo.save(product);
        return mapToResponseDto(savedProduct);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepo.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepo.findById(id).orElseThrow(() ->
                new ProductNotFoundException(
                        "Product Not Found with id : " + id));
        return mapToResponseDto(product);

    }

    @Override
    public List<ProductResponseDto> getProductsByCategory(Long categoryId) {
        ProductCategory category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(
                        "Category Not Found with id : " + categoryId));

        List<Product> productList = productRepo.findByCategory_CategoryId(categoryId);
        return productList.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

	@Override
    public Page<ProductResponseDto> getProductsWithPagination(int pageNumber,
                                                              int pageSize,
                                                              String sortBy, String sortDirection) {

		if (pageNumber < 0) {
			throw new IllegalArgumentException("pageNumber must be >= 0");
		}
		if (pageSize <= 0 ||pageSize > 100) {
			throw new IllegalArgumentException("pageSize must be between 1 and 100");
		}
        // To ensure the security and prevent the sql error
        Set<String> allowedSortField=  Set.of("productId", "name","unitPrice","unitsInStock", "dateCreated");
        if (sortBy == null || !allowedSortField.contains(sortBy)) {
            sortBy = "dateCreated";
        }
        Sort sortOrder = "desc".equalsIgnoreCase(sortDirection)
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sortOrder);
		Page<Product> page = productRepo.findAll(pageable);
		return page.map(this::mapToResponseDto);
		
	}

	@Override
	public List<ProductResponseDto> getProductByName(String name) {
		List<Product> byName = productRepo.findByNameContainingIgnoreCase(name);
		return  byName.stream()
                .map(this::mapToResponseDto)
                .toList();
	}

    @Override
    public ProductResponseDto deleteProductById(Long productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product Not Found with id : " + productId
                ));
        // create response snapshot before deletion
        ProductResponseDto response = mapToResponseDto(product);
        // remove any cart items referencing this product to avoid FK constraint violations
        List<com.sharad.entity.CartItem> cartItems = cartItemRepo.findByProduct(product);
        if (cartItems != null && !cartItems.isEmpty()) {
            cartItemRepo.deleteAll(cartItems);
        }
        productRepo.delete(product);
        return response;
    }

    @Override
    public ProductResponseDto updateProduct(Long productId,ProductRequestDto productRequestDto) {
       Product productFromDb=productRepo.findById(productId)
               .orElseThrow(() -> new ProductNotFoundException(
                       "product Not found this is " + productId
               ));
       ProductCategory category=categoryRepo.findById(productRequestDto.getCategoryId())
               .orElseThrow(()-> new  CategoryNotFoundException(
                       "category Not Found with id : " + productRequestDto.getCategoryId()
               ));
       mapRequestToEntity(productRequestDto, productFromDb, category);
       Product updatedProduct = productRepo.save(productFromDb);
       return mapToResponseDto(updatedProduct);

    }


    //helper method

    public void mapRequestToEntity(ProductRequestDto productRequestDto,
                                   Product product,
                                   ProductCategory productCategory) {
        product.setName(productRequestDto.getName());
        product.setCategory(productCategory);
        product.setTitle(productRequestDto.getTitle());
        product.setDescription(productRequestDto.getDescription());
        product.setImageUrl(productRequestDto.getImageUrl());
        product.setUnitPrice(productRequestDto.getUnitPrice());
        product.setUnitsInStock(productRequestDto.getUnitsInStock());
        // only update active when provided in the request (keeps addProduct default behavior)
        if (productRequestDto.getActive() != null) {
            product.setActive(productRequestDto.getActive());
        }
    }

    public ProductResponseDto mapToResponseDto(Product product) {
        ProductCategory category = product.getCategory();
        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .title(product.getTitle())
                .active(product.getActive())
                .description(product.getDescription())
                .unitPrice(product.getUnitPrice())
                .imageUrl(product.getImageUrl())
                .unitsInStock(product.getUnitsInStock())
                .categoryId(category!=null ?category.getCategoryId():null)
                .categoryName(category!=null ? category.getCategoryName(): null)
                .dateCreated(product.getDateCreated())
                .lastUpdated(product.getLastUpdated())
                .build();

    }
}

