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

import com.dozen.hangman.model.Player;
import com.dozen.hangman.service.PlayerService;
import com.dozen.hangman.utility.exception.NoResultExc;
/**
 * @author deniz.ozen
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PlayerController.class)
public class PlayerControllerTest extends ATest {

	@Autowired
	private PlayerService playerServiceMock;

	@MockBean
	PlayerService playerService;

	@Autowired
	private MockMvc mvc;
	
	@Before
	public void setUp() {
		Player first = new Player();
		first.setId(1);
		first.setAge(1);
		first.setName("first");
		Player second = new Player();
		second.setId(2);
		second.setAge(2);
		second.setName("second");

		when(playerServiceMock.getPlayers()).thenReturn(Arrays.asList(first, second));
		when(playerServiceMock.getPlayer(1)).thenReturn(first);
		when(playerServiceMock.getPlayer(0)).thenThrow(new NoResultExc("player"));
	}

	@Test
	public void testGetAllPlayers() throws Exception {
		mvc.perform(get(APP_CONTEXT+"/player")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2)))

				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is("first")))
				.andExpect(jsonPath("$[0].age", is(1)))

				.andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].name", is("second")))
				.andExpect(jsonPath("$[1].age", is(2)));

		verify(playerService, times(1)).getPlayers();
		verifyNoMoreInteractions(playerService);
	}

	@Test
    public void testGetPlayerByIdNotFoundExc() throws Exception {
        mvc.perform(get(APP_CONTEXT+"/player/{id}", 0))
                .andExpect(status().isNotFound())
        		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
    			.andExpect(jsonPath("$.message", is(getMessage("exc.msg.noResult", "player"))));
        
        verify(playerService, times(1)).getPlayer(0);
        verifyNoMoreInteractions(playerService);
    }
	
	@Test
    public void testGetPlayerById() throws Exception {
        mvc.perform(get(APP_CONTEXT+"/player/{id}", 1))
        		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        		
        		.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("first")))
				.andExpect(jsonPath("$.age", is(1)))
				;
				
        verify(playerService, times(1)).getPlayer(1);
        verifyNoMoreInteractions(playerService);
    }
	
	 @Test
	    public void testAddPlayerNotValid() throws Exception {
	        String name = createStringWithLength(201);
	 
	        Player toAddPlayer = new Player();
	        toAddPlayer.setAge(3);
	        toAddPlayer.setName(name);
			
	        mvc.perform(post(APP_CONTEXT+"/player")
	                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
	                .content(convertObjectToJsonBytes(toAddPlayer))
	        )
	        .andExpect(status().isBadRequest())
    		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$.message", is("name size must be between 5 and 200")));
	 
	        verifyZeroInteractions(playerServiceMock);
	    }
	 
	 @Test
	    public void testAddPlayer() throws Exception {
	        Player toAddPlayer = new Player();
	        toAddPlayer.setAge(3);
	        toAddPlayer.setName("name1");
	        toAddPlayer.setId(3);
	        when(playerServiceMock.createPlayer(toAddPlayer)).thenReturn(toAddPlayer);
	        
	        mvc.perform(post(APP_CONTEXT+"/player")
	                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
	                .content(convertObjectToJsonBytes(toAddPlayer))
	        )
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(jsonPath("$.name", is("name1")))
			.andExpect(jsonPath("$.age", is(3)));
	        
	        verify(playerService, times(1)).createPlayer(toAddPlayer);
	        verifyNoMoreInteractions(playerService);
	    }

}
