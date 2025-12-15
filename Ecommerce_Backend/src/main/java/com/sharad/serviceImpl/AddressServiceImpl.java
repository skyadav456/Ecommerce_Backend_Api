package com.sharad.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sharad.entity.Address;
import com.sharad.entity.Customer;
import com.sharad.exception.AddressNotFoundException;
import com.sharad.repository.AddressRepository;
import com.sharad.repository.CustomerRepository;
import com.sharad.service.AddressService;


@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private AddressRepository addRepo;
	@Autowired
	private CustomerRepository custRepo;
	
	
	@Override
	public Address addAddress(Address add, Long custId) {
		Customer customer = custRepo.findById(custId).orElseThrow(()->new RuntimeException("Customer not found with ID: " + custId));
		add.setCustomer(customer);
		return addRepo.save(add);
	}
	@Override
	public List<Address> getAddressByCustomerId(Long custId) {
		return addRepo.findByCustomer_CustomerId(custId);
	}
	@Override
	public Address getAddressById(Long addressId) {
		return	addRepo.findById(addressId).orElseThrow(()->
												new AddressNotFoundException("Address not found with ID: " + addressId));
	}
	@Override
	public List<Address> getAllAddresses() {
		return addRepo.findAll();
	}

	
}
