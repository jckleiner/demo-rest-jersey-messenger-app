package com.greydev.messenger.resource.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {
	
	private String errorMessage;
	private String errorDescription;
	private int errorCode;
	
	public ErrorMessage() {
	}
	
	public ErrorMessage( int errorCode, String errorMessage, String errorDescription) {
		this.errorMessage = errorMessage;
		this.errorDescription = errorDescription;
		this.errorCode = errorCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	

}
