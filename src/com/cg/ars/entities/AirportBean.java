package com.cg.ars.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="airportSpring")
public class AirportBean {
	
	@Column(name="AirportName")
	@NotEmpty
	private String airportName;
	
	@Id
	@Column(name="abbr")
	@NotEmpty
	@Pattern(regexp="^[A-Z]{3}$",message="Match the requested pattern. Eg:PNQ.")
	private String abbr;
	
	@Column(name="Location")
	@NotEmpty
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
