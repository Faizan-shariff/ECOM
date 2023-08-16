package com.website.profile.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> resourceNotFoundException(ResourceNotFoundException ex,WebRequest request){
		ErrorDetails errordetails =  new ErrorDetails(new Date(),ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errordetails, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(MobileNumberAlreadyExistsException.class)
	public ResponseEntity<ErrorDetails> handleMobileNumberAlreadyExistsException(MobileNumberAlreadyExistsException ex,WebRequest request){
		ErrorDetails errordetails =  new ErrorDetails(new Date(),ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errordetails, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ErrorDetails> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex,WebRequest request){
		ErrorDetails errordetails =  new ErrorDetails(new Date(),ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errordetails, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleOtherException(Exception ex,WebRequest request){
		ErrorDetails errordetails =  new ErrorDetails(new Date(),ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errordetails, HttpStatus.BAD_REQUEST);
	}	

}
