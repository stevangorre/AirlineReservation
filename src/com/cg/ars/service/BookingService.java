package com.cg.ars.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.ars.dao.IBooking;
import com.cg.ars.entities.BookingInfoBean;
import com.cg.ars.entities.FlightInfoBean;
import com.cg.ars.entities.UserBean;
import com.cg.ars.exception.AirlineException;

@Service
@Transactional
public class BookingService implements IBookingService{

	@Autowired
	private IBooking dao;

	public IBooking getDao() {
		return dao;
	}

	public void setDao(IBooking dao) {
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
	public String cancelBooking(int bookingId) throws AirlineException {
		// TODO Auto-generated method stub
		return dao.cancelBooking(bookingId);
	}

	@Override
	public List<FlightInfoBean> viewFlights() throws AirlineException {
		// TODO Auto-generated method stub
		return dao.viewFlights();
	}

	@Override
	public List<FlightInfoBean> getExecOccupancy(Date startdate, Date lastdate)
			throws AirlineException {
		// TODO Auto-generated method stub
		return dao.getExecOccupancy(startdate, lastdate);
	}
	
}
