package com.dozen.hangman.utility.exception;

import org.springframework.http.HttpStatus;

public class GuessAlreadyMadeExc extends HangmanException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public GuessAlreadyMadeExc() {
		super();
		this.code = 1;
		this.messageCode = GUESS_ALREADY_MADE;
	}
	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.OK;
	}
}
