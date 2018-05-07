package com.cg.ars.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import org.springframework.web.bind.annotation.RequestParam;

import com.cg.ars.entities.AirportBean;
import com.cg.ars.entities.BookingInfoBean;
import com.cg.ars.entities.FlightInfoBean;
import com.cg.ars.entities.UserBean;
import com.cg.ars.exception.AirlineException;
import com.cg.ars.service.IBookingService;

@Controller
public class AirlineController {

	@Autowired
	private IBookingService service;
	
	public IBookingService getService() {
		return service;
	}

	public void setService(IBookingService service) {
		this.service = service;
	}
	
	UserBean user;
	BookingInfoBean booking;
	FlightInfoBean flight;
	String show="page";
	
	@RequestMapping("/index.obj")
	public String getLoginPage(Model model) {
		model.addAttribute("user",new UserBean());
		return "home";
	}

	@RequestMapping("/logout.obj")
	public String getLogoutPage(Model model) {
		model.addAttribute("user",new UserBean());
		return "home";
	}
	
	@RequestMapping(value="/processLogin.obj",method=RequestMethod.POST)
	public String processLoginPage(@ModelAttribute("user") @Valid UserBean user1,BindingResult res,Model model) {
	   String role=null;
		if(res.hasErrors()) {
			model.addAttribute("user",user1);
			model.addAttribute("error", "Error while processing. Try again.");
		   return "home";
	   }
	   try {
		role=service.login(user1);
		//user1.setRole(role);
		user=user1;
		model.addAttribute("user",user);
		if(role==null){
			 model.addAttribute("error","Invalid username or password");
			return "home";
		}
		else if (role.equals("executive")) {
			return "profileExecutive";
		}
		else if(role.equals("admin")) {
			return "profileAdmin";
		}
		
	} catch (AirlineException e) {
		e.printStackTrace();
		model.addAttribute("user",new UserBean());
		model.addAttribute("error", e.getMessage());
		return "home";
	}
	return "profile";
	}
	
	@RequestMapping("/register.obj")
	public String getRegisterPage(Model model) {
		model.addAttribute("userBeanForRegister", new UserBean());
		return "home";
	}
	
