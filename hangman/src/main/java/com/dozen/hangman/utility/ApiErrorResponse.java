package com.dozen.hangman.utility;
/**
 * 
 * @author deniz.ozen
 *	exception wrapper for json response
 */
public class ApiErrorResponse{

	private String message;
	private Integer code;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	

}
