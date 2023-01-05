package com.stacksimplify.restservices.exceptions;

import java.util.Date;

//Create simple custom error details bean
public class CustomErrorDetails {

	private Date timestamp;
	private String message;
	private String errordetails;
	
	//Fields Constructor
	public CustomErrorDetails(Date timestamp, String message, String errordetails) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.errordetails = errordetails;
	}

	//Getters
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}
	
	
}