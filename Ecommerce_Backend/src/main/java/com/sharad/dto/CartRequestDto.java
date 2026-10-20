package com.sharad.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequestDto {

    @NotNull(message = "Customer id required")
    private Long customerId;
    @NotNull(message ="Product id required" )
    private Long productId;
    @Min(value=1, message = "quantity must be at least 1")
    private Integer quantity;
}
