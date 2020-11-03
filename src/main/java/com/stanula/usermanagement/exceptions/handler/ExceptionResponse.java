package com.stanula.usermanagement.exceptions.handler;

import lombok.Getter;

import java.util.Date;

@Getter
public class ExceptionResponse {

	private Date timestamp;
	private String message;
	private String details;

	public ExceptionResponse(Date timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
}
