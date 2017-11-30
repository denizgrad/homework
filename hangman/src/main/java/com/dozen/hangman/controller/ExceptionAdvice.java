package com.dozen.hangman.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dozen.hangman.utility.ApiErrorResponse;
import com.dozen.hangman.utility.exception.HangmanException;
/**
 * 
 * @author deniz.ozen
 *
 *	Exception catcher and wrapper
 */
@ControllerAdvice
public class ExceptionAdvice extends AController {

	@ExceptionHandler(HangmanException.class)
	public ResponseEntity<ApiErrorResponse> customExceptionHandler(HangmanException ex) {
		ApiErrorResponse response = new ApiErrorResponse();
		response.setCode(ex.getCode());
		response.setMessage(getMessage(ex.getMessageCode(), ex.getArgs()));
		return new ResponseEntity<ApiErrorResponse>(response, ex.getHttpStatus());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<ApiErrorResponse> validationHandler(MethodArgumentNotValidException ex) {
		ApiErrorResponse response = new ApiErrorResponse();
		response.setCode(-1);
		response.setMessage(fromBindingErrors(ex.getBindingResult()));
		return new ResponseEntity<ApiErrorResponse>(response, HttpStatus.BAD_REQUEST);
	}

	private String fromBindingErrors(BindingResult result) {
		StringBuffer message = new StringBuffer();
		for (Object object : result.getAllErrors()) {
			if (object instanceof FieldError) {
				FieldError fieldError = (FieldError) object;
				message.append(fieldError.getField());
				message.append(" ");
				message.append(fieldError.getDefaultMessage());
			}
		}
		return message.toString();
	}
}
