package com.stockmarket.user.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomizedExceptionHandling extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleExceptions(UserNotFoundException exception, WebRequest webRequest) {
		ExceptionResponse response = new ExceptionResponse();
		response.setDateTime(LocalDateTime.now());
		response.setMessage("User Not found");
		ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		return entity;
	}

	@ExceptionHandler(DataInvalidException.class)
	public ResponseEntity<Object> inputExceptions(DataInvalidException exception, WebRequest webRequest) {
		ExceptionResponse response = new ExceptionResponse();
		response.setDateTime(LocalDateTime.now());
		response.setMessage("Credentials not available");
		ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		return entity;
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<Object> inputExceptions(UserAlreadyExistsException exception, WebRequest webRequest) {
		ExceptionResponse response = new ExceptionResponse();
		response.setDateTime(LocalDateTime.now());
		response.setMessage("User already exists");
		ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		return entity;
	}
}