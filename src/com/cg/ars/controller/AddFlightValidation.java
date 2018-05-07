package com.cg.ars.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.cg.ars.entities.FlightInfoBean;

public class AddFlightValidation implements Validator{

	public AddFlightValidation(){
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean supports(Class<?> arg0) {
		
		return FlightInfoBean.class.equals(arg0);
	}

	@Override
	public void validate(Object arg0, Errors arg1){
		FlightInfoBean obj=(FlightInfoBean)arg0;
		if(obj.getArrDate().getTime()<obj.getDepDate().getTime())
			arg1.rejectValue("arrDate", "E23", "this date should be after departure date");
		String depTime=obj.getDepTime();
		String arrTime=obj.getArrTime();
		String dep[]=depTime.split(":");
		String arr[]=arrTime.split(":");
		if(!(Integer.parseInt(dep[0])>=0 && Integer.parseInt(dep[0])<=23) || !(Integer.parseInt(dep[1])>=0 && Integer.parseInt(dep[1])<=59))
			arg1.rejectValue("depTime", "E45", "please give proper time");
		if(!(Integer.parseInt(arr[0])>=0 && Integer.parseInt(arr[0])<=23) || !(Integer.parseInt(arr[1])>=0 && Integer.parseInt(arr[1])<=59))
			arg1.rejectValue("arrTime", "E55", "please give proper time");
		
	}

}
