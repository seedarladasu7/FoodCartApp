package com.service.food.exceptionclasses;

@SuppressWarnings("serial")
public class InvalidUserException extends RuntimeException{
	
	public InvalidUserException(String exDesc) {
		super(exDesc);
	}

}
