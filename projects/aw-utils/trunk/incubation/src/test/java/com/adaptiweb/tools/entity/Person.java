package com.adaptiweb.tools.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entity for testing purpose.
 */
@Entity
public class Person {
	@Id @GeneratedValue
	private Integer id;
	@Column(nullable=false)
	private String name;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return getId() + ": " + getName();
	}
}
