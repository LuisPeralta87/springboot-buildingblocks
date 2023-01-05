package com.stacksimplify.restservices.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	//MethodArgument Not valid exception
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		CustomErrorDetails customerror = new CustomErrorDetails(new Date(), 
				"From MethodArgumentNotValid Exception in GEH",
				ex.getMessage());
		return new ResponseEntity<>(customerror, HttpStatus.BAD_REQUEST);
	}
	
	//HttpRequestMethodNotSupportedException
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		CustomErrorDetails customerror = new CustomErrorDetails(new Date(),
		"From HttpRequestMethodNotSupportedException in GEH, Method Not Allowed",
		ex.getMessage());
		return new ResponseEntity<>(customerror, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	//UserNameNotFoundException
	@ExceptionHandler(UserNameNotFoundException.class)
	public final ResponseEntity<Object> handleUserNameNotFoundException(
			UserNameNotFoundException ex, WebRequest request){
		
		CustomErrorDetails customerror = new CustomErrorDetails(new Date(),
		ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(customerror, HttpStatus.NOT_FOUND);
	}
	
	//ConstraintViolationException
	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<Object> handleConstarintViolationException(
			ConstraintViolationException ex, WebRequest request){
		
		CustomErrorDetails customerror = new CustomErrorDetails(new Date(),
		ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(customerror, HttpStatus.BAD_REQUEST);
	}
	
}
