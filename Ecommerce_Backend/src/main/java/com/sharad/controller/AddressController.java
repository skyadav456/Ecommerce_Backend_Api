package com.sharad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sharad.entity.Address;
import com.sharad.service.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/addresses")
@CrossOrigin("*")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@PostMapping("/customer/{custId}")
	public Address addAddress( @Valid @RequestBody Address add, @PathVariable Long custId) {
		return addressService.addAddress(add, custId);
	}
	
	@GetMapping("/customer/{custId}")
	public List<Address>getAddressByCustomerId(@PathVariable Long custId){
		return addressService.getAddressByCustomerId(custId);
	}
	
	@GetMapping("/{addressId}")
	public Address getAddressById(@PathVariable Long addressId) {
		return addressService.getAddressById(addressId);
	}
	
	@GetMapping("/allAddresses")
	public List<Address> getAllAddresses(){
		return addressService.getAllAddresses();
	}

}
