package com.greydev.messenger.exception;

public class DatabaseOperationException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DatabaseOperationException() {
		super("Failed Database Operation");
	}

	public DatabaseOperationException(String message) {
		super(message);
	}

	public DatabaseOperationException(Throwable cause) {
		super(cause);
	}

	public DatabaseOperationException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
