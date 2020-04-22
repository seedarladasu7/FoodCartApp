package com.service.food.exceptionclasses;

@SuppressWarnings("serial")
public class RecordInsertionException extends RuntimeException{
	
	public RecordInsertionException(String exDesc) {
		super(exDesc);
	}

}
