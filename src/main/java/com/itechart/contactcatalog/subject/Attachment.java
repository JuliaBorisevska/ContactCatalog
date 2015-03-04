package com.itechart.contactcatalog.subject;

public class Attachment extends Entity {
	private String title;
	private String path;
	private String uploads;
	private String userComment;
	
	public Attachment() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUploads() {
		return uploads;
	}

	public void setUploads(String uploads) {
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
	
	
	

}
