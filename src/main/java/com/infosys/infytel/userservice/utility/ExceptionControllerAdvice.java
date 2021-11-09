package com.infosys.infytel.userservice.utility;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionControllerAdvice {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	Environment environment;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> exceptionHandler(Exception exception) {
		logger.info("error message {}", exception);
		ErrorInfo error = new ErrorInfo();
		error.setErrorMessage(exception.getMessage());
		error.setErrorCode(HttpStatus.NOT_FOUND.value());
		error.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

//	@ExceptionHandler(InfyBankException.class)
//	public ResponseEntity<ErrorInfo> infyBankexceptionHandler(InfyBankException exception) {
//		ErrorInfo error = new ErrorInfo();
//		logger.info("error message {}", exception);
//		error.setErrorMessage(exception.getMessage());
//		error.setTimestamp(LocalDateTime.now());
//		error.setErrorCode(HttpStatus.NOT_FOUND.value());
//		return new ResponseEntity<ErrorInfo>(error, HttpStatus.NOT_FOUND);
//	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorInfo> exceptionHandler(MethodArgumentNotValidException exception) {

		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());

		
		String errorMsg = exception.getBindingResult().getAllErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.joining(", "));

		errorInfo.setErrorMessage(errorMsg);
		errorInfo.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}

	

		@ExceptionHandler(ConstraintViolationException.class)
		public ResponseEntity<ErrorInfo> pathExceptionHandler(ConstraintViolationException exception) {

			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());

			String errorMsg = exception.getConstraintViolations().stream().map(x -> x.getMessage())
					.collect(Collectors.joining(", "));
			errorInfo.setErrorMessage(errorMsg);
			errorInfo.setTimestamp(LocalDateTime.now());
			return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
		}
	
}

	

