package com.dozen.hangman.utility;

import com.dozen.hangman.model.Game;
import com.dozen.hangman.model.GameStatus;
import com.dozen.hangman.model.Player;

public class GameFactory {

	public static Game createGame(Player player, GameStatus gameStatus, String word, int guessesLeft){
		Game newGame = new Game();
		newGame.setPlayerObj(player);
		newGame.setGameStatus(GameStatus.ongoing);
		newGame.setWord(word);
		newGame.setGuessesLeft(guessesLeft);
		newGame.populateStatusAndJsonFields();
		return newGame;
	}
}
