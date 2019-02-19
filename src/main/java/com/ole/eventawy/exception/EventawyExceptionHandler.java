package com.ole.eventawy.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
* This class is an advisor controller used to handle different types of {@link Exception}
* @author  Reda ElSayed
* @version 1.0
*
*/

@ControllerAdvice
public class EventawyExceptionHandler {
	 private static final Logger LOGGER = LoggerFactory.getLogger(EventawyExceptionHandler.class);
	
	 
	 /**
		 * This method used to handle any  exception of type {@link EventawyException}
		 * 
		 * @param ex  throwed exception
		 * @return ResponseEntity
		 * @see ResponseEntity
		 **/
	@ResponseBody
	@ExceptionHandler(value = { EventawyException.class })

	   protected ResponseEntity<Object> handleEventawyException (EventawyException ex){
			LOGGER.debug(ex.getMessage(), ex);
		
	       String error = "Error in processing your request : "+ex.getMessage();
	       ErrorResponse errorResponse= new ErrorResponse(HttpStatus.BAD_REQUEST, error, ex);
	       
	       errorResponse.setCode(ex.getError());
	       
	       return buildResponseEntity(errorResponse);
	   }
	
	
	/**
	 * This method used to handle any  exception of type {@link ObjectNotFoundException}
	 * 
	 * @param ex  throwed exception
	 * @return ResponseEntity
	 * @see ResponseEntity
	 **/
   @ResponseBody
   @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handleObjectNotFoundException(ObjectNotFoundException ex) {
	   LOGGER.debug(ex.getMessage(), ex);
        ErrorResponse response = new ErrorResponse();
        
        response.setStatus(HttpStatus.NOT_FOUND);
        response.setCode(ex.getError());
        response.setMessage(ex.getMessage());
       
        return buildResponseEntity(response);
    }
   
   
   /**
	 * This method used to handle any  exception of type {@link Exception}
	 * 
	 * @param ex  throwed exception
	 * @return ResponseEntity
	 * @see ResponseEntity
	 **/
	
	@ResponseBody
	@ExceptionHandler(Exception.class)
	   protected ResponseEntity<Object> handleGeneralError (Exception ex){
			LOGGER.debug(ex.getMessage(), ex);
		
	       String error = "Error in processing your request : "+ex.getMessage();
	       ErrorResponse errorResponse= new ErrorResponse(HttpStatus.BAD_REQUEST, error, ex);
	       
	       errorResponse.setCode(Error.GENERAL_ERROR);
	       
	       return buildResponseEntity(errorResponse);
	   }

	
	   /**
		 * This method used to handle any exception of type {@link MethodArgumentNotValidException} 
		 * 
		 * @param ex  throwed exception
		 * @return ResponseEntity
		 * @see ResponseEntity
		 **/
	   @ResponseBody
	   @ExceptionHandler(MethodArgumentNotValidException .class)
	    public ResponseEntity<Object> handleMethodArgumentNotValidException (MethodArgumentNotValidException  ex) {
		   LOGGER.debug(ex.getMessage(), ex);
		   List<ValidationError> validationsError= new ArrayList<ValidationError>(); 
		   
		   
		   // Get the validation Errors from the Binding fields
		   BindingResult result = ex.getBindingResult();
	       List<FieldError> fieldErrors = result.getFieldErrors();
	       
	       // iterate over fields error to fill our ValidationError list
	       fieldErrors.stream().forEach(field ->{
	    	   ValidationError  error =new ValidationError(field.getField(),field.getDefaultMessage(),field.getRejectedValue());
	    	   validationsError.add(error);	    	   
	       });
	        
	        ErrorResponse response = new ErrorResponse();
	        response.setStatus(HttpStatus.BAD_REQUEST);
	        response.setCode(Error.ARGUMENT_NOT_VALID);
	        response.setMessage("Method Argument Not Valid");
	        response.setSubErrors(validationsError);
	        return buildResponseEntity(response);
	    }
	   
	   /**
			 * This method used to handle any exception of type {@link HttpMessageNotReadableException} 
			 * 
			 * @param ex  throwed exception
			 * @return ResponseEntity
			 * @see ResponseEntity
			 **/
		@ResponseBody
		@ExceptionHandler(HttpMessageNotReadableException.class)
		   protected ResponseEntity<Object> handleHttpMessageNotReadableException (HttpMessageNotReadableException ex){
				LOGGER.debug(ex.getMessage(), ex);
			
		       String error = "Your submitted paremeters didn't match the needed parameters";
		       ErrorResponse response = new ErrorResponse();
		        
		        response.setStatus(HttpStatus.BAD_REQUEST);
		        response.setCode(Error.ARG_MISMATCH);
		        response.setMessage(error);
		       
		       return buildResponseEntity(response);
		   }
	   

	
	
	 private ResponseEntity<Object> buildResponseEntity(ErrorResponse apiError) {
		 apiError.setDate(new Date());
	       return new ResponseEntity<>(apiError, apiError.getStatus());
	   }
}
