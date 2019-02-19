package com.ole.eventawy.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ValidationError {
	

	   
	   
	   private String field;
	   private String message;
	   private Object rejectedValue;
	  

}
