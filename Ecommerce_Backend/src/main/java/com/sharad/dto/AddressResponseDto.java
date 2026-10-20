package com.sharad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponseDto {

    private Long addressId;
    private String houseNo;
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;
    private Long customerId;
}
