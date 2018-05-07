package com.cg.ars.dao;

import java.sql.Date;
import java.util.List;

import com.cg.ars.entities.AirportBean;
import com.cg.ars.entities.BookingInfoBean;
import com.cg.ars.entities.FlightInfoBean;
import com.cg.ars.entities.UserBean;
import com.cg.ars.exception.AirlineException;

public interface IBookingDAO {

	String login(UserBean uobj) throws AirlineException;

	String register(UserBean obj) throws AirlineException;

	void update(UserBean user) throws AirlineException;

	UserBean getUser(String id) throws AirlineException;
	
	public List<BookingInfoBean> getBookings(String username) throws AirlineException;

	boolean cancelBooking(int bookingId) throws AirlineException;

	BookingInfoBean getBooking(int id) throws AirlineException;

	void updateBooking(BookingInfoBean booking) throws AirlineException;

	void updateFlight(FlightInfoBean flight) throws AirlineException;

	FlightInfoBean getFlight(String id) throws AirlineException;

	int updateUserBooking(BookingInfoBean booking) throws AirlineException;
	
	List<AirportBean> viewDestinations() throws AirlineException;

	boolean addDestination(AirportBean aobj) throws AirlineException;

	String addFlight(FlightInfoBean fobj) throws AirlineException;

	List<FlightInfoBean> viewFlights(String depCity, String arrCity,Date departDate) throws AirlineException;

	BookingInfoBean updateSeatsInFlight(BookingInfoBean bobj) throws AirlineException;

	int finalBooking(BookingInfoBean bobj) throws AirlineException;

	boolean checkDuplicates(UserBean user) throws AirlineException;
	
	List<FlightInfoBean> viewOccupancyBetweenDests(String source, String dest) throws AirlineException;

    List<String> getAirlines();

	List<FlightInfoBean> getAdminOccupancy(String airline, String clas) throws AirlineException;

	List<FlightInfoBean> getExecOccupancy(Date startdate, Date lastdate) throws AirlineException;

	List<FlightInfoBean> viewFlights() throws AirlineException;
	
	List<FlightInfoBean> getFlights() throws AirlineException;

	List<String> getPassangers(String flightNo) throws AirlineException;

	boolean updateSchedule(FlightInfoBean fib) throws AirlineException;
	
	String removeFlight(String flightNo);
	
	List<BookingInfoBean> displayBookings(String flightNo) throws AirlineException;

	boolean checkExistence(String id, String tab) throws AirlineException;

}
