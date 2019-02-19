package com.ole.eventawy.exception;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
	
	   private HttpStatus status;
	   private Date date;
	   private String message;
	   private String debugMessage;
	   private List<ValidationError> subErrors;
	   private Error code;
	   
	   

		  public  ErrorResponse(HttpStatus status) {
		       this();
		       this.status = status;
		       this.date = new Date();
		   }

		  public   ErrorResponse(HttpStatus status, Throwable ex) {
		       this();
		       this.status = status;
		       this.message = "Unexpected error";
		       this.debugMessage = ex.getLocalizedMessage();
		       this.date = new Date();
		   }

		  public  ErrorResponse(HttpStatus status, String message, Throwable ex) {
		       this();
		       this.status = status;
		       this.message = message;
		       this.debugMessage = ex.getLocalizedMessage();
		       this.date = new Date();
		   }
		  public  ErrorResponse(HttpStatus status, String message, Error code,Throwable ex) {
		       this();
		       this.status = status;
		       this.message = message;
		       this.debugMessage = ex.getLocalizedMessage();
		       this.code=code;
		       this.date = new Date();
		   }

}
