package com.infosys.infytel.userservice.dto;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LoginDTO {
	@Id
	@NotNull(message = "{userservice.emailid.absent}")
	@Email(message = "{userservice.emailid.invalid}")
	String emailId;
	@NotNull(message = "{userservice.password.absent}")
	@Pattern(regexp ="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{7,20}", message = "{userservice.loginpassword.invalid}")
	String password;
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LoginDTO() {
		super();
	}
	@Override
	public String toString() {
		return "LoginDTO [emailId=" + emailId + ", password=" + password + "]";
	}

	
}
