package com.sharad.serviceImpl;

import java.util.List;

import com.sharad.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import com.sharad.dto.AddressRequestDto;
import com.sharad.dto.AddressResponseDto;
import com.sharad.entity.Address;
import com.sharad.entity.Customer;
import com.sharad.exception.AddressNotFoundException;
import com.sharad.repository.AddressRepository;
import com.sharad.repository.CustomerRepository;
import com.sharad.service.AddressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addRepo;
    private final CustomerRepository customerRepository;

    @Override
    public AddressResponseDto addAddress(AddressRequestDto add, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

        Address address = mapRequestToEntity(add);
        address.setCustomer(customer);

        Address savedAddress = addRepo.save(address);
        return mapToResponseDto(savedAddress);
    }

    @Override
    public List<AddressResponseDto> getAddressByCustomerId(Long customerId) {
        return addRepo.findByCustomer_CustomerId(customerId)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public AddressResponseDto getAddressById(Long addressId) {
        Address address = addRepo.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException(
                        "Address not found with ID: " + addressId));
        return mapToResponseDto(address);
    }

    @Override
    public List<AddressResponseDto> getAllAddresses() {
        return addRepo.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public AddressResponseDto updateAddress(Long customerId, Long addressId, AddressRequestDto request) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer Not found with ID: " + customerId));

        Address customerAddress = addRepo.findById(addressId)
                .filter(address ->
                        address.getCustomer() != null
                                && address.getCustomer().getCustomerId().equals(customerId))
                .orElseThrow(() -> new AddressNotFoundException(
                        "Address not found with ID: " + addressId + " for customer id: " + customerId));

        customerAddress.setHouseNo(request.getHouseNo());
        customerAddress.setStreet(request.getStreet());
        customerAddress.setCity(request.getCity());
        customerAddress.setState(request.getState());
        customerAddress.setZipcode(request.getZipcode());
        customerAddress.setCountry(request.getCountry());

        Address savedAddress = addRepo.save(customerAddress);
        return mapToResponseDto(savedAddress);
    }

    @Override
    public void deleteAddress(Long customerId, Long addressId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer Not found with ID: " + customerId));

        Address customerAddress = addRepo.findById(addressId)
                .filter(address ->
                        address.getCustomer() != null
                                && address.getCustomer().getCustomerId().equals(customerId))
                .orElseThrow(() -> new AddressNotFoundException(
                        "Address not found with ID: " + addressId + " for customer id: " + customerId));

        addRepo.delete(customerAddress);
    }

    @Override
    public void deleteAddressById(Long addressId) {
        Address address = addRepo.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException(
                        "Address not found with ID: " + addressId));
        addRepo.delete(address);
    }

    private Address mapRequestToEntity(AddressRequestDto requestDto) {
        Address address = new Address();
        address.setHouseNo(requestDto.getHouseNo());
        address.setStreet(requestDto.getStreet());
        address.setCity(requestDto.getCity());
        address.setState(requestDto.getState());
        address.setZipcode(requestDto.getZipcode());
        address.setCountry(requestDto.getCountry());
        return address;
    }

    private AddressResponseDto mapToResponseDto(Address address) {
        return AddressResponseDto.builder()
                .addressId(address.getAddressId())
                .houseNo(address.getHouseNo())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipcode(address.getZipcode())
                .country(address.getCountry())
                .customerId(address.getCustomer() != null ? address.getCustomer().getCustomerId() : null)
                .build();
    }
}
