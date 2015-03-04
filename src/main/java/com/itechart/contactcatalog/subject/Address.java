package com.itechart.contactcatalog.subject;

import java.io.Serializable;

public class Address implements Serializable, Cloneable{
	private String country;
	private String town;
	private String street;
	private int house;
	private int flat;
	private long indexValue;
	
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

	public int getHouse() {
		return house;
	}

	public void setHouse(int house) {
		this.house = house;
	}

	public int getFlat() {
		return flat;
	}

	public void setFlat(int flat) {
		this.flat = flat;
	}

	public long getIndexValue() {
		return indexValue;
	}

	public void setIndexValue(long indexValue) {
		this.indexValue = indexValue;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(country);
		sb.append(" ");
		sb.append(town);
		if (street!=null){
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
