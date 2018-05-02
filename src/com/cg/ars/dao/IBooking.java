package com.cg.ars.dao;

import java.sql.Date;
import java.util.List;

import com.cg.ars.entities.BookingInfoBean;
import com.cg.ars.entities.FlightInfoBean;
import com.cg.ars.entities.UserBean;
import com.cg.ars.exception.AirlineException;

public interface IBooking {

	String login(UserBean uobj) throws AirlineException;

	String register(UserBean obj) throws AirlineException;

	void update(UserBean user) throws AirlineException;

	UserBean getUser(String id) throws AirlineException;
	
	public List<BookingInfoBean> getBookings(String username) throws AirlineException;

	String cancelBooking(int bookingId) throws AirlineException;

	List<FlightInfoBean> viewFlights() throws AirlineException;

	List<FlightInfoBean> getExecOccupancy(Date startdate, Date lastdate) throws AirlineException;

}
