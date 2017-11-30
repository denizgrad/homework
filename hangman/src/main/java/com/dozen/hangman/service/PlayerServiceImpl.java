package com.dozen.hangman.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dozen.hangman.dao.PlayerDao;
import com.dozen.hangman.model.Player;
import com.dozen.hangman.utility.exception.PlayerAlreadyExists;
/**
 * 
 * @author deniz.ozen
 *
 */
@Service
@Transactional
public class PlayerServiceImpl implements PlayerService{

	@Autowired
	PlayerDao playerDao;

	@Override
	public List<Player> getPlayers() {
		return playerDao.getPlayers();
	}

	@Override
	public Player createPlayer(Player player) {
		if(playerDao.isPlayerExists(player.getName())) {
			throw new PlayerAlreadyExists();
		}
		return playerDao.addPlayer(player);
	}

	@Override
	public Player getPlayer(Integer id) {
		return playerDao.getPlayer(id);
	}
}
