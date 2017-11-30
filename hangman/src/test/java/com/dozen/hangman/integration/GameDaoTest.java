package com.dozen.hangman.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Objects;

import javax.transaction.Transactional;

import org.hibernate.Query;
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
import com.dozen.hangman.service.WordService;
import com.dozen.hangman.utility.GameFactory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HangmanApplication.class)
public class GameDaoTest extends BaseDao{
	
	@Autowired
	GameDao dao;
	@Autowired
	PlayerDao playerDao;

	@Autowired
	private WordService wordServiceMock;
	@MockBean
	WordService wordService;

	@Before
	public void setUp() {
		when(wordServiceMock.getRandomWord()).thenReturn("word");
		Player player = new Player();
		player.setName("name1");
		player.setAge(1);
		//add parent Player
		playerDao.addPlayer(player);
	}
	
	private Player getPlayer() {
		return playerDao.getPlayers().get(0);
	}
	private Game getGame() {
		Game game =  GameFactory.createGame(getPlayer(), GameStatus.ongoing, wordService.getRandomWord(), 6);
		return game;
	}
	
	@Test
	@Transactional
	public void addDeleteGameTest() {
		
		Game game = getGame();
		dao.addGame(game);
		String gameWord = (String) getCurrentSession().createQuery("select g.word from Game g where g.id = :id")
				.setInteger("id", game.getId()).uniqueResult();

		assertThat(game.getWord())
	      .isEqualTo(gameWord);
		
		dao.deleteGame(game.getId());
		
		String sql = "select count(g) from Game g";
		Object count = getCurrentSession().createQuery(sql)
				.uniqueResult();
		assertThat(Integer.parseInt(Objects.toString(count)))
	      .isEqualTo(0);
		
	}

	@Test
	@Transactional
	public void updateGameTest() {
		Game game = getGame();
		
		dao.addGame(game);
		int gameId = game.getId();
		
		Game gameToUpdate =  getGame();
		gameToUpdate.setId(gameId);
		gameToUpdate.setGameStatus(GameStatus.won);
		dao.updateGame(gameToUpdate);
		
		
		Game gameControl = (Game) getCurrentSession().createQuery("select g from Game g where g.id = :id")
				.setInteger("id", game.getId()).uniqueResult();

	
		assertThat(gameControl.getGameStatus())
	      .isEqualTo(GameStatus.won);
	}


	@Test
	@Transactional
	public void getGamesAndDeleteAllTest() {
		Game game1 =  getGame();
		dao.addGame(game1);
		Game game2 =  getGame();
		dao.addGame(game2);
		
		int testSize = dao.getGames().size();
		long testCount = (long) getCurrentSession().createQuery("select count(*) from Game ").uniqueResult();

		assertThat(Long.valueOf(testSize))
	      .isEqualTo(testCount);
		
		assertThat(2L)
	      .isEqualTo(testCount);
		
		String hql = String.format("delete from %s", "Game");
	    Query query = getCurrentSession().createQuery(hql);
	    query.executeUpdate();
	    
	    long testCount2 = (long) getCurrentSession().createQuery("select count(*) from Game ").uniqueResult();
	    
	    assertThat(0L)
	      .isEqualTo(testCount2);
	}

	@Test
	@Transactional
	public void guessCascadeTest() {
		Game game = getGame();
		dao.addGame(game);

		long testCount = (long) getCurrentSession().createQuery("select count(*) from Guess ").uniqueResult();

		assertThat(0L).isEqualTo(testCount);
		game.addGuess(new Guess("e"));
		dao.updateGame(game);

		long testCount2 = (long) getCurrentSession().createQuery("select count(*) from Guess ").uniqueResult();
		assertThat(1L).isEqualTo(testCount2);

	}
	

}
