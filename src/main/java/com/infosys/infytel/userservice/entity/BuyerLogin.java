package com.infosys.infytel.userservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="buyer")
public class BuyerLogin {
	
	private String buyerId;
	@Column(name = "email_id",unique=true,columnDefinition="VARCHAR(64)", nullable = false)
	@Id
	private String emailId;
//	@Column(nullable = false, length = 50)
	private String name;
//	@Column(name = "phone_no", nullable = false)
	private Long phoneNo;
//	@Column(nullable = false, length = 50)
	private String password;
//	@Column(nullable = false)
	private String isPrivilaged;
//	@Column(nullable = false)
	private Integer rewardPoints;
//	@Column(nullable = false)
	private String isactive;


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
	
	public Long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(Long phoneNo) {
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

	public BuyerLogin() {
		super();
	}
}
