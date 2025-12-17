package com.sharad.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sharad.entity.Customer;
import com.sharad.exception.CustomerNotFoundException;
import com.sharad.exception.EmailAlreadyExistsException;
import com.sharad.repository.CustomerRepository;
import com.sharad.service.CustomerService;

@Service
public class CustomerServiceImpl  implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;

	
	@Override
	public Customer registerCustomer(Customer customer) {
		/*
		 * String normalizedEmail = email.toLowerCase();
		 */
		 if (customerRepository.existsByEmail(customer.getEmail().toLowerCase())) {     
	            throw new EmailAlreadyExistsException(customer.getEmail());
	        }
		return customerRepository.save(customer);
	}

	@Override
	public Customer loginCostomer(String email, String password) {
		Customer customer = customerRepository.findByEmail(email).orElseThrow(()-> 
													new CustomerNotFoundException("Invalid email or password"));
		if(!customer.getPassword().equals(password)) {
			throw new CustomerNotFoundException("Invalid email or password");
		} 
		return customer;
	}

	@Override
	public Customer getCustomerById(Long id) {
		Customer customerById = customerRepository.findById(id).orElseThrow(()-> 
										                 new CustomerNotFoundException("Customer not found with id "+id));
		return customerById;
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

}
