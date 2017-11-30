package com.dozen.hangman.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/*
 * 
id*	integer($int64)
Game ID

player*	string
guesses*	integer
Indicates the number of guesses made.

guessesLeft*	integer
Indicates the number of guesses left.

guessedWord*	string
Contains the word with guessed letters. Letters not yet guessed are replaced with asterisks (*)

incorrectLetters*	string
Contains all the letters that have been proposed but turned out incorrect.

gameStatus*	string
Contains the state of the game.

Enum:
[ ongoing, won, lost ]
 */
@Entity
@Table(name = "GAMES")
public class Game extends AModel {
	/**
	 * player ralation object
	 */
	@OneToOne
	@JsonIgnore
	@JoinColumn(name = "player_id")
	Player playerObj;
	
	@OneToMany(mappedBy="game", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JsonIgnore
	Set<Guess> guessList;
	/**
	 * word itself
	 */
	@Column
	@JsonIgnore
	private String word;
	/**
	 * replaced with * where not guessed letters @see {@link Game#populateStatusAndJsonFields()}
	 */
	@Transient
	private String guessedWord;
	/**
	 * after method @see {@link Game#populateStatusAndJsonFields()}
	 */
	@Transient
	private String incorrectLetters;
	/**
	 * players name for json response @see {@link Game#populateStatusAndJsonFields()}
	 */
	@Transient
	private String player;
	
	@Enumerated(EnumType.STRING)
	@Column(name="game_status")
	GameStatus gameStatus;
	
	@Column(name="guesses_left")
	Integer guessesLeft;
	/**
	 * guess count @see {@link Game#populateStatusAndJsonFields()}
	 */
	@Transient
	int guesses;

	public Player getPlayerObj() {
		return playerObj;
	}

	public void setPlayerObj(Player player) {
		this.playerObj = player;
	}

	public Set<Guess> getGuessList() {
		if (guessList == null) {
			guessList = new HashSet<>();
		}
		return guessList;
	}

	public void addGuess(Guess guess) {
		this.getGuessList().add(guess);
		guess.setGame(this);
	}

	public void setGuessedWord(String guessedWord) {
		this.guessedWord = guessedWord;
	}

	public String getIncorrectLetters() {
		return incorrectLetters;
	}

	public void setIncorrectLetters(String incorrectLetters) {
		this.incorrectLetters = incorrectLetters;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Integer getGuessesLeft() {
		return guessesLeft;
	}

	public void setGuessesLeft(Integer guessesLeft) {
		this.guessesLeft = guessesLeft;
	}

	public void setGuessList(Set<Guess> guessList) {
		this.guessList = guessList;
	}

	public String getGuessedWord() {
		return guessedWord;
	}

	@JsonIgnore
	public boolean isWon() {
		return !this.getGuessedWord().contains("*");
	}
	@JsonIgnore
	public boolean isLost() {
		return this.gameStatus==GameStatus.lost;
	}

	
	public void setGuesses(Integer guesses) {
		this.guesses = guesses;
	}
	public int getGuesses() {
		return this.guesses;
	}
	/**
	 * <p>
	 * populates fields:<br>
	 * 	&nbsp	incorrectWords <br>
	 * 	&nbsp 	guesses<br>
	 * 	&nbsp 	guessedWord<br>
	 * 	&nbsp 	player(just name field)<br>
	 * 	&nbsp 	gameStatus<br>
	 * 
	 * before validations and json response
	 * </p>
	 */
	public void populateStatusAndJsonFields() {
		StringBuffer incorrects = new StringBuffer();
		List<String> corrects = new ArrayList<>();

		this.getGuessList().forEach(guess -> {
			String letter = ((Guess) guess).getLetter();
			if (this.getWord().contains(letter)) {
				corrects.add(letter);
			} else {
				incorrects.append(letter);
			}
		});
		StringBuffer guessedWord = new StringBuffer();
		for (char c : this.getWord().toCharArray()) {
			if (corrects.contains(String.valueOf(c))) {
				guessedWord.append(c);
			} else {
				guessedWord.append("*");
			}
		}

		this.setGuessedWord(guessedWord.toString());
		this.setIncorrectLetters(incorrects.toString());
		this.setGuesses(this.getGuessList().size());
		
		if (this.isWon()) {
			this.setGameStatus(GameStatus.won);
		} else if (this.getGuessesLeft() == 0) {
			this.setGameStatus(GameStatus.lost);
		}
		this.setPlayerName(this.getPlayerObj().getName());
	}

	public void setPlayerName(String player) {
		this.player = player;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}
	
	

	
}
