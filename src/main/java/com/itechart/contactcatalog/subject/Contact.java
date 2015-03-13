package com.itechart.contactcatalog.subject;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;

public class Contact extends Entity {
	private String firstName;
	private String middleName;
	private String lastName;
	private LocalDate birthDate;
	private String citizenship;
	private String website;
	private String email;
	private String company;
	private Sex sex;
	private MaritalStatus maritalStatus;
	private String image;
	private Address address;
	private List<Phone> phones;
	private List<Attachment> attachments;
	
	public Contact() {
		super();
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	public String getCitizenship() {
		return citizenship;
	}
	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<Phone> getPhones() {
		return phones;
	}
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id - ");
		sb.append(this.getId());
		sb.append(",first name - ");
		sb.append(firstName);
		sb.append(", lastName - ");
		sb.append(lastName);
		sb.append(", middleName - ");
		sb.append(middleName);
		sb.append(", birthDate - ");
		sb.append(birthDate);
		sb.append(", citizenship - ");
		sb.append(citizenship);
		sb.append(", website - ");
		sb.append(website);
		sb.append(", email - ");
		sb.append(email);
		sb.append(", company - ");
		sb.append(company);
		sb.append(", sex: ");
		sb.append(sex);
		sb.append(", maritalStatus: ");
		sb.append(maritalStatus);
		sb.append(", image: ");
		sb.append(image);
		sb.append(", address: ");
		sb.append(address);
		return sb.toString();
	}
	
	
	
}
