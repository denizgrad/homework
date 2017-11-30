package com.dozen.hangman.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dozen.hangman.HangmanApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HangmanApplication.class)
public class WordServiceTest {

	@Autowired
	WordService wordService;
	@Test
	public void wordServiceTest() {
		String word1 = wordService.getRandomWord();
		assertThat(word1.length())
	      .isNotEqualTo(0);
		
		String word2 = wordService.getRandomWord();
		
		assertThat(word1)
	      .isNotEqualTo(word2);
	}
}
