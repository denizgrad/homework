package com.dozen.hangman.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.dozen.hangman.model.Game;
import com.dozen.hangman.model.GameStatus;
import com.dozen.hangman.model.Guess;
import com.dozen.hangman.model.Player;
import com.dozen.hangman.service.GameService;
import com.dozen.hangman.service.WordService;
import com.dozen.hangman.utility.GameFactory;
import com.dozen.hangman.utility.exception.NoResultExc;

/**
 * @author deniz.ozen
 */
@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerTest extends ATest{
	@Autowired
	private GameService gameServiceMock;
	@Autowired
	private WordService wordServiceMock;

	@MockBean
	GameService gameService;
	@MockBean
	WordService wordService;

	@Autowired
	private MockMvc mvc;
	@Before
	public void setUp() {
		Player gamePlayer = new Player();
		gamePlayer.setId(1);
		gamePlayer.setAge(1);
		gamePlayer.setName("first");
		
		when(wordServiceMock.getRandomWord()).thenReturn("word");
		Game first =  GameFactory.createGame(gamePlayer, GameStatus.ongoing, wordService.getRandomWord(), 6);
		first.setId(1);
		first.addGuess(new Guess("w"));
		first.populateStatusAndJsonFields();
		Game second = GameFactory.createGame(gamePlayer, GameStatus.ongoing, wordService.getRandomWord(), 6);
		second.setId(2);
		second.addGuess(new Guess("o"));
		second.populateStatusAndJsonFields();

		when(gameServiceMock.getGames()).thenReturn(Arrays.asList(first, second));
		when(gameServiceMock.getGame(1)).thenReturn(first);
		when(gameServiceMock.getGame(0)).thenThrow(new NoResultExc("game"));
	}
	@Test
	public void testGetAllPlayers() throws Exception {
		mvc.perform(get(APP_CONTEXT+"/game")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2)))

				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].guessedWord", is("w***")))
				.andExpect(jsonPath("$[0].guesses", is(1)))
				.andExpect(jsonPath("$[0].player", is("first")))

				.andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].guessedWord", is("*o**")))
				.andExpect(jsonPath("$[1].guesses", is(1)))
				.andExpect(jsonPath("$[1].player", is("first")));

		verify(gameService, times(1)).getGames();
		verifyNoMoreInteractions(gameService);
	}
	
	@Test
    public void testGetPlayerByIdNotFoundExc() throws Exception {
        mvc.perform(get(APP_CONTEXT+"/game/{id}", 0))
                .andExpect(status().isNotFound())
        		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
    			.andExpect(jsonPath("$.message", is(getMessage("exc.msg.noResult", "game"))));
        
        verify(gameService, times(1)).getGame(0);
        verifyNoMoreInteractions(gameService);
    }
	
	@Test
    public void testGetPlayerById() throws Exception {
        mvc.perform(get(APP_CONTEXT+"/game/{id}", 1))
        		.andExpect(status().isOk())
        		
        		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        		.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.player", is("first")))
				;
				
        verify(gameService, times(1)).getGame(1);
        verifyNoMoreInteractions(gameService);
    }
	
	 @Test
	    public void testAddGame() throws Exception {
	        Player toAddPlayer = new Player();
	        toAddPlayer.setAge(3);
	        toAddPlayer.setName("name1");
	        toAddPlayer.setId(3);
	        Game first =  GameFactory.createGame(toAddPlayer, GameStatus.ongoing, wordService.getRandomWord(), 0);
			first.setId(1);
			first.addGuess(new Guess("w"));
			first.populateStatusAndJsonFields();
	        when(gameServiceMock.createGame(toAddPlayer)).thenReturn(first);
	        
	        mvc.perform(post(APP_CONTEXT+"/game")
	                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
	                .content(convertObjectToJsonBytes(toAddPlayer))
	        )
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$.player", is("name1")))
			.andExpect(jsonPath("$.gameStatus", is("lost")));
	        
	        verify(gameService, times(1)).createGame(toAddPlayer);
	        verifyNoMoreInteractions(gameService);
	    }
}
