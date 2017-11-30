package com.dozen.hangman.utility.exception;

import org.springframework.http.HttpStatus;

public class GameOverExc extends HangmanException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public GameOverExc(String messageCode) {
		super();
		this.code = 2;
		this.messageCode = messageCode;
	}
	@Override
	public	HttpStatus getHttpStatus() {
		return HttpStatus.OK;
	}
}
