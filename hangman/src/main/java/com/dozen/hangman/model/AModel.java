package com.dozen.hangman.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * 
 * @author deniz.ozen
 *
 */
@MappedSuperclass
public class AModel {
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Id
	private Integer id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return object has an id
	 */
	@JsonIgnore
	public boolean isNew () {
		return id == null;
	}
	
}
