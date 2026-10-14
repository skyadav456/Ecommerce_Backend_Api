package com.sharad.dto;

import com.sharad.entity.ProductCategory;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {

    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name  can not exceed 100 character")
    private String name;

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Product title cannot exceed 200 characters.")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description cannot exceed 2000 characters.")
    private String description;

    @NotNull(message = "Unit price is required")
    @Positive(message = "should be positive")
    private BigDecimal unitPrice;

    private String imageUrl;

    @NotNull(message = "Stock quality is required")
    @Min(value=0 , message = "")
    private Integer unitsInStock;

    @NotNull(message = "Category id is required")
    @Min(value = 1, message = "Must be greater than zero")
    private Long categoryId;

    private Boolean active;
}
