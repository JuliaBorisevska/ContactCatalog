package com.itechart.contactcatalog.subject;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class Address implements Serializable, Cloneable{
	private String country;
	private String town;
	private String street;
	private Integer house;
	private Integer flat;
	private Long indexValue;
	
	public Address() {
		super();
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getHouse() {
		return house;
	}

	public void setHouse(Integer house) {
		this.house = house;
	}

	public Integer getFlat() {
		return flat;
	}

	public void setFlat(Integer flat) {
		this.flat = flat;
	}

	public Long getIndexValue() {
		return indexValue;
	}

	public void setIndexValue(Long indexValue) {
		this.indexValue = indexValue;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(country);
		sb.append(" ");
		sb.append(town);
		if (StringUtils.isNotEmpty(street)){
			sb.append(" ул. ");
			sb.append(street);
			if (house!=0){
				sb.append(" д.");
				sb.append(house);
				if (flat!=0){
					sb.append(", кв.");
					sb.append(flat);
				}
			}
		}
		return sb.toString();
	}

	
	
	
	
	
}
