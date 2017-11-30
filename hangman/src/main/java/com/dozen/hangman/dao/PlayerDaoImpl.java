package com.dozen.hangman.dao;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.dozen.hangman.model.Player;
/**
 * 
 * @author deniz.ozen
 *
 */
@Repository
public class PlayerDaoImpl extends BaseDao implements PlayerDao{

	@Override
	public Player addPlayer(Player player) {
		return (Player) addModel(player);
	}

	@Override
	public void updatePlayer(Player player) {
		Player playerToUpdate = getPlayer(player.getId());
		playerToUpdate.setAge(player.getAge());
		playerToUpdate.setName(player.getName());
		playerToUpdate.setGames(player.getGames());
		updateModel(playerToUpdate);
	}

	@Override
	public Player getPlayer(int id) {
		return getCurrentSession().get(Player.class, id);
	}
	@Override
	public boolean isPlayerExists(String name) {
		String sql = "select count(p) from Player p "
				+ "where p.name = :name";
		Object count = getCurrentSession().createQuery(sql)
				.setParameter("name", name)
				.uniqueResult();
		return Integer.parseInt(Objects.toString(count)) > 0;
	}

	@Override
	public Player getPlayerByName(String name) {
		String sql = "select p from Player p "
				+ "where p.name = :name";
		Player ret = (Player) getCurrentSession().createQuery(sql)
				.setParameter("name", name)
				.uniqueResult();
		return ret;
	}
	
	@Override
	public void deletePlayer(int id) {
		deleteModel(Player.class, id);
	}

	@Override
	public List<Player> getPlayers() {
		return getModels(Player.class);
	}
	
}

