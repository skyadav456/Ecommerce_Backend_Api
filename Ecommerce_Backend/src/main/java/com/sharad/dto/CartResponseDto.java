package com.sharad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponseDto {
    private Long cartId;
    private Long customerId;
    private String status;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private List<CartItemResponseDto> items = new ArrayList<>();
}
