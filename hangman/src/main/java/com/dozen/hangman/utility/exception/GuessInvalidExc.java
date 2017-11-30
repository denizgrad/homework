package com.dozen.hangman.utility.exception;

import org.springframework.http.HttpStatus;

public class GuessInvalidExc extends HangmanException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public GuessInvalidExc() {
		super();
		this.code = 1;
		this.messageCode = GUESS_INVALID;
	}
	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.OK;
	}
}
