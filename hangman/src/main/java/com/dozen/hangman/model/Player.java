package com.dozen.hangman.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 
 * @author deniz.ozen
 *
 */
@Entity
@Table(name="PLAYERS")
public class Player extends AModel{

	@Column
	@Size(min = 5, max = 200)
	private String name;
	
	@OneToMany(mappedBy="playerObj", fetch=FetchType.LAZY)
	@JsonIgnore
	Set<Game> games;
	
	@Column
	@NotNull
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Set<Game> getGames() {
		if (games == null) {
			games = new HashSet<>();
		}
		return games;
	}

	public void setGames(Set<Game> games) {
		this.games = games;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Player) {
			Player test = (Player)obj;
			return (this.getId() != null && test.getId() != null) 
					? Objects.equals(this.getId(), test.getId())
					: (!StringUtils.isEmpty(this.name) && !StringUtils.isEmpty(test.name) ?  
							Objects.equals(this.name, test.name): super.equals(obj));
		}
		
		return super.equals(obj);
	}
}
