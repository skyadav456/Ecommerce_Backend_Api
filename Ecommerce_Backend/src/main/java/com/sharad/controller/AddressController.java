package com.sharad.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sharad.dto.AddressRequestDto;
import com.sharad.dto.AddressResponseDto;
import com.sharad.service.AddressService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<AddressResponseDto> addAddress(@Valid @RequestBody AddressRequestDto add,
                                                      @PathVariable Long customerId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.addAddress(add, customerId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AddressResponseDto>> getAddressByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(addressService.getAddressByCustomerId(customerId));
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto> getAddressById(@PathVariable Long addressId) {
        return ResponseEntity.ok(addressService.getAddressById(addressId));
    }

    @GetMapping("/allAddresses")
    public ResponseEntity<List<AddressResponseDto>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    @PutMapping("/customer/{customerId}/{addressId}")
    public ResponseEntity<AddressResponseDto> updateAddress(@PathVariable Long customerId,
                                                           @PathVariable Long addressId,
                                                           @Valid @RequestBody AddressRequestDto addressRequestDto) {
        AddressResponseDto updatedAddress = addressService.updateAddress(customerId, addressId, addressRequestDto);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/customer/{customerId}/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long customerId,
                                             @PathVariable Long addressId) {
        addressService.deleteAddress(customerId, addressId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddressById(@PathVariable Long addressId) {
        addressService.deleteAddressById(addressId);
        return ResponseEntity.noContent().build();
    }
}