	@RequestMapping(value="/processRegister.obj",method=RequestMethod.POST)
	public String processRegisterPage(@ModelAttribute("userBeanForRegister") @Valid UserBean obj,BindingResult res,Model model) {
		ForPasswords pass=new ForPasswords();
		String uid=null;
		pass.validate(obj, res);
		if(res.hasErrors()) {
			model.addAttribute("error", "Error while processing. Try again.");
			model.addAttribute("userBeanForRegister", obj);
			return "home";
		}
		try {
			if(service.checkDuplicates(obj)) {
				uid=service.register(obj);
			}
			else {
				model.addAttribute("error","This email is already registered. Use another mail");
				model.addAttribute("userBeanForRegister", new UserBean());
				return "home";
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
			return "home";
		}
		model.addAttribute("uid", uid);
		return "home";
	}
	public List<AirportBean> getDestinations(){
		List<AirportBean> list=null;
		try {
		    list=service.viewDestinations();
		} catch (AirlineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping("/viewFlights.obj")
	public String getViewFlights(@RequestParam("custEmail") String email,Model model){
			model.addAttribute("user",user);
			List<String> destinations=new ArrayList<String>();
			List<AirportBean> list=null;
			list=getDestinations();
			for(AirportBean obj:list){
				destinations.add(obj.getLocation());
			}
			model.addAttribute("destList", destinations);
			model.addAttribute("flightObj",new FlightInfoBean());
			return "profile";
		/*} catch (AirlineException e) {
			model.addAttribute("error","Error fetching the details "+e.getMessage());
			return "profile";
		}*/
	
	}
	
	@RequestMapping(value="/processViewFlights.obj",method=RequestMethod.POST)
	public String processViewFlights(@ModelAttribute("flightObj")  FlightInfoBean fobj,@RequestParam("depdate") Date d,BindingResult res,Model model){
		model.addAttribute("user",user);
		if(res.hasErrors()){
			model.addAttribute("error","Error while Processing. Try again.");
			return "profile";
		}
		List<String> dest=new ArrayList<String>();
		List<AirportBean> airList=getDestinations();
		for(AirportBean obj1:airList){
			dest.add(obj1.getLocation());
		}
		model.addAttribute("destList",dest);
		if(fobj.getDepCity().equals(fobj.getArrCity())){
			model.addAttribute("error","source and destination cant be same");
			return "profile";
		}
		FlightInfoBean faobj=new FlightInfoBean();
	    faobj.setDepCity(fobj.getDepCity());
	    faobj.setArrCity(fobj.getArrCity());
	    faobj.setDepDate(d);
		List<FlightInfoBean> flist=null;
		List<AirportBean> list=getDestinations();
		List<AirportBean> aList=getDestinations();
		for(AirportBean obj:list){
			if(faobj.getDepCity().equals(obj.getLocation()))
				faobj.setDepCity(obj.getAbbr());
			if(faobj.getArrCity().equals(obj.getLocation()))
				faobj.setArrCity(obj.getAbbr());
		}
		try {
			flist=service.viewFlights(faobj.getDepCity(),faobj.getArrCity(),d);
		} catch (AirlineException e) {
			model.addAttribute("error", e.getMessage());
			return "profile";
		}
		if(flist.size()==0) {
			model.addAttribute("error", "Sorry, there are no flights on the selected date");
			return "profile";
		}
		for(FlightInfoBean obj:flist){
			for(AirportBean abean:aList){
			if(obj.getDepCity().equals(abean.getAbbr()))
				obj.setDepCity(abean.getLocation());
			if(obj.getArrCity().equals(abean.getAbbr()))
				obj.setArrCity(abean.getLocation());
		}
		}
		model.addAttribute("flist",flist);
		List<String> destinations=new ArrayList<String>();
		for(AirportBean obj:list){
			destinations.add(obj.getLocation());
		}
		model.addAttribute("destList",destinations);
		return "profile";
	}
	
	@RequestMapping("/BookFlight.obj")
	public String getBookingPage(@RequestParam("fnum") String fno,@RequestParam("custEmail") String email,Model model){
		model.addAttribute("fno",fno);
		try {
			user=service.getUser(email);
			model.addAttribute("user",user);
			booking=new BookingInfoBean();
			flight=service.getFlight(fno);
			booking.setArrCity(flight.getArrCity());
			booking.setDepCity(flight.getDepCity());
			booking.setDepDate(flight.getDepDate());
			model.addAttribute("bookingObj",booking);
		} catch (AirlineException e) {
			e.printStackTrace();
			model.addAttribute("error","Error fetching the details "+e.getMessage());
			return "profile";
		}
		return "profile";
	}

	@RequestMapping(value="/processBookFlight.obj",method=RequestMethod.POST)
	public String processBookFlight(@ModelAttribute("bookingObj")@Valid BookingInfoBean bobj,BindingResult res,Model model){
		int id=0;
		try {
			user=service.getUser(bobj.getCustEmail());
			model.addAttribute("user",user);
			
		if(res.hasErrors()){
			model.addAttribute("fno",bobj.getFlightNo());
			model.addAttribute("bookingObj",bobj);
			return "profile";
		}
			BookingInfoBean bdobj=service.updateSeatsInFlight(bobj);
			if(bdobj.getSeatNumbers()>0){
				id=service.finalBooking(bobj);
			}
		} catch (AirlineException e) {
			e.printStackTrace();
		}
		model.addAttribute("message","Successfully booked with id: "+id+" costing INR "+bobj.getTotalFare());
		return "profile";
	}
	
	
	@RequestMapping("/update.obj")
	public String processfetchUserForUpdate(@RequestParam("custEmail") String email,Model model){
		try{
			user=service.getUser(email);
			model.addAttribute("userUpdate",user);
			model.addAttribute("user",user);
			return "profile";
		}
		catch(Exception e){
			model.addAttribute("error","Error fetching the details "+e.getMessage());
			return "profile";
		}
	}
	
	@RequestMapping(value="/processUpdateProfileForm.obj",method=RequestMethod.POST)
	public String updateProductDetails(@ModelAttribute("userUpdate") @Valid UserBean user,BindingResult res,Model model){
		
		model.addAttribute("user",user);
		if(res.hasErrors()){
			model.addAttribute("error","Error while Processing. Try again.");
			return "profile";
		}
		try{
			service.update(user);
			model.addAttribute("message", "Profile updated Successfully");
			//model.addAttribute("user",user);
			return "profile";
		}
		catch(Exception e){
			model.addAttribute("error", "Error Occured while updating the profile.");
			return "profile";
		}
		
	}
	
	@RequestMapping("/view.obj")
	public String processGetListForm(@RequestParam("custEmail") String custEmail,Model model){
		try{
			List<BookingInfoBean> bookings=service.getBookings(custEmail);
			 user=service.getUser(custEmail);
			 model.addAttribute("user",user);
			if(bookings.isEmpty()){
				model.addAttribute("error", "No Bookings to display.");
			}
			model.addAttribute("bookings", bookings);
		}
		catch(Exception e){
			model.addAttribute("error",e.getMessage());
			return "profile";
		}
		return "profile";
	}
	
	@RequestMapping(value="/cancelBooking.obj")
	public String processCancelBookingForm(@RequestParam("bookingId") int bookingId,Model model){
		try
		{
			model.addAttribute("user",user);
			booking=service.getBooking(bookingId);
			if(service.checkDate(booking.getDepDate().toString())) {
				if(service.cancelBooking(bookingId)){
				model.addAttribute("message", "Booking has been Successfully cancelled with booking Id:"+bookingId);
				}
				else{
					model.addAttribute("error","Sorry! failed to cancel. Try again.");
				}
			}
			else {
				model.addAttribute("error","Sorry!, you can't change the past bookings");
				model.addAttribute("user",user);
			}

		}
		catch(Exception e){
			model.addAttribute("error", e.getMessage());
			model.addAttribute("user",user);
			return "profile";
		}
		return "profile";
	}
	
	@RequestMapping(value="/updateBooking.obj")
	public String updateBooking(@RequestParam("bookingId") int bookingId,Model model){
		
		model.addAttribute("user",user);
		try
		{
			booking=service.getBooking(bookingId);
			if(service.checkDate(booking.getDepDate().toString())) {
			model.addAttribute("booking",booking);
			return "profile";
			}
			else {
				model.addAttribute("error","Sorry!, you can't change the past bookings");
				return "profile";
			}
		}
		catch(Exception e){
			model.addAttribute("error", e.getMessage());
			return "profile";
		}
	}
	
	@RequestMapping(value="/processUpdateUpdateBooking.obj",method=RequestMethod.POST)
	public String updateBookingDetails(@ModelAttribute("booking") BookingInfoBean booking,BindingResult res,Model model){
		
		model.addAttribute("user",user);
		if(res.hasErrors()){
			model.addAttribute("error","Error while Processing. Try again.");
			return "profile";
		}
		try{
		int changeFare=service.updateUserBooking(booking);
		if(changeFare<0) {
			model.addAttribute("message", "Booking updated Successfully. Rs. "+((-1)*(changeFare))+" will be refunded to you.");
		}
		else {
			model.addAttribute("message", "Booking updated Successfully. You need to pay an extra amount of Rs. "+changeFare);
		}
			return "profile";
		}
		catch(Exception e){
			model.addAttribute("error", "Error Occured while updating the product details");
			return "profile";
		}
		
	}
	
	@RequestMapping("/ViewOccupancyWithDates.obj")
	public String getExecOccup(@RequestParam("custEmail") String custEmail,Model model) {
		try {
			user=service.getUser(custEmail);
		} catch (AirlineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("user",user);
		model.addAttribute("show", show);
		return "profileExecutive";
	}
	
	@RequestMapping("/ViewOccupancy.obj")
	public String getViewOccupancy(@RequestParam("custEmail") String custEmail,Model model){
		try {
			user=service.getUser(custEmail);
		} catch (AirlineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("user",user);
		List<AirportBean> aList=getDestinations();
		List<String> destinations=new ArrayList<String>();
		for(AirportBean obj:aList){
			destinations.add(obj.getLocation());
		}
		model.addAttribute("airList",destinations);
		model.addAttribute("bookingObject", new BookingInfoBean());
		return "profileExecutive";
	}
	
	@RequestMapping(value="/processViewOccupancy.obj",method=RequestMethod.POST)
	public String getProcessViewOccupancy(@ModelAttribute("bookingObject") BookingInfoBean bib,@RequestParam("email") String email,Model model){
	
		List<FlightInfoBean> occupList=new ArrayList();
		List<AirportBean> aList=getDestinations();
		String source=bib.getDepCity();
		String dest=bib.getArrCity();
		
			for(AirportBean abean:aList){
			if(source.equals(abean.getLocation()))
				source=abean.getAbbr();
			if(dest.equals(abean.getLocation()))
				dest=abean.getAbbr();
		}
		try {
			 user=service.getUser(email);
			   model.addAttribute("user",user);
			occupList=service.viewOccupancyBetweenDests(source,dest);
			if(occupList.size()==0)
			{
				model.addAttribute("error", "There are no Flights between the given cities");
			}
		} catch (AirlineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("occupList",occupList);
		return "profileExecutive";
	}
	
	@RequestMapping(value="/processExecOccup.obj",method=RequestMethod.POST)
	public String processExecOccup(@RequestParam("startDate") Date start,@RequestParam("endDate") Date end,@RequestParam("email") String email,Model model) {
	   try {
		   user=service.getUser(email);
		   model.addAttribute("user",user);
		List<FlightInfoBean> execOccup=service.getExecOccupancy(start,end);
		if(execOccup.size()==0){
			model.addAttribute("error","There are no flights between the given period");
			return "profileExecutive";
		}
		model.addAttribute("execOccup", execOccup);
		model.addAttribute("show", show);
		return "profileExecutive";
	}
	   catch (AirlineException e) {
		e.printStackTrace();
		model.addAttribute("error","Error while retrieving flights");
		return "profileExecutive";
	}
	}
	
	@RequestMapping("/AddAirport.obj")
	public String getAddDestination(Model model){
		
		
		model.addAttribute("airport", new AirportBean());
		return "profileAdmin";
	}
	
	@RequestMapping("/showReport.obj")
	public String getReports(Model model){
		
		model.addAttribute("show", show);
		return "profileAdmin";
	}
	
	@RequestMapping(value="/processAddDestination.obj",method=RequestMethod.POST)
	public String processAddDestination(@ModelAttribute("airport") @Valid AirportBean aobj,BindingResult res,Model model){
		if(res.hasErrors()){
			model.addAttribute("airport",aobj);
			return "profileAdmin";
		}
		try {
			if(service.checkExistence(aobj.getAbbr(),"airport")){
				if(service.addDestination(aobj)){
					model.addAttribute("message", "destination added successfully");
				}
				else{
					model.addAttribute("error", "failed to destination...please try again ");
				}
			}
			else{
				model.addAttribute("error", "This destination already exists. ");
			}
			return "profileAdmin";

		} catch (AirlineException e) {
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
			return "profileAdmin";
		}
	
	}
	
	
	@RequestMapping("/getAddFlight.obj")
	public String getAddFlight(Model model){
		
		List<String> destinations=new ArrayList();
		List<AirportBean> list=null;
		try {
			list = service.viewDestinations();
		} catch (AirlineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(AirportBean obj:list){
			destinations.add(obj.getLocation());
		}
		model.addAttribute("destList",destinations);
		model.addAttribute("flight",new FlightInfoBean());
		return "profileAdmin";
	}
	
	@RequestMapping(value="/processAddFlight.obj",method=RequestMethod.POST)
	public String processAddFlight(@ModelAttribute("flight") @Valid FlightInfoBean fobj,BindingResult res,Model model){
		List<String> destinations=new ArrayList<String>();
		List<AirportBean> list=null;
		try {
			list = service.viewDestinations();
		} catch (AirlineException e) {
			e.printStackTrace();
		}
		for(AirportBean obj:list){
			destinations.add(obj.getLocation());
		}
		AddFlightValidation fval=new AddFlightValidation();
		fval.validate(fobj, res);
		if(res.hasErrors()){
			model.addAttribute("flight", fobj);
			model.addAttribute("destList", destinations);
			return "profileAdmin";
		}
	        String flightNo=null;
	        List<AirportBean> airportList=null;
		try {
			airportList = service.viewDestinations();
			for(AirportBean obj:airportList){
				if(fobj.getDepCity().equals(obj.getLocation()))
					fobj.setDepCity(obj.getAbbr());
				if(fobj.getArrCity().equals(obj.getLocation()))
					fobj.setArrCity(obj.getAbbr());
			}
			if(service.checkExistence(fobj.getFlightNo(),"flight")){
			flightNo=service.addFlight(fobj);
			model.addAttribute("message", "flight added successfully with number "+flightNo);
			}
			else{
				model.addAttribute("error", "Sorry!, This flight already exists. ");
			}
		} catch (AirlineException e) {
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
			return "profileAdmin";
		}
		
		return "profileAdmin";
	}
	
	@RequestMapping("/ChangeSchedule.obj")
	public String getChangeSchedule(Model model) {

		List<FlightInfoBean> fList=null;
		try {
			fList = service.getFlights();
		} catch (AirlineException e) {
			e.printStackTrace();
		}
		model.addAttribute("fList", fList);
		return "profileAdmin";
	}
	
	@RequestMapping(value="/procesChangeSchedule.obj",method=RequestMethod.GET)
	public String getProcessChangeSchedule(@RequestParam("flightNo")String fno,Model model) {
		try {
			flight = service.getFlight(fno);
			model.addAttribute("flightForChange", flight);
		} catch (AirlineException e) {
			e.printStackTrace();
			model.addAttribute("error", "Sorry! Something went wrong. Try again");
		}
		return "profileAdmin";
	}
	
	@RequestMapping(value="/processFinalChangeSchedule.obj",method=RequestMethod.POST)
	public String processChangeSchedule(@ModelAttribute("flightForChange") FlightInfoBean fib,BindingResult res,Model model) {
		AddFlightValidation obj=new AddFlightValidation();
		boolean flag=false;
		obj.validate(fib, res);
		if(res.hasErrors()) {
			model.addAttribute("flightForChange", fib);
			model.addAttribute("error","Error while changing try again");
			return "profileAdmin";
		}
		try {
			if(service.updateSchedule(fib)){
				model.addAttribute("message","Schedule updated successfully");
				return "profileAdmin";
			}
			else{
				model.addAttribute("error","Error while updating");
				return "profileAdmin";
			}
		} catch (AirlineException e) {
			model.addAttribute("error", e.getMessage());
			e.printStackTrace();
			return "profileAdmin";
		}

	}
	
	@RequestMapping("/FlightstoDest.obj")
	public String getFLightsTODestination(Model model) {

		List<String> destinations=new ArrayList();
		List<AirportBean> list=null;
		list=getDestinations();
		for(AirportBean obj:list){
			destinations.add(obj.getLocation());
		}
		model.addAttribute("destsList", destinations);
		model.addAttribute("show", show);
		return "profileAdmin";
	}
	
	@RequestMapping(value="/processFlightsTodest.obj",method=RequestMethod.POST)
	public String getProcesFlightsToDestination(@RequestParam("destin") String dest,Model model) {
		List<FlightInfoBean> filtered=new ArrayList();
		List<AirportBean> airList=getDestinations();
		for(AirportBean obj:airList){
			if(dest.equals(obj.getLocation()))
				dest=obj.getAbbr();
		}
		try {
			List<FlightInfoBean> flist=service.getFlights();
			for(FlightInfoBean obj:flist) {
				if(obj.getArrCity().equals(dest))
					filtered.add(obj);
				
			}
			if(filtered.size()==0) {
				model.addAttribute("error", "There are no flights to this destination");
				
			}
			model.addAttribute("filtered",filtered);
			return "profileAdmin";
		} catch (AirlineException e) {
			model.addAttribute("error",e.getMessage());
			e.printStackTrace();
			return "profileAdmin";
		}
		
		
	}
	


	
	@RequestMapping(value="/processPassangersOfFlights.obj",method=RequestMethod.GET)
	public String getProcessPassangersOfFlights(@RequestParam("flightNo") String flightno,Model model) {
		
		try {
			List<String> passangers=service.getPassangers(flightno);
			model.addAttribute("passangers", passangers);
			return "profileAdmin";
			
		} catch (AirlineException e) {
			model.addAttribute("error", e.getMessage());
			e.printStackTrace();
			return "profileAdmin";
		}
	}
	
	@RequestMapping(value="/FlightsOnDate.obj")
	public String getFlightsOnDate(Model model){
		model.addAttribute("forDate",new FlightInfoBean());
		model.addAttribute("show", show);
		return "profileAdmin";
	}
	
	@RequestMapping(value="/processFLightsOnDate.obj",method=RequestMethod.POST)
	public String getProcessFLightsOnDate(@ModelAttribute("forDate") FlightInfoBean d1,Model model) {
		Date d=d1.getDepDate();
		System.out.println(d);
		List<FlightInfoBean> fl=new ArrayList();
//		if(d.getTime()<(Date.valueOf(LocalDate.now()).getTime())) {
//		model.addAttribute("err", "date cant be in past");
//		model.addAttribute("forDate", d1);
//		return "ChangeSchedule";
//		}
		try {
			List<FlightInfoBean> flist=service.getFlights();
			System.out.println(flist);
			for(FlightInfoBean fib:flist) {
				System.out.println(d.getTime());
				System.out.println(fib.getDepDate().getTime());
				if(fib.getDepDate().getTime()==d.getTime())
					fl.add(fib);
			}
		} catch (AirlineException e) {
			model.addAttribute("error", e.getMessage());
			e.printStackTrace();
			return "profileAdmin";
		}
		if(fl.size()==0) {
			model.addAttribute("forDate", d1);
			model.addAttribute("error", "no flights on selected date");
			return "profileAdmin";
		}
		
		model.addAttribute("forDate", d1);
		model.addAttribute("dater",d);
		model.addAttribute("flightondate",fl);
		return "profileAdmin";
	}
	

	
	@RequestMapping(value="/viewAllFlights.obj")
	public String removeFlight(Model model)
	{
		try 
		{
			List<FlightInfoBean> flist=service.viewFlights();
			model.addAttribute("flightList", flist);
			return "profileAdmin";
		} 
		
		catch (AirlineException e) {
			model.addAttribute("error", e.getMessage());
			return "profileAdmin";
		}
		
		
	}
	
	@RequestMapping(value="/processRemoveFlight.obj")
	public String processRemoveFlight(@RequestParam("flightNo") String flightNo ,Model model)
	{
		
		String fid=service.removeFlight(flightNo);
		model.addAttribute("message","Flight Successfully removed");
		return "profileAdmin";
	}
	
	@RequestMapping(value="/displayBookings.obj")
	public String displayBookings(@RequestParam("flightNo") String flightNo ,Model model)
	{
		
		try
		{
			
		List<BookingInfoBean> booklist=service.displayBookings(flightNo);
		model.addAttribute("booklist", booklist);
		return "profileAdmin";
		}
		catch (AirlineException e) {
			model.addAttribute("message", e.getMessage());
			return "profileAdmin";
		}
		
	}
	
	@RequestMapping("/viewDests.obj")
	public String getDestinations(Model model) {

		List<String> destinations=new ArrayList<String>();
		List<AirportBean> list=null;
		list=getDestinations();
		for(AirportBean obj:list){
			destinations.add(obj.getLocation());
		}
		model.addAttribute("destinsList", destinations);
		return "profileAdmin";
	}
	
	@RequestMapping("/adminOccup.obj")
	public String getAirlines(Model model) {

		List<String> airlines=new ArrayList<String>();
		List<String> list=null;
		list=service.getAirlines();
		for(String str:list){
			airlines.add(str);
		}
		model.addAttribute("airlines", airlines);
		return "profileAdmin";
	}
	
	
	@RequestMapping(value="/processadminoccupancy.obj",method=RequestMethod.POST)
	public String getAdminOccupancy(@RequestParam("airline") String airline,@RequestParam("clas") String clas,Model model) {

		List<FlightInfoBean> flights=null;
		try {
			flights = service.getAdminOccupancy(airline,clas);
			model.addAttribute("airline", airline);
			model.addAttribute("clas", clas);
			model.addAttribute("adminOccup",flights);
			return "profileAdmin";
		} catch (AirlineException e) {
			e.printStackTrace();
			model.addAttribute("error","Sorry, Error while processing. Try again.");
			return "profileAdmin";
		}
	}
	
}
