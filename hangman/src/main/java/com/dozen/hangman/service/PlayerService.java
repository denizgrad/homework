package com.dozen.hangman.service;

import java.util.List;

import com.dozen.hangman.model.Player;

public interface PlayerService {

	public List<Player> getPlayers();
	public Player createPlayer(Player player);
	public Player getPlayer(Integer id);
}
