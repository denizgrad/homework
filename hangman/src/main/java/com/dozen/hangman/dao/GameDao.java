package com.dozen.hangman.dao;

import java.util.List;

import javax.persistence.NoResultException;

import com.dozen.hangman.model.Game;
/**
 * 
 * @author deniz.ozen
 *
 */
public interface GameDao {

	public void addGame(Game game);
	public void updateGame(Game game);
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws NoResultException if Game not exists with id 
	 */
	public Game getGame(int id);
	public void deleteGame(int id);
	public List<Game> getGames();
}
