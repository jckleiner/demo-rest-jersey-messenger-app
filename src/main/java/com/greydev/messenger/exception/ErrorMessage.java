package com.greydev.messenger.exception;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {

	private int statusCode;
	private int errorCode;
	private String errorDescription;
	private String errorDocumentationLink;
	private String requestUrl;
	private String requestMedhod;

	public ErrorMessage() {
	}
	
	public ErrorMessage(AppException ex) {
		this.statusCode = ex.getStatusCode();
		this.errorCode = ex.getErrorCode();
		this.errorDescription = ex.getErrorDescription();
		this.errorDocumentationLink = ex.getErrorDocumentationLink();
		this.requestUrl = ex.getRequestUrl();
		this.requestMedhod = ex.getRequestMedhod();
	}
	
	public ErrorMessage(int statusCode, int errorCode, String errorDescription, String errorDocumentationLink) {
		this.statusCode = statusCode;
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
		this.errorDocumentationLink = errorDocumentationLink;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getErrorDocumentationLink() {
		return errorDocumentationLink;
	}

	public void setErrorDocumentationLink(String errorDocumentationLink) {
		this.errorDocumentationLink = errorDocumentationLink;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRequestMedhod() {
		return requestMedhod;
	}

	public void setRequestMedhod(String requestMedhod) {
		this.requestMedhod = requestMedhod;
	}
	
}
