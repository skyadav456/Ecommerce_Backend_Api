package com.sharad.service;

import java.util.List;
import com.sharad.entity.Customer;


public interface CustomerService {
	
	Customer registerCustomer(Customer customer);
	Customer loginCostomer(String email,String password);
	Customer getCustomerById(Long id);
	List<Customer> getAllCustomers();

}
