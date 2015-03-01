package com.itechart.contactcatalog.subject;

import java.io.Serializable;

public class Sex implements Serializable, Cloneable{
	private char id;
	private String title;
	
	public Sex() {
		super();
	}

	public char getId() {
		return id;
	}

	public void setId(char id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
