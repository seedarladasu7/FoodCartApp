package com.service.banking.exceptionclasses;

@SuppressWarnings("serial")
public class InvalidUserException extends RuntimeException{
	
	public InvalidUserException(String exDesc) {
		super(exDesc);
	}

}
