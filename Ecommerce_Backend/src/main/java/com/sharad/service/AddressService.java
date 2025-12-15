package com.sharad.service;

import java.util.List;
import com.sharad.entity.Address;

public interface AddressService {
	
	Address addAddress(Address add,Long custId);
	List<Address>getAddressByCustomerId(Long custId);
	Address getAddressById(Long addressId);
	List<Address> getAllAddresses();

}
