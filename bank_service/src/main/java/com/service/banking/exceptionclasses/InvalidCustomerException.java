package com.service.banking.exceptionclasses;

@SuppressWarnings("serial")
public class InvalidCustomerException extends RuntimeException{
	
	public InvalidCustomerException(String exDesc) {
		super(exDesc);
	}

}
