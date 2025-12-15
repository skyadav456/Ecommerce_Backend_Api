package com.sharad.exception;

public class CustomerNotFoundException extends RuntimeException {
	public CustomerNotFoundException(String mssg) {
		super("Customer not found with ID: " + mssg);
	}

}
