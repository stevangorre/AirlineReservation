package com.cg.ars.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="airportSpring")
public class AirportBean {
	
	@Column(name="AirportName")
	private String airportName;
	
	@Id
	@Column(name="abbr")
	private String abbr;
	
	@Column(name="Location")
	private String location;
	
	public String getAirportName() {
	return airportName;
}
public void setAirportName(String airportName) {
	this.airportName = airportName;
}
public String getAbbr() {
	return abbr;
}
public void setAbbr(String abbr) {
	this.abbr = abbr;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}

}
