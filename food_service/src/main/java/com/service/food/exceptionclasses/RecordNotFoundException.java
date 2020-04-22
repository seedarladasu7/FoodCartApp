package com.service.food.exceptionclasses;

@SuppressWarnings("serial")
public class RecordNotFoundException extends RuntimeException{
	
	public RecordNotFoundException(String exDesc) {
		super(exDesc);
	}

}
