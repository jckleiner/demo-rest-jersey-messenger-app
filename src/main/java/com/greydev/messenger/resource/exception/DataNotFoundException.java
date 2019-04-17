package com.greydev.messenger.resource.exception;

public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5618858306763402778L;
	
	public DataNotFoundException(String message) {
		super(message);
	}

}
