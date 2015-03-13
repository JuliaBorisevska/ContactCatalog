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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id - ");
		sb.append(id);
		sb.append(", title - ");
		sb.append(title);
		return sb.toString();
	}
	
	
}
