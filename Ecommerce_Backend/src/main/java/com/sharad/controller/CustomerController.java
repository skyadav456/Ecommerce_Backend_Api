package com.sharad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sharad.entity.Customer;
import com.sharad.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin("*")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/register")
	public Customer registerCustomer(@Valid @RequestBody  Customer customer) {
		return customerService.registerCustomer(customer);
	}
	
	@PostMapping("/login")
	public Customer loginCustomer( @RequestParam  String email, @RequestParam String password) {
		return customerService.loginCostomer(email, password);
	}
	
	@GetMapping("/{id}")
	public Customer getCustomerById(@PathVariable Long id) {
		return customerService.getCustomerById(id);	
	}
	
	@GetMapping("getAllCustomers")
	public List<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}

}
