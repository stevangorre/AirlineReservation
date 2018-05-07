package com.cg.ars.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="usersSpring")
public class UserBean {
	
	@Id
	@Column(name="username")
	@NotEmpty(message="please give your mailId to login")
	@Email
	private String userName;
	
	@Column(name="password")
	@Size(min=6,max=12)
	@Pattern(regexp="^.*[0-9].*{6,12}$",message="Should be between 6 to 12 characters and contain atleast 1 digit")
	@NotEmpty(message="please give password to login")
	private String password;
	
	@Transient
	private String confirmPassword;
	private String role;
	
	@Column(name="mobile_no")
	@Pattern(regexp="^[0-9]{10}$",message="please give valid mobile number")
	private String mobileNo;

public String getConfirmPassword() {
	return confirmPassword;
}
public void setConfirmPassword(String confirmPassword) {
	this.confirmPassword = confirmPassword;
}

public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public String getMobileNo() {
	return mobileNo;
}
public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
}
@Override
public String toString() {
	return "UserBean [userName=" + userName + ", password=" + password + ", role=" + role + ", mobileNo=" + mobileNo
			+ "]";
}
public UserBean(String userName, String password, String role, String mobileNo) {
	super();
	this.userName = userName;
	this.password = password;
	this.role = role;
	this.mobileNo = mobileNo;
}
public UserBean() {
	
}
	
}
