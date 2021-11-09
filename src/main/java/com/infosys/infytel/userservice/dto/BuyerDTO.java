package com.infosys.infytel.userservice.dto;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
//import java.util.List;

import com.infosys.infytel.userservice.entity.Buyer;


public class BuyerDTO {
	 
	private String buyerId;
	@NotNull(message = "{userservice.emailid.absent}")
	@Email(message = "{userservice.emailid.invalid}")
	private String emailId;
	@NotNull(message = "{userservice.name.absent}")
	@Pattern(regexp ="[A-Za-z]+( [A-Za-z]+)*", message = "{userservice.name.invalid}")
	private String name;
	@NotNull(message = "{userservice.phoneNo.absent}")
	@Pattern(regexp = "^[0-9]{10}", message = "{userservice.phoneNo.invalid}")
	private String phoneNo;
	@NotNull(message = "{userservice.password.absent}")
	@Pattern(regexp ="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{7,20}", message = "{userservice.password.invalid}")
	private String password;
	private String isPrivilaged;
	private Integer rewardPoints;
	private String isactive;
	ProductsorderdDTO orders;
	public ProductsorderdDTO getProductsorderd() {
		return orders;
	}

	public void setProductsorderd(ProductsorderdDTO order) {
		this.orders = order;
	}
	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
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

	public String getIsPrivilaged() {
		return isPrivilaged;
	}

	public void setIsPrivilaged(String isPrivilaged) {
		this.isPrivilaged = isPrivilaged;
	}

	public Integer getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(Integer rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	

	@Override
	public String toString() {
		return "Buyer [phoneNo=" + phoneNo + ", name=" + name + ", emailId=" + emailId
				+ ", isPrivilaged=" + isPrivilaged + ", password=" + password + ", rewardPoints=" + rewardPoints
				+ ", isactive=" + isactive + "]";
	}

		// Converts Entity into DTO
		public static BuyerDTO valueOf(Buyer cust) {
			BuyerDTO custDTO = new BuyerDTO();
			custDTO.setBuyerId(cust.getBuyerId());
			custDTO.setPassword(cust.getPassword());
			custDTO.setEmailId(cust.getEmailId());
			custDTO.setName(cust.getName());
			custDTO.setPhoneNo(cust.getPhoneNo());
			custDTO.setRewardPoints(cust.getRewardPoints());
			custDTO.setIsPrivilaged(cust.getIsPrivilaged());
			custDTO.setIsactive(cust.getIsactive());
//			PlanDTO planDTO = new PlanDTO();
//			planDTO.setPlanId(cust.getPlanId());
//			custDTO.setCurrentPlan(planDTO);
//			custDTO.setCurrentPlan(planDTO);
			
			return custDTO;
		}

		// Converts DTO into Entity
		public Buyer createEntity() {
			Buyer cust = new Buyer();
			cust.setBuyerId(this.getBuyerId());
			cust.setPassword(this.getPassword());
			cust.setEmailId(this.getEmailId());
			cust.setName(this.getName());
			cust.setPhoneNo(this.getPhoneNo());
			cust.setRewardPoints(this.getRewardPoints());
			cust.setIsPrivilaged(this.getIsPrivilaged());
			cust.setIsactive(this.getIsactive());
			
			return cust;
		}


}
