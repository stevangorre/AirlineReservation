package com.cg.ars.controller;

import java.sql.Date;
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
	
	@RequestMapping("/login.obj")
	public String getLoginPage(Model model) {
		model.addAttribute("user", new UserBean());
		return "Login";
	}

	@RequestMapping("/logout.obj")
	public String getLogoutPage(Model model) {
		model.addAttribute("user", new UserBean());
		return "Login";
	}
	
	@RequestMapping(value="/processLogin.obj",method=RequestMethod.POST)
	public String processLoginPage(@ModelAttribute("user") @Valid UserBean uobj,BindingResult res,Model model) {
	   String role=null;
		model.addAttribute("user",uobj);
		if(res.hasErrors()) {
		   return "Login";
	   }
	   try {
		role=service.login(uobj);
		if(role==null){
			 model.addAttribute("error","Invalid username or password");
			return "Login";
		}
		else if (role.equals("executive")) {
			return "profileExecutive";
		}
		
	} catch (AirlineException e) {
		e.printStackTrace();
		model.addAttribute("error", e.getMessage());
		return "ErrorPage";
	}
	return "profile";
	}
	
	@RequestMapping(value="/processExecOccup.obj",method=RequestMethod.POST)
	public String processExecOccup(@RequestParam("startDate") Date start,@RequestParam("endDate") Date end,@RequestParam("email") String email,Model model) {
	   try {
		   user=service.getUser(email);
		   model.addAttribute("user",user);
		List<FlightInfoBean> execOccup=service.getExecOccupancy(start,end);
		if(execOccup.size()==0){
			model.addAttribute("message","There are no flights between the given period");
			return "profileExecutive";
		}
		model.addAttribute("execOccup", execOccup);
		return "profileExecutive";
	}
	   catch (AirlineException e) {
		e.printStackTrace();
		model.addAttribute("error","Error while retrieving flights");
		return "profileExecutive";
	}
	}
	
	@RequestMapping("/register.obj")
	public String getRegisterPage(Model model) {
		model.addAttribute("userBeanForRegister", new UserBean());
		return "Register";
	}
	
	@RequestMapping(value="/processRegister.obj",method=RequestMethod.POST)
	public String processRegisterPage(@ModelAttribute("userBeanForRegister") @Valid UserBean obj,BindingResult res,Model model) {
		//ForPasswords pass=new ForPasswords();
		String uid=null;
		//pass.validate(obj, res);
		if(res.hasErrors()) {
			model.addAttribute("userBeanForRegister", obj);
			return "Register";
		}
		try {
			uid=service.register(obj);
		} catch (AirlineException e) {
			e.printStackTrace();
			model.addAttribute("error", e.getMessage());
			return "ErrorPage";
		}
		model.addAttribute("uid", uid);
		return "Register";
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
			model.addAttribute("message","Error fetching the details "+e.getMessage());
			return "ErrorPage";
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
			return "profile";
		}
		catch(Exception e){
			model.addAttribute("message", "Error Occured while updating the product details");
			return "ErrorPage";
		}
		
	}
	
	@RequestMapping("/view.obj")
	public String processGetListForm(@RequestParam("custEmail") String custEmail,Model model){
		try{
			List<BookingInfoBean> bookings=service.getBookings(custEmail);
			model.addAttribute("bookings", bookings);
			 user=service.getUser(custEmail);
			model.addAttribute("user",user);
			return "profile";
		}
		catch(Exception e){
			model.addAttribute("error",e.getMessage());
			return "ErrorPage";
		}
	}
	
	@RequestMapping(value="/cancelBooking.obj")
	public String processCancelBookingForm(@RequestParam("bookingId") int bookingId,Model model){
		try
		{
			@SuppressWarnings("unused")
			String clear;
			clear=service.cancelBooking(bookingId);
			model.addAttribute("message", "Booking has been Successfully cancelled with booking Id:"+bookingId);
			return "profile";
		}
		catch(Exception e){
			model.addAttribute("message", e.getMessage());
			return "ErrorPage";
		}
	}
}
