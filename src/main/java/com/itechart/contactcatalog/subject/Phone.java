package com.itechart.contactcatalog.subject;

public class Phone extends Entity {
	private Integer countryCode;
	private Integer operatorCode;
	private Long basicNumber;
	private PhoneType type; 
	private String userComment;
	
	public Phone() {
	}

	public Integer getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(Integer countryCode) {
		this.countryCode = countryCode;
	}

	public Integer getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(Integer operatorCode) {
		this.operatorCode = operatorCode;
	}

	public Long getBasicNumber() {
		return basicNumber;
	}

	public void setBasicNumber(Long number) {
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
