package com.dozen.hangman.utility.exception;

import org.springframework.http.HttpStatus;
/**
 * 
 * @author deniz.ozen
 *	abstract custom application exception
 */
public abstract class HangmanException extends RuntimeException{

	public static String NO_RESULT = "exc.msg.noResult";
	public static String GUESS_ALREADY_MADE= "exc.msg.guessAlreadyMade";
	public static String PLAYER_ALREADY_EXISTS= "exc.msg.playerAlreadyExists";
	public static String GAME_LOST= "exc.msg.gameIsLost";
	public static String GAME_WON= "exc.msg.gameIsWon";
	public static String GUESS_INVALID  ="exc.msg.guessInvalid";
	protected Integer code;
	protected String messageCode;
	/**
	 * fillers
	 */
	protected Object[] args;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	
	
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(String[] args) {
		this.args = args;
	}
	/**
	 * strategy filler
	 */
	public abstract HttpStatus getHttpStatus();

}
