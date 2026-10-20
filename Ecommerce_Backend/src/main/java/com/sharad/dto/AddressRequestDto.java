package com.sharad.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDto {

    @NotBlank(message = "House number is required")
    private String houseNo;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Zipcode is required")
    @Pattern(
            regexp = "^[A-Za-z0-9 -]{3,12}$",
            message = "Zipcode must contain 3 to 12 valid characters"
    )
    private String zipcode;

    @NotBlank(message = "Country is required")
    private String country;
}
