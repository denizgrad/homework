package com.dozen.hangman.service;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.dozen.hangman.HangmanApplication;
import com.dozen.hangman.dao.BaseDao;
import com.dozen.hangman.dao.GameDao;
import com.dozen.hangman.dao.PlayerDao;
import com.dozen.hangman.model.Game;
import com.dozen.hangman.model.GameStatus;
import com.dozen.hangman.model.Guess;
import com.dozen.hangman.model.Player;
import com.dozen.hangman.utility.GameFactory;
import com.dozen.hangman.utility.exception.GuessAlreadyMadeExc;
import com.dozen.hangman.utility.exception.GuessInvalidExc;
import com.dozen.hangman.utility.exception.NoResultExc;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HangmanApplication.class)
public class GameServiceTest {
	
	@Autowired
	GameService gameService;
		
	@Autowired
	GameDao gameDaoMock;
	@MockBean
	GameDao gameDao;

	@Before
	public void setUp() {
		Player player = new Player();
		player.setName("name1");
		player.setAge(1);
		
		when(gameDaoMock.getGame(0)).thenReturn(null);
		
		Game doubleGuess  = GameFactory.createGame(player, GameStatus.lost, "word", 6);
		doubleGuess.addGuess(new Guess("w"));
		when(gameDaoMock.getGame(2)).thenReturn(doubleGuess);
	}
		
	@Test(expected=NoResultExc.class)
	public void getGameTestFail() {
		gameService.getGame(0);
	}
	
	@Test(expected=GuessAlreadyMadeExc.class)
	public void getGuessTwiceTest() {
		gameService.makeGuess(2, new Guess("w"));
	}
	
	@Test(expected=GuessInvalidExc.class)
	public void getGuessInvalidTest() {
		gameService.makeGuess(2, new Guess("ww"));
		
	}

}
