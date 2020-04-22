package com.service.food.exceptionclasses;

@SuppressWarnings("serial")
public class InvalidInputException extends RuntimeException{
	
	public InvalidInputException(String exDesc) {
		super(exDesc);
	}

}
