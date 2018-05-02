package com.cg.ars.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.cg.ars.entities.UserBean;

public class ForPasswords implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		return UserBean.class.equals(arg0);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		UserBean obj=(UserBean)arg0;
	/*if(!(obj.getPassword().equals(obj.getConfirmPassword()))) {
		errors.rejectValue("confirmPassword","E124","password and confirm password fields should match");
	}
		*/
	}

}
