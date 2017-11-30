package com.dozen.hangman.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
/**
 * 
 * @author deniz.ozen
 *
 */
public class AController {
	
	public static final String APP_CONTEXT = "/hangman/v1";
	
	@Autowired @Qualifier ("messageSource")
	protected MessageSource labelSource;
	
	/**
	 * Helper method for getting message form label source
	 * @return
	 */
	public String getMessage (String code, Object ... args) {
		return labelSource.getMessage(code, args, getUserLocale());
	}
	
	/**
	 * Helper method for getting user locale<br>
	 * It can be used in controllers easily
	 * @return user locale 
	 */
	public static Locale getUserLocale() {
		return LocaleContextHolder.getLocale();
	}
	
}
