package com.sharad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {
    private Long productId;
    private String name;
    private String title;
    private String description;
    private BigDecimal unitPrice;
    private String imageUrl;
    private Boolean active;
    private Integer unitsInStock;

    private Long categoryId;
    private String categoryName;

    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
}
