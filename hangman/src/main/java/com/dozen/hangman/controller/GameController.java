package com.dozen.hangman.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dozen.hangman.model.Game;
import com.dozen.hangman.model.Guess;
import com.dozen.hangman.model.Player;
import com.dozen.hangman.service.GameService;
import com.dozen.hangman.utility.exception.GuessInvalidExc;
import com.dozen.hangman.utility.exception.ValidationExc;
/**
 * 
 * @author deniz.ozen
 *
 */
@RestController
public class GameController extends AController{

	@Autowired
	GameService gameService;
	
	public static final String GAME = "/game";
	
	@GetMapping(APP_CONTEXT+GAME)
	public ResponseEntity<List<Game>> getGames() {
		List<Game> gamesRet= gameService.getGames();
		return new ResponseEntity<List<Game>>(gamesRet, HttpStatus.OK);
	}
	
	@RequestMapping(value = APP_CONTEXT+GAME, method = RequestMethod.POST,
	        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
	        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Game> startGameForPlayer(@RequestBody @Valid Player player) {
		if(player.getId() == null) {
			throw new ValidationExc("Player Id cannot be null") ;
		}
		Game ret = gameService.createGame(player);
		return new ResponseEntity<Game>(ret, HttpStatus.OK);
	}
	
	@RequestMapping(value = APP_CONTEXT+GAME+"/{id}", method = RequestMethod.PUT,
	        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
	        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Game> putGuessOnGameById(@PathVariable Integer id,@RequestBody @Valid Guess guess) {
		 return new ResponseEntity<Game>(gameService.makeGuess(id, guess), HttpStatus.OK);
	}

	@GetMapping(value=APP_CONTEXT+GAME+"/{id}")
	public ResponseEntity<Game> getGameById(@PathVariable Integer id) {
		return new ResponseEntity<Game>(gameService.getGame(id), HttpStatus.OK);
	}
	
	@DeleteMapping(value=APP_CONTEXT+GAME+"/{id}")
	public ResponseEntity<Game> deleteGame(@PathVariable Integer id) {
		return new ResponseEntity<Game>(gameService.deleteGame(id), HttpStatus.OK);
	}
}
