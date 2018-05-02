package com.cg.ars.dao;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.cg.ars.entities.BookingInfoBean;
import com.cg.ars.entities.FlightInfoBean;
import com.cg.ars.entities.UserBean;
import com.cg.ars.exception.AirlineException;

@Repository
public class Booking implements IBooking{
	
	@PersistenceContext
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public String login(UserBean uobj) throws AirlineException {
		String role=null;
		UserBean user=null;
		try {
			//em.creteQuery("from ")
		user=em.find(UserBean.class, uobj.getUserName());
		if(user==null) {
			throw new AirlineException("No user with the given credentials");
		}
		else{
			if(user.getPassword().equals(uobj.getPassword())){
				role=user.getRole();
				return role;
			}
		}
		}catch(Exception e) {
			throw new AirlineException(e.getMessage());
		}
		return role;
	}

	@Override
	public String register(UserBean obj) throws AirlineException {
		String uid=null;
		obj.setRole("user");
		try {
		em.persist(obj);
		em.flush();
		}catch(Exception e) {
			throw new AirlineException(e.getMessage());
		}
		uid=obj.getUserName();
		return uid;
	}
	
	@Override
	public void update(UserBean user) throws AirlineException {
		try{
			user.setRole("user");
			em.merge(user);
			em.flush();
		}
		catch(Exception e){
			e.printStackTrace();
			throw new AirlineException(e.getMessage());
		}
		
	}
	
	@Override
	public UserBean getUser(String id) throws AirlineException {
		UserBean user=null;
		try{
			user=em.find(UserBean.class,id);
			if(user==null){
				throw new AirlineException("There is no user with the given Id : "+id);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new AirlineException(e.getMessage());
		}
		return user;
	}
	
	@Override
	public List<BookingInfoBean> getBookings(String custEmail) throws AirlineException {
		List<BookingInfoBean> bookings=null;
		try{
			TypedQuery<BookingInfoBean> query=em.createNamedQuery("getAllBookings",BookingInfoBean.class);
			query.setParameter("custEmail", custEmail);
			bookings=query.getResultList();
			
			if(bookings.isEmpty()){
				throw new AirlineException("No Bookings to display");
			}
		}
		catch(Exception e){
			//e.printStackTrace();
			throw new AirlineException(e.getMessage());
		}
		return bookings;
	}
	
	@Override
	public String cancelBooking(int bookingId) throws AirlineException {
		BookingInfoBean booking=null;
		FlightInfoBean flight=null;
		try
		{
			booking=em.find(BookingInfoBean.class, bookingId);
			if(booking==null)
			{
				throw new Exception("No Booking Exists with Id "+ bookingId);
			}
			flight=em.find(FlightInfoBean.class,booking.getFlightNo());
			if(booking.getClassType().equals("firstclass"))
			{
				flight.setFirstSeats(flight.getFirstSeats()+booking.getNumOfPass());
			}
			else
			{
				flight.setBussSeats(flight.getBussSeats()+booking.getNumOfPass());
			}
			em.merge(flight);
			em.remove(booking);			
			em.flush();
			
		}
		catch(Exception e){
			//e.printStackTrace();
			throw new AirlineException(e.getMessage());
		}
		return null;
	}

	@Override
	public List<FlightInfoBean> viewFlights() throws AirlineException {
		
	 Query q=em.createQuery("select f from FlightInfoBean f");
		List<FlightInfoBean> flist=q.getResultList();
		if(flist.size()==0)
		{
			throw new AirlineException("There are no flights that are added..");
		}
		return flist;
	}
	
	@Override
	public List<FlightInfoBean> getExecOccupancy(Date startdate,Date lastdate) throws AirlineException {
		List<FlightInfoBean> execOccup=new ArrayList();
		List<FlightInfoBean> flist=viewFlights();
			for(FlightInfoBean fobj:flist){
				if((fobj.getDepDate().getTime()>=startdate.getTime()) && (fobj.getDepDate().getTime()<=lastdate.getTime())) {
				int tot=0,a=0,b=0,c=0,d=0,occ=0;
				Query q1=em.createQuery("select f.firstSeats from FlightInfoBean f where f.flightNo=:a");
				Query q2=em.createQuery("select f.bussSeats from FlightInfoBean f where f.flightNo=:a");
				Query q3=em.createQuery("select max(f.seatNumbers) from BookingInfoBean f where f.flightNo=:b and f.classType=:c");
				Query q4=em.createQuery("select max(f.seatNumbers) from BookingInfoBean f where f.flightNo=:b and f.classType=:c");
				q1.setParameter("a",fobj.getFlightNo());
				q2.setParameter("a",fobj.getFlightNo());
				q3.setParameter("b",fobj.getFlightNo());
				q3.setParameter("c","FirstClass");
				q4.setParameter("b",fobj.getFlightNo());
				q4.setParameter("c", "Business");
				try {
					a=(int)q1.getSingleResult();
					b=(int)q2.getSingleResult();
					c=(int)q3.getSingleResult();
					d=(int)q4.getSingleResult();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				if(c==0&&d==0) {
					tot=a+b;
					occ=c+d;
				}
				else if(c==0) {
					tot=a+d;
					occ=d-b;
				}
				else if(d==0) {
					tot=d+b;
					occ=c-a;
				}
				else {
					tot=c+d;
					occ=(c+d)-(a+b);
				}
				
				FlightInfoBean obj=new FlightInfoBean();
				obj.setFlightNo(fobj.getFlightNo());
				obj.setFirstSeats(tot);
				obj.setBussSeats(occ);
				execOccup.add(obj);
			}
	}
			return execOccup;
	
}
}
