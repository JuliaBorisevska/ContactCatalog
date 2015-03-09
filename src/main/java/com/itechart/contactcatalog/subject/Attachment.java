package com.itechart.contactcatalog.subject;

import org.joda.time.LocalDateTime;

public class Attachment extends Entity {
	private String title;
	private String path;
	private LocalDateTime uploads;
	private String userComment;
	private Contact contact;
	
	public Attachment() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getUploads() {
		return uploads;
	}

	public void setUploads(LocalDateTime uploads) {
		this.uploads = uploads;
	}

	public String getUserComment() {
		return userComment;
	}

	public void setUserComment(String comment) {
		this.userComment = comment;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
	

}
