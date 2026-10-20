package com.sharad.service;

import java.util.List;

import com.sharad.dto.AddressRequestDto;
import com.sharad.dto.AddressResponseDto;

public interface AddressService {

    AddressResponseDto addAddress(AddressRequestDto add, Long customerId);
    List<AddressResponseDto> getAddressByCustomerId(Long customerId);
    AddressResponseDto getAddressById(Long addressId);
    List<AddressResponseDto> getAllAddresses();
    AddressResponseDto updateAddress(Long customerId, Long addressId, AddressRequestDto request);
    void deleteAddress(Long customerId, Long addressId);
    void deleteAddressById(Long addressId);
}
