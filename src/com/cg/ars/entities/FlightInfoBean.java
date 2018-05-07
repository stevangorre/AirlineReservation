package com.cg.ars.entities;

import java.sql.Time;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="flightinformationSpring")
public class FlightInfoBean {
	
	@Id
	@Column(name="FlightNo")
	@Pattern(regexp="^[A-Z]{2}[0-9]{3}$",message="Please match the requested pattern. Eg:AI021.")
	@NotEmpty(message="This field can not be empty")
	private String flightNo;
	
	@Column(name="AirLine")
	@NotEmpty(message="This field can not be empty")
	private String airLine;
	
	@Column(name="dep_city")
	@NotEmpty(message="This field can not be empty")
	private String depCity;
	
	@Column(name="arr_city")
	@NotEmpty(message="This field can not be empty")
	private String arrCity;
	
	
	@Column(name="dep_date")
	@Future
	private Date depDate;
	
	@Column(name="arr_date")
	@Future
	private Date arrDate;
	
	@Column(name="dep_time")
	@NotEmpty(message="This field can not be empty")
	private String depTime;
	
	@Column(name="arr_time")
	@NotEmpty(message="This field can not be empty")
	private String arrTime;
	
	@Column(name="firstSeats")
	@NotNull
	@Max(value=500)
	@Min(value=1)
	private int firstSeats;
	
	@Column(name="firstSeatsFare")
	@NotNull
	@Min(value=1)
	private int firstSeatFare;
	
	@Column(name="bussSeats")
	@NotNull
	@Max(value=500)
	@Min(value=1)
	private int bussSeats;
	
	@Column(name="bussSeatsFare")
	@NotNull
	@Min(value=1)
	private int bussSeatsFare;
	
	public String getFlightNo() {
	return flightNo;
	}
	
	public void setFlightNo(String flightNo) {
	this.flightNo = flightNo;
	}
	public String getAirLine() {
	return airLine;
	}
	public void setAirLine(String airLine) {
	this.airLine = airLine;
	}

public FlightInfoBean() {
	super();
}


public String getDepCity() {
	return depCity;
}

@Override
public String toString() {
	return "FlightInfoBean [flightNo=" + flightNo + ", airLine=" + airLine
			+ ", depCity=" + depCity + ", arrCity=" + arrCity
			+ ", depDate=" + depDate + ", arrDate=" + arrDate
			+ ", depTime=" + depTime + ", arrTime=" + arrTime
			+ ", firstSeats=" + firstSeats + ", firstSeatFare="
			+ firstSeatFare + ", bussSeats=" + bussSeats
			+ ", bussSeatsFare=" + bussSeatsFare + "]";
}

public FlightInfoBean(String flightNo, String airLine, String depCity,
		String arrCity, Date depDate, Date arrDate, String depTime,
		String arrTime, int firstSeats, int firstSeatFare, int bussSeats,
		int bussSeatsFare) {
	super();
	this.flightNo = flightNo;
	this.airLine = airLine;
	this.depCity = depCity;
	this.arrCity = arrCity;
	this.depDate = depDate;
	this.arrDate = arrDate;
	this.depTime = depTime;
	this.arrTime = arrTime;
	this.firstSeats = firstSeats;
	this.firstSeatFare = firstSeatFare;
	this.bussSeats = bussSeats;
	this.bussSeatsFare = bussSeatsFare;
}

public void setDepCity(String depCity) {
	this.depCity = depCity;
}

public String getArrCity() {
	return arrCity;
}

public void setArrCity(String arrCity) {
	this.arrCity = arrCity;
}

public Date getDepDate() {
	return depDate;
}
public void setDepDate(Date depDate) {
	this.depDate = depDate;
}
public Date getArrDate() {
	return arrDate;
}
public void setArrDate(Date arrDate) {
	this.arrDate = arrDate;
}
public String getDepTime() {
	return depTime;
}
public void setDepTime(String depTime) {
	this.depTime = depTime;
}
public String getArrTime() {
	return arrTime;
}
public void setArrTime(String arrTime) {
	this.arrTime = arrTime;
}
public int getFirstSeats() {
	return firstSeats;
}
public void setFirstSeats(int firstSeats) {
	this.firstSeats = firstSeats;
}
public int getFirstSeatFare() {
	return firstSeatFare;
}
public void setFirstSeatFare(int firstSeatFare) {
	this.firstSeatFare = firstSeatFare;
}
public int getBussSeats() {
	return bussSeats;
}
public void setBussSeats(int bussSeats) {
	this.bussSeats = bussSeats;
}
public int getBussSeatsFare() {
	return bussSeatsFare;
}
public void setBussSeatsFare(int bussSeatsFare) {
	this.bussSeatsFare = bussSeatsFare;
}
}
