package com.infosys.infytel.userservice.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SellerLoginDTO2 {
	
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
	public SellerLoginDTO2() {
		super();
	}
	
	public static SellerLoginDTO2 valueOf(SellerLoginDTO2 cust) {
		SellerLoginDTO2 custDTO = new SellerLoginDTO2();
		custDTO.setPassword(cust.getPassword());
		custDTO.setEmailId(cust.getEmailId());
//		PlanDTO planDTO = new PlanDTO();
//		planDTO.setPlanId(cust.getPlanId());
//		custDTO.setCurrentPlan(planDTO);
//		custDTO.setCurrentPlan(planDTO);
		
		return custDTO;
	}
	
	@Override
	public String toString() {
		return "sellerLoginDTO [emailId=" + emailId + ", password=" + password + "]";
	}

	
}
