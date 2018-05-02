package com.cg.ars.entities;

import java.sql.Date;






import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@NamedQueries(@NamedQuery(name="getAllBookings",query="From BookingInfoBean booking where booking.custEmail=:custEmail"))
@Entity
@Table(name="BookingInfoSpring")
public class BookingInfoBean {

	@Id
	@SequenceGenerator(name="idSequence",sequenceName="bookingId_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="idSequence")
	@Column(name="booking_id")
	private int bookingId;
	
	@Column(name="Cust_Email")
	private String custEmail;
	
	@Column(name="FlightNo")
	private String flightNo;
	
	@Column(name="No_Of_Passengers")
	private int numOfPass;
	
	@Column(name="Class_Type")
	private String classType;
	
	@Column(name="total_fare")
	private int totalFare;
	
	@Column(name="seat_number")
	private int seatNumbers;
	
	@Column(name="CardInfo")
	private String cardInfo;
	
	@Column(name="src_city")
	private String depCity;
	
	@Column(name="dest_city")
	private String arrCity;
	
	@Column(name="dep_date")
	private Date depDate;
	
	


	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public String getCustEmail() {
		return custEmail;
	}
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}
	public int getNumOfPass() {
		return numOfPass;
	}
	public void setNumOfPass(int numOfPass) {
		this.numOfPass = numOfPass;
	}
	
	public int getTotalFare() {
		return totalFare;
	}
	public void setTotalFare(int totalFare) {
		this.totalFare = totalFare;
	}
	public int getSeatNumbers() {
		return seatNumbers;
	}
	public void setSeatNumbers(int seatNumbers) {
		this.seatNumbers = seatNumbers;
	}
	public String getCardInfo() {
		return cardInfo;
	}
	public void setCardInfo(String cardInfo) {
		this.cardInfo = cardInfo;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	

	public Date getDepDate() {
		return depDate;
	}
	public void setDepDate(Date depDate) {
		this.depDate = depDate;
	}

	public BookingInfoBean() {
		super();
	}
	public String getDepCity() {
		return depCity;
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
	public BookingInfoBean(int bookingId, String custEmail, String flightNo,
			int numOfPass, String classType, int totalFare, int seatNumbers,
			String cardInfo, String depCity, String arrCity, Date depDate) {
		super();
		this.bookingId = bookingId;
		this.custEmail = custEmail;
		this.flightNo = flightNo;
		this.numOfPass = numOfPass;
		this.classType = classType;
		this.totalFare = totalFare;
		this.seatNumbers = seatNumbers;
		this.cardInfo = cardInfo;
		this.depCity = depCity;
		this.arrCity = arrCity;
		this.depDate = depDate;
	}



	
}
