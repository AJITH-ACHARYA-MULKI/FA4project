package com.infosys.infytel.userservice.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import com.infosys.infytel.userservice.entity.Seller;


public class SellerDTO {

	private String sellerId;
	@NotNull(message = "{userservice.emailid.absent}")
	@Email(message = "{userservice.emailid.invalid}")
	private String emailId;
	@NotNull(message = "{userservice.name.absent}")
	@Pattern(regexp ="[A-Za-z]+( [A-Za-z]+)*", message = "{userservice.name.invalid}")
	private String name;
	@NotNull(message = "{userservice.phoneNo.absent}")
	@Pattern(regexp = "^[0-9]{1,10}$", message = "{userservice.phoneNo.invalid}")
	private String phoneNo;
	@NotNull(message = "{userservice.password.absent}")
	@Pattern(regexp ="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{7,20}", message = "{userservice.password.invalid}")
	private String password;
	private String isactive;


	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	

	@Override
	public String toString() {
		return "sellerDTO [phoneNo=" + phoneNo + ", name=" + name + ", emailId=" + emailId
				+ ", password=" + password + ", isactive=" + isactive + "]";
	}

		// Converts Entity into DTO
		public static SellerDTO valueOf(Seller cust) {
			SellerDTO custDTO = new SellerDTO();
			custDTO.setSellerId(cust.getSellerId());
			custDTO.setPassword(cust.getPassword());
			custDTO.setEmailId(cust.getEmailId());
			custDTO.setName(cust.getName());
			custDTO.setPhoneNo(cust.getPhoneNo());
			custDTO.setIsactive(cust.getIsactive());
//			PlanDTO planDTO = new PlanDTO();
//			planDTO.setPlanId(cust.getPlanId());
//			custDTO.setCurrentPlan(planDTO);
//			custDTO.setCurrentPlan(planDTO);
			
			return custDTO;
		}

		// Converts DTO into Entity
		public Seller createEntity3() {
			Seller cust = new Seller();
			cust.setSellerId(this.getSellerId());			
			cust.setPassword(this.getPassword());
			cust.setEmailId(this.getEmailId());
			cust.setName(this.getName());
			cust.setPhoneNo(this.getPhoneNo());
			cust.setIsactive(this.getIsactive());
			
			return cust;
		}

		


}
