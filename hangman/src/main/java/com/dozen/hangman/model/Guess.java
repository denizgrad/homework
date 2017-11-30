package com.dozen.hangman.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.StringUtils;
/**
 * 
 * @author deniz.ozen
 *
 */
@Entity
@Table(name = "GUESSES")
public class Guess extends AModel {
	public Guess() {
		super();
	}

	@ManyToOne
	@JoinColumn(name = "game_id")
	Game game;
	
	@Column
	@NotNull
	private String letter;

	public Guess(String letter) {
		this.letter = letter;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Guess) {
			Guess test = (Guess)obj;
			return !StringUtils.isEmpty(this.letter) && !StringUtils.isEmpty(test.letter) ?  
							Objects.equals(this.letter, test.letter): super.equals(obj);
		}
		return super.equals(obj);
	}
	
}
