package com.dozen.hangman.service;

import java.util.List;

import com.dozen.hangman.model.Game;
import com.dozen.hangman.model.Guess;
import com.dozen.hangman.model.Player;

public interface GameService {

	public List<Game> getGames();
	public Game createGame(Player player);
	public Game getGame(Integer id);
	public Game makeGuess(Integer gameId, Guess guess);
	public Game deleteGame(Integer id);
	
	
}
