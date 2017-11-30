package com.dozen.hangman.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
/**
 * 
 * @author deniz.ozen
 * responsible for creating random words from "classpath:wordPool.txt"
 */
@Component
public class WordServiceImpl implements WordService {

	@Value(value = "classpath:wordPool.txt")
	private Resource wordPool;

	public static List<String> words = new ArrayList<>();

	@SuppressWarnings("resource")
	@PostConstruct
	public void init() throws Exception {
		Scanner reader = null;

		reader = new Scanner(wordPool.getInputStream());

		while (reader.hasNextLine()) {
			String word = reader.nextLine();
			words.add(word.trim());
		}
	}

	@Override
	public String getRandomWord() {
		int wordNum = words.size();
		return words.get((int) (Math. random() * wordNum ));
	}

}
