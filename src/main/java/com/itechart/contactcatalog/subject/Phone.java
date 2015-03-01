package com.itechart.contactcatalog.subject;

public class Phone extends Entity {
	private int countryCode;
	private int operatorCode;
	private long basicNumber;
	private PhoneType type; 
	private String userComment;
	
	public Phone() {
	}

	public int getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(int countryCode) {
		this.countryCode = countryCode;
	}

	public int getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(int operatorCode) {
		this.operatorCode = operatorCode;
	}

	public long getBasicNumber() {
		return basicNumber;
	}

	public void setBasicNumber(long number) {
		this.basicNumber = number;
	}

	public String getUserComment() {
		return userComment;
	}

	public void setUserComment(String comment) {
		this.userComment = comment;
	}

	public PhoneType getType() {
		return type;
	}

	public void setType(PhoneType type) {
		this.type = type;
	}
	
	
	
}
