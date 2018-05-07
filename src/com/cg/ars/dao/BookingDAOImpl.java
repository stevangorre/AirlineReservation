package com.cg.ars.dao;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.cg.ars.entities.AirportBean;
import com.cg.ars.entities.BookingInfoBean;
import com.cg.ars.entities.FlightInfoBean;
import com.cg.ars.entities.UserBean;
import com.cg.ars.exception.AirlineException;
@Repository
public class BookingDAOImpl implements IBookingDAO{
	
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
	public boolean checkDuplicates(UserBean user) throws AirlineException {
		List<UserBean> list=null;
		String qStr="select u from UserBean u where u.userName=:uname";
		TypedQuery<UserBean> query=em.createQuery(qStr,UserBean.class);
		query.setParameter("uname",user.getUserName());
		 list=query.getResultList();
		if(list.size()==0) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean checkExistence(String id,String tab) throws AirlineException {
		String q1,q2;
		switch(tab){
		case "flight":
			List<FlightInfoBean> list=null;
			q1="select f from FlightInfoBean f where f.flightNo=:id";
			TypedQuery<FlightInfoBean> query1=em.createQuery(q1,FlightInfoBean.class);
			query1.setParameter("id",id);
			 list=query1.getResultList();
			if(list.size()==0) {
				return true;
			}
			return false;
			
		case "airport":
			List<AirportBean> list1=null;
			q2="select a from AirportBean a where a.abbr=:id";
			TypedQuery<AirportBean> query2=em.createQuery(q2,AirportBean.class);
			query2.setParameter("id",id);
			 list1=query2.getResultList();
			if(list1.size()==0) {
				return true;
			}
			return false;
		}
		return false;
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
	public int updateUserBooking(BookingInfoBean booking) throws AirlineException {
		try{
			BookingInfoBean bookingOld=getBooking(booking.getBookingId());
			FlightInfoBean flight=getFlight(bookingOld.getFlightNo());
			int changeSeats=bookingOld.getNumOfPass()-booking.getNumOfPass();
			int oldFirstSeats=flight.getFirstSeats();
			int oldBussSeats=flight.getBussSeats();
			
			if(booking.getClassType().equals(bookingOld.getClassType())){
				if(booking.getClassType().equals("firstClass")) {
					flight.setFirstSeats(flight.getFirstSeats()+changeSeats);
					
				}
				else {
					flight.setBussSeats(flight.getBussSeats()+changeSeats);
				} 
			}
			else if (booking.getNumOfPass()==bookingOld.getNumOfPass()) {
				if(booking.getClassType().equals("firstClass")) {
					flight.setFirstSeats(flight.getFirstSeats()-booking.getNumOfPass());
					flight.setBussSeats(flight.getBussSeats()+booking.getNumOfPass());
					bookingOld.setSeatNumbers(oldFirstSeats);
				}
				else {
					flight.setBussSeats(flight.getBussSeats()-booking.getNumOfPass());
					flight.setFirstSeats(flight.getFirstSeats()+booking.getNumOfPass());
					bookingOld.setSeatNumbers(oldBussSeats);
				}
			}
			else{
				if(booking.getClassType().equals("firstClass")) {
					flight.setBussSeats(flight.getBussSeats()+bookingOld.getNumOfPass());
					flight.setFirstSeats(flight.getFirstSeats()-booking.getNumOfPass());
					bookingOld.setSeatNumbers(oldFirstSeats);
				}
				else {
					flight.setFirstSeats(flight.getFirstSeats()+bookingOld.getNumOfPass());
					flight.setBussSeats(flight.getBussSeats()-booking.getNumOfPass());
					bookingOld.setSeatNumbers(oldBussSeats);
				} 
			}
			bookingOld.setClassType(booking.getClassType());
			int oldFare=bookingOld.getTotalFare();
			if(booking.getClassType().equals("firstClass")) {
				bookingOld.setTotalFare(flight.getFirstSeatFare()*booking.getNumOfPass());
			}
			else {
				bookingOld.setTotalFare(flight.getBussSeatsFare()*booking.getNumOfPass());
			}
			bookingOld.setNumOfPass(booking.getNumOfPass());
			updateFlight(flight);
			updateBooking(bookingOld);
			return bookingOld.getTotalFare()-oldFare;
		}
		catch(Exception e){
			e.printStackTrace();
			throw new AirlineException(e.getMessage());
		}
	}
	
	@Override
	public void updateBooking(BookingInfoBean booking) throws AirlineException {
		try{
			em.merge(booking);
			em.flush();
		}
		catch(Exception e){
			e.printStackTrace();
			throw new AirlineException(e.getMessage());
		}
		
	}
	
	@Override
	public void updateFlight(FlightInfoBean flight) throws AirlineException {
		try{
			em.merge(flight);
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
	public BookingInfoBean getBooking(int id) throws AirlineException {
		BookingInfoBean booking=null;
		try{
			booking=em.find(BookingInfoBean.class,id);
			if(booking==null){
				throw new AirlineException("There is no booking with the given Id : "+id);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new AirlineException(e.getMessage());
		}
		return booking;
	}
	
	@Override
	public FlightInfoBean getFlight(String id) throws AirlineException {
		FlightInfoBean flight=null;
		try{
			flight=em.find(FlightInfoBean.class,id);
			if(flight==null){
				throw new AirlineException("There is no flight with the given Id : "+id);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new AirlineException(e.getMessage());
		}
		return flight;
	}
	
	@Override
	public List<BookingInfoBean> getBookings(String custEmail) throws AirlineException {
		List<BookingInfoBean> bookings=null;
		try{
			TypedQuery<BookingInfoBean> query=em.createNamedQuery("getAllBookings",BookingInfoBean.class);
			query.setParameter("custEmail",custEmail);
			bookings=query.getResultList();
		}
		catch(Exception e){
			throw new AirlineException(e.getMessage());
		}
		return bookings;
	}
	
	@Override
	public boolean cancelBooking(int bookingId) throws AirlineException {
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
			return true;
			
		}
		catch(Exception e){
			throw new AirlineException(e.getMessage());
		}
	}

	@Override
	public List<AirportBean> viewDestinations() throws AirlineException {
		List<AirportBean> list=em.createQuery("from AirportBean").getResultList();
		return list;
	}

	@Override
	public boolean addDestination(AirportBean aobj) throws AirlineException {
		boolean val=false;
		try{
			em.persist(aobj);
			em.flush();
			val=true;
		}catch(Exception e){
			throw new AirlineException(e.getMessage());
		}
		return val;
	}
	@Override
	public String addFlight(FlightInfoBean fobj) throws AirlineException {
		String fli=null;
		try{
			em.persist(fobj);
			em.flush();
			fli=fobj.getFlightNo();
		}catch(Exception e){
			throw new AirlineException(e.getMessage());
		}
		return fli;
	}
	@Override
	public List<FlightInfoBean> viewFlights(String depCity, String arrCity, Date departDate) throws AirlineException {
		Query q=em.createQuery("from FlightInfoBean where depCity=:a and arrCity=:b and depDate=:c");
		q.setParameter("a", depCity);
		q.setParameter("b", arrCity);
		q.setParameter("c", departDate);
		return q.getResultList();
	}

	@Override
	public BookingInfoBean updateSeatsInFlight(BookingInfoBean bobj) throws AirlineException {
		String fno=bobj.getFlightNo();
		int numpass=bobj.getNumOfPass();
		String clas=bobj.getClassType();
		FlightInfoBean flightObj=em.find(FlightInfoBean.class,fno);
/*		bobj.setDepDate(flightObj.getDepDate());
		bobj.setDepCity(flightObj.getDepCity());
		bobj.setArrCity(flightObj.getArrCity());*/
		//bookingDetails.setFliObj(flightObj);
		if(clas.equals("firstClass")){
			if(flightObj.getFirstSeats()>=numpass){
				bobj.setSeatNumbers(flightObj.getFirstSeats());
				bobj.setTotalFare(numpass*(flightObj.getFirstSeatFare()));
				flightObj.setFirstSeats(flightObj.getFirstSeats()-numpass);
				em.merge(flightObj);
				return bobj;
			}
		}
		else{
			if(flightObj.getBussSeats()>=numpass){
				bobj.setSeatNumbers(flightObj.getBussSeats());
				bobj.setTotalFare(numpass*(flightObj.getBussSeatsFare()));
				flightObj.setBussSeats(flightObj.getBussSeats()-numpass);
				em.merge(flightObj);
				return bobj;
			}
		}
		return bobj;
	}

	@Override
	public int finalBooking(BookingInfoBean bobj) throws AirlineException {
		int id=0;
		try{
			em.persist(bobj);
			em.flush();
			id=bobj.getBookingId();
		}catch(Exception e){
			throw new AirlineException(e.getMessage());
		}
		return id;
	}
	
	public List<FlightInfoBean> getAllFlightsBetweenStations(String source,String dest){
		Query q=em.createQuery("from FlightInfoBean f where f.depCity=:a and f.arrCity=:b");
		q.setParameter("a", source);
		q.setParameter("b", dest);
		return q.getResultList();
	}
	
	@Override
	public List<FlightInfoBean> viewOccupancyBetweenDests(String source,String dest) throws AirlineException {
		List<FlightInfoBean> occupList=new ArrayList<FlightInfoBean>();
		try{
			List<FlightInfoBean> flist=getAllFlightsBetweenStations(source,dest);
			for(FlightInfoBean obj:flist){
				int p=0,q=0,r=0,s=0,tot=0,occ=0;
				Query q1=em.createQuery("from FlightInfoBean f where f.flightNo=:a");
				q1.setParameter("a",obj.getFlightNo());
				FlightInfoBean obj1=(FlightInfoBean)q1.getSingleResult();
				p=obj1.getFirstSeats();
//				Query q2=em.createQuery("from FlightInfoBean f where f.flightNo=:b");
//				q2.setParameter("b",obj.getFlightNo());
//				FlightInfoBean obj2=(FlightInfoBean)q2.getSingleResult();
				q=obj1.getBussSeats();
				
				try{
				Query q4=em.createQuery("select max(a.seatNumbers) from BookingInfoBean a where a.flightNo=:val2 and a.classType='Business'");
				q4.setParameter("val2",obj.getFlightNo());
				s=(Integer)q4.getSingleResult();
				}catch(NullPointerException e){
					//s=q;
				}
				System.out.println(p+" "+q);
				
				try{
				Query q3=em.createQuery("select max(a.seatNumbers) from BookingInfoBean a where a.flightNo=:val and a.classType='firstClass'");
				q3.setParameter("val",obj.getFlightNo());
				r=(Integer)q3.getSingleResult();
				}catch(NullPointerException e){
					//r=p;
				}
				
				if(r==0&&s==0) {
					tot=p+q;
					occ=r+s;
				}
				else if(r==0) {
					tot=p+s;
					occ=s-q;
				}
				else if(s==0) {
					tot=r+q;
					occ=r-p;
				}
				else {
					tot=r+s;
					occ=(r+s)-(p+q);
				}
				System.out.println(p+" "+q+" "+r+" "+s);
				System.out.println(tot);
				System.out.println(occ);
				FlightInfoBean fobj=new FlightInfoBean();
				fobj.setFlightNo(obj.getFlightNo());
				fobj.setFirstSeats(tot);
				fobj.setBussSeats(occ);
				occupList.add(fobj);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new AirlineException(e.getMessage());
		}
		return occupList;
	}

	@Override
	public List<String> getAirlines() {
//		List<String> airlines=new ArrayList();
//		List<FlightInfoBean> flist=(List<String>)em.createQuery("select distinct(f.airLine) from FlightInfoBean f").getResultList();
//		for(FlightInfoBean fobj:flist){
//			airlines.add(fobj.getAirLine());
//		}
//		return airlines;
		return (List<String>)em.createQuery("select distinct(f.airLine) from FlightInfoBean f").getResultList();
	}

	@Override
	public List<FlightInfoBean> getAdminOccupancy(String airline, String clas) throws AirlineException {
		List<FlightInfoBean> adminocucp=new ArrayList<FlightInfoBean>();
		Query q=em.createQuery("from FlightInfoBean f where f.airLine=:a");
		q.setParameter("a", airline);
		List<FlightInfoBean> flist=q.getResultList();
		if(clas.equals("firstClass")){
			for(FlightInfoBean fobj:flist){
				int tot=0,a=0,b=0,occ=0;
				Query q1=em.createQuery("select f.firstSeats from FlightInfoBean f where f.flightNo=:a");
				q1.setParameter("a",fobj.getFlightNo());
				try {
					a=(int)q1.getSingleResult();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				Query q2=em.createQuery("select max(f.seatNumbers) from BookingInfoBean f where f.flightNo=:b and f.classType=:c");
				q2.setParameter("b",fobj.getFlightNo());
				q2.setParameter("c","firstClass");
				try {
					b=(int)q2.getSingleResult();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(b==0){
					tot=a;
					occ=0;
				}
				else{
					tot=b;
					occ=(b-a);
				}
				FlightInfoBean obj=new FlightInfoBean();
				obj.setFlightNo(fobj.getFlightNo());
				obj.setFirstSeats(tot);
				obj.setBussSeats(occ);
				adminocucp.add(obj);
			}
		}
		else{
			for(FlightInfoBean fobj:flist){
				int tot=0,a=0,b=0,occ=0;
				Query q1=em.createQuery("select f.bussSeats from FlightInfoBean f where f.flightNo=:a");
				q1.setParameter("a",fobj.getFlightNo());
				try {
					a=(int)q1.getSingleResult();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				Query q2=em.createQuery("select max(f.seatNumbers) from BookingInfoBean f where f.flightNo=:b and f.classType=:c");
				q2.setParameter("b",fobj.getFlightNo());
				q2.setParameter("c", "Business");
				try {
					b=(int)q2.getSingleResult();
				} catch (Exception e) {	
					e.printStackTrace();
				}
				if(b==0){
					tot=a;
					occ=0;
				}
				else{
					tot=b;
					occ=(b-a);
				}
				FlightInfoBean obj=new FlightInfoBean();
				obj.setFlightNo(fobj.getFlightNo());
				obj.setFirstSeats(tot);
				obj.setBussSeats(occ);
				adminocucp.add(obj);
			}
		}
		return adminocucp;
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
		List<FlightInfoBean> execOccup=new ArrayList<FlightInfoBean>();
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
				q3.setParameter("c","firstClass");
				q4.setParameter("b",fobj.getFlightNo());
				q4.setParameter("c", "Business");
				try {
					a=(int)q1.getSingleResult();
					b=(int)q2.getSingleResult();
					try{
					c=(int)q3.getSingleResult();
					}
					catch(Exception e){
						e.printStackTrace();
					}
					try{
					d=(int)q4.getSingleResult();
					}
					catch(Exception e){
						e.printStackTrace();
					}
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
					tot=c+b;
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

	@Override
	public List<FlightInfoBean> getFlights() throws AirlineException {
		// TODO Auto-generated method stub
		try {
			return em.createQuery("from FlightInfoBean").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AirlineException(e.getMessage());
		}
	}

	@Override
	public boolean updateSchedule(FlightInfoBean fib) throws AirlineException {
		//fib.setFlightNo(fib.getFlightNo());
		try {
		Query q=em.createQuery("update FlightInfoBean f set f.depDate=:a,f.arrDate=:b,f.depTime=:c,f.arrTime=:d where f.flightNo=:e");
		
		q.setParameter("a", fib.getDepDate());
		q.setParameter("b",fib.getArrDate());
		q.setParameter("c", fib.getDepTime());
		q.setParameter("d", fib.getArrTime());
		q.setParameter("e", fib.getFlightNo());
		int f=q.executeUpdate();
		System.out.println(f);
		if(f>0) {
			Query q2=em.createQuery("update BookingInfoBean b set b.depDate=:a where b.flightNo=:c");
			q2.setParameter("a", fib.getDepDate());
			q2.setParameter("c", fib.getFlightNo());
			q2.executeUpdate();
				return true;
		}
	}catch(Exception e) {
		throw new AirlineException(e.getMessage());
	}
		return false;
	}

	@Override
	public List<String> getPassangers(String flightNo) throws AirlineException {
		
		
		Query q=em.createQuery("select f.custEmail from BookingInfoBean f where f.flightNo=:flightNo");
		q.setParameter("flightNo", flightNo);
		
		List<String> passangers=(List<String>)q.getResultList();
		if(passangers.size()==0)
		{
			throw new AirlineException("There are No passangers on this Flight to display");
		}
		
		return passangers;
	}
	
	@Override
	public String removeFlight(String flightNo) {
		
		Query q=em.createQuery("delete from BookingInfoBean f where f.flightNo=:flightNo");
		q.setParameter("flightNo", flightNo);
		FlightInfoBean f=em.find(FlightInfoBean.class, flightNo);
		q.executeUpdate();
		em.remove(f);
		em.flush();
		
		return flightNo;
	}

	@Override
	public List<BookingInfoBean> displayBookings(String flightNo) throws AirlineException{
		Query q=em.createQuery("select b from BookingInfoBean b where b.flightNo=:flightNo");
		q.setParameter("flightNo", flightNo);
		List<BookingInfoBean> booklist=q.getResultList();
		if(booklist.size()==0)
		{
			throw new AirlineException("There are No Bookings done for this flight");
		}
		return booklist;
	}
	
}
