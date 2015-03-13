package com.itechart.contactcatalog.subject;

public class MaritalStatus extends Entity{
	private String title;
	
	public MaritalStatus() {
		super();
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
		sb.append(this.getId());
		sb.append(", title - ");
		sb.append(title);
		return sb.toString();
	}
}
