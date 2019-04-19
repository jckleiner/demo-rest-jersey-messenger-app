package com.greydev.messenger.exception;

public class DataNotFoundException extends AppException {

	private static final long serialVersionUID = -5618858306763402778L;
	private static final int statusCode = 404;
	private static final int errorCode = 999;
	private static final String errorDescription = "Requested data not found";
	private static final String errorDocumentationLink = "https://www.google.com";

	public DataNotFoundException() {
		this(null, null);
	}

	public DataNotFoundException(String requestMethod, String requestUri) {
		super(statusCode, errorCode, errorDescription, errorDocumentationLink, requestMethod, requestUri);
	}

}
