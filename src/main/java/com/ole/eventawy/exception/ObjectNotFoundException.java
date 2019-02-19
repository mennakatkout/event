package com.ole.eventawy.exception;

public class ObjectNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Error error=Error.OBJECT_NOT_FOUND;
	public ObjectNotFoundException(String msg){
		super(msg);
	}
	
	public ObjectNotFoundException(String msg,Error error){
		super(msg);
		this.error=error;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
}
