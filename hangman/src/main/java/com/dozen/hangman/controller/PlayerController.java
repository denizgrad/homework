package com.dozen.hangman.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dozen.hangman.model.Player;
import com.dozen.hangman.service.PlayerService;
/**
 * 
 * @author deniz.ozen
 *
 */
@RestController
public class PlayerController extends AController{
	@Autowired
	PlayerService playerService;
	
	public static final String PLAYER = "/player";
	@GetMapping(APP_CONTEXT+PLAYER)
	public ResponseEntity<List<Player>> getPlayers() {
		return new ResponseEntity<List<Player>>(playerService.getPlayers(), HttpStatus.OK);
	}
	@RequestMapping(value = APP_CONTEXT+PLAYER, method = RequestMethod.POST,
	        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, 
	        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
	        )
	public ResponseEntity<Player> createPlayer(@RequestBody @Valid Player player) {
		Player ret = playerService.createPlayer(player);
		return new ResponseEntity<Player>(ret, HttpStatus.OK);
	}
	@GetMapping(value=APP_CONTEXT+PLAYER+"/{id}")
	public ResponseEntity<Player> getPlayerById(@PathVariable Integer id) {
		return new ResponseEntity<Player>(playerService.getPlayer(id), HttpStatus.OK);
	}
}
