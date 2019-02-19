package com.ole.eventawy.exception;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor

public class EventawyException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Error error =Error.GENERAL_ERROR;
	
	public  EventawyException(Error error ,String message) {
		super(message);
		this.error=error;
	}
	
	public  EventawyException(String message) {
		super(message);
		
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
	
	
	

}
