package com.greydev.messenger.exception;

public class InvalidRequestDataException extends AppException {

	private static final long serialVersionUID = 4798057332931206855L;
	private static final int statusCode = 400;
	private static final int errorCode = 888;
	private static final String errorDescription = "Request data sent is incomplete, contains invalid fields or already exists";
	private static final String errorDocumentationLink = "https://www.google.com";

	public InvalidRequestDataException() {
		this(null, null);
	}

	public InvalidRequestDataException(String requestMethod, String requestUri) {
		super(statusCode, errorCode, errorDescription, errorDocumentationLink, requestMethod, requestUri);
	}

}
