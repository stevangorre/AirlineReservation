package com.cg.ars.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.ars.dao.IBookingDAO;
import com.cg.ars.entities.AirportBean;
import com.cg.ars.entities.BookingInfoBean;
import com.cg.ars.entities.FlightInfoBean;
import com.cg.ars.entities.UserBean;
import com.cg.ars.exception.AirlineException;

@Service
@Transactional
public class BookingServiceImpl implements IBookingService{

	@Autowired
	private IBookingDAO dao;

	public IBookingDAO getDao() {
		return dao;
	}

	public void setDao(IBookingDAO dao) {
		this.dao = dao;
	}

	@Override
	public String login(UserBean uobj) throws AirlineException {
		
		return dao.login(uobj);
	}

	@Override
	public String register(UserBean obj) throws AirlineException {
		// TODO Auto-generated method stub
		return dao.register(obj);
	}

	@Override
	public void update(UserBean user) throws AirlineException {
		dao.update(user);
		
	}

	@Override
	public UserBean getUser(String id) throws AirlineException {
		// TODO Auto-generated method stub
		return dao.getUser(id);
	}

	@Override
	public List<BookingInfoBean> getBookings(String username)
			throws AirlineException {
		// TODO Auto-generated method stub
		return dao.getBookings(username);
	}

	@Override
	public boolean cancelBooking(int bookingId) throws AirlineException {
		// TODO Auto-generated method stub
		return dao.cancelBooking(bookingId);
	}

	@Override
	public BookingInfoBean getBooking(int id) throws AirlineException {
		// TODO Auto-generated method stub
		return dao.getBooking(id);
	}

	@Override
	public void updateBooking(BookingInfoBean booking) throws AirlineException {
		// TODO Auto-generated method stub
	dao.updateBooking(booking);	
	}

	@Override
	public void updateFlight(FlightInfoBean flight) throws AirlineException {
		dao.updateFlight(flight);
	}

	@Override
	public FlightInfoBean getFlight(String id) throws AirlineException {
		// TODO Auto-generated method stub
		return dao.getFlight(id);
	}

	@Override
	public int updateUserBooking(BookingInfoBean booking) throws AirlineException {
		return dao.updateUserBooking(booking);
		
	}

	@Override
	public List<AirportBean> viewDestinations() throws AirlineException {
		// TODO Auto-generated method stub
		return dao.viewDestinations();
	}

	@Override
	public boolean addDestination(AirportBean aobj) throws AirlineException {
		// TODO Auto-generated method stub
		return dao.addDestination(aobj);
	}

	@Override
	public String addFlight(FlightInfoBean fobj) throws AirlineException {
		// TODO Auto-generated method stub
		return dao.addFlight(fobj);
	}

	@Override
	public List<FlightInfoBean> viewFlights(String depCity, String arrCity, Date departDate) throws AirlineException {
		// TODO Auto-generated method stub
		return dao.viewFlights(depCity, arrCity, departDate);
	}

	@Override
	public BookingInfoBean updateSeatsInFlight(BookingInfoBean bobj) throws AirlineException {
		// TODO Auto-generated method stub
		return dao.updateSeatsInFlight(bobj);
	}

	@Override
	public int finalBooking(BookingInfoBean bobj) throws AirlineException {
		// TODO Auto-generated method stub
		return dao.finalBooking(bobj);
	}

	@Override
	public boolean checkDuplicates(UserBean user) throws AirlineException {
		// TODO Auto-generated method stub
		return dao.checkDuplicates(user);
	}
	
	@Override
	public boolean checkDate(String depDate){
		Date dep=Date.valueOf(depDate);
		if(dep.getTime()>Date.valueOf(LocalDate.now().toString()).getTime()){
			return true;
		}
		return false;
	}

	@Override
	public List<FlightInfoBean> viewOccupancyBetweenDests(String source, String dest) throws AirlineException {
		// TODO Auto-generated method stub
		return dao.viewOccupancyBetweenDests(source, dest);
	}

	@Override
	public List<String> getAirlines() {
		// TODO Auto-generated method stub
		return dao.getAirlines();
	}

	@Override
	public List<FlightInfoBean> getAdminOccupancy(String airline, String clas) throws AirlineException {
		// TODO Auto-generated method stub
		return dao.getAdminOccupancy(airline, clas);
	}

	@Override
	public List<FlightInfoBean> getExecOccupancy(Date startdate, Date lastdate) throws AirlineException {
		// TODO Auto-generated method stub
		return dao.getExecOccupancy(startdate, lastdate);
	}

	@Override
	public List<FlightInfoBean> viewFlights() throws AirlineException {
		// TODO daoAuto-generated method stub
		return dao.viewFlights();
	}

	@Override
	public List<FlightInfoBean> getFlights() throws AirlineException {
		// TODO Auto-generated method stub
		return dao.getFlights();
	}

	@Override
	public List<String> getPassangers(String flightNo) throws AirlineException {
		// TODO Auto-generated method stub
		return dao.getPassangers(flightNo);
	}

	@Override
	public boolean updateSchedule(FlightInfoBean fib) throws AirlineException {
		// TODO Auto-generated method stub
		return dao.updateSchedule(fib);
	}

	@Override
	public String removeFlight(String flightNo) {
		// TODO Auto-generated method stub
		return dao.removeFlight(flightNo);
	}

	@Override
	public List<BookingInfoBean> displayBookings(String flightNo) throws AirlineException {
		// TODO Auto-generated method stub
		return dao.displayBookings(flightNo);
	}

	@Override
	public boolean checkExistence(String id, String tab)
			throws AirlineException {
		// TODO Auto-generated method stub
		return dao.checkExistence(id, tab);
	}	
	
}
