package com.dozen.hangman.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dozen.hangman.model.Game;
/**
 * 
 * @author deniz.ozen
 *
 */
@Repository
public class GameDaoImpl extends BaseDao implements GameDao{

	@Override
	public void addGame(Game game) {
		addModel(game);
	}

	@Override
	public void updateGame(Game game) {
		Game gameToUpdate = getGame(game.getId());
		gameToUpdate.setGameStatus(game.getGameStatus());
		//
		gameToUpdate.setGuessList(game.getGuessList());
		//
		gameToUpdate.setPlayerObj(game.getPlayerObj());
		updateModel(gameToUpdate);
	}

	@Override
	public Game getGame(int id) {
		Game ret =  getCurrentSession().get(Game.class, id);
		return ret;
	}

	@Override
	public void deleteGame(int id) {
		deleteModel(Game.class, id);
	}

	@Override
	public List<Game> getGames() {
		return getModels(Game.class);
	}
	
	

}
