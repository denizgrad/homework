package com.dozen.hangman.utility.exception;

import org.springframework.http.HttpStatus;

public class ValidationExc extends HangmanException{
private static final long serialVersionUID = 1L;
	
	public ValidationExc(String messageCode) {
		super();
		this.code = 0;
		this.messageCode = messageCode;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.OK;
	}
}
