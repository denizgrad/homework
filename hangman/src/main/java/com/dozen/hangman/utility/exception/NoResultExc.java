package com.dozen.hangman.utility.exception;

import org.springframework.http.HttpStatus;

public class NoResultExc extends HangmanException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoResultExc() {
		super();
		this.code = 0;
		this.messageCode = NO_RESULT;
	}

	public NoResultExc(Object... args) {
		super();
		this.code = 0;
		this.messageCode = NO_RESULT;
		this.args = args;
	}


	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.NOT_FOUND;
	}

}
