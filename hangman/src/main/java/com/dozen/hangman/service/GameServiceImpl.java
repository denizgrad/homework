package com.dozen.hangman.service;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.dozen.hangman.dao.GameDao;
import com.dozen.hangman.dao.PlayerDao;
import com.dozen.hangman.model.Game;
import com.dozen.hangman.model.GameStatus;
import com.dozen.hangman.model.Guess;
import com.dozen.hangman.model.Player;
import com.dozen.hangman.utility.GameFactory;
import com.dozen.hangman.utility.exception.GameOverExc;
import com.dozen.hangman.utility.exception.GuessAlreadyMadeExc;
import com.dozen.hangman.utility.exception.GuessInvalidExc;
import com.dozen.hangman.utility.exception.HangmanException;
import com.dozen.hangman.utility.exception.NoResultExc;
/**
 * 
 * @author deniz.ozen
 *
 */
@Service
@Transactional
public class GameServiceImpl implements GameService{

	public static int maximumGuess = 6;
	@Autowired
	WordService wordService;
	@Autowired
	GameDao gameDao;
	@Autowired
	PlayerDao playerDao;
	@Resource
	private Environment env;
	
	@Override
	public List<Game> getGames() {
		List<Game> games = gameDao.getGames();
		games.forEach(g->g.populateStatusAndJsonFields());
		return games;
	}

	@Override
	public Game createGame(Player player) {
		Player playerDb = playerDao.getPlayer(player.getId());
		if(playerDb == null) {
			throw new NoResultExc("player");
		}
		Game newGame = GameFactory.createGame(playerDb, 
				GameStatus.ongoing, 
				wordService.getRandomWord(), 
				maximumGuess);
		gameDao.addGame(newGame);
		return newGame;
	}

	@Override
	public Game getGame(Integer id) {
		Game game = gameDao.getGame(id);
		if(game == null) {
			throw new NoResultExc();
		}
		game.populateStatusAndJsonFields();
		return game;
	}

	/**
	 * all guess logic
	 */
	@Override
	public Game makeGuess(Integer gameId, Guess guess) {
		if(!isOneLetter(guess.getLetter()) ) {
			throw new GuessInvalidExc();
		}
		Game game = gameDao.getGame(gameId);
		if(game == null) {
			throw new NoResultExc("game");
		}
		
		game.populateStatusAndJsonFields();
		guess.setLetter(guess.getLetter().toLowerCase());
		
		if(game.getGameStatus() == GameStatus.won) {
			throw new GameOverExc(HangmanException.GAME_WON);
		}
		if(game.getGameStatus() == GameStatus.lost) {
			throw new GameOverExc(HangmanException.GAME_LOST);
		}
		if(isGuessMadeBefore(game.getGuessList(), (guess))){
			throw new GuessAlreadyMadeExc();
		}
		if(!game.getWord().contains(guess.getLetter())){
			game.setGuessesLeft(game.getGuessesLeft() - 1);
		}
		
		game.getGuessList().add(guess);
		guess.setGame(game);
		
		game.populateStatusAndJsonFields();
		if(game.isLost() && game.getGuessesLeft() == 0) {
			game.setGuessedWord(game.getWord());
		}
		gameDao.updateGame(game);
		
		
		return game;
	}
	
	public boolean isOneLetter(String name) {
	    return name.matches("[a-zA-Z]");
	}

	private boolean isGuessMadeBefore(Set<Guess> guessList, Guess guess) {
		for (Guess guessDb : guessList) {
			if(guessDb.equals(guess)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Game deleteGame(Integer id) {
		Game game = gameDao.getGame(id);
		gameDao.deleteGame(id);
		return game;
	}
	
	
}
