package com.dozen.hangman.utility.exception;

import org.springframework.http.HttpStatus;

public class PlayerAlreadyExists extends HangmanException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PlayerAlreadyExists() {
		super();
		this.code = 1;
		this.messageCode = PLAYER_ALREADY_EXISTS;
	}
	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.NOT_FOUND;
	}
}
