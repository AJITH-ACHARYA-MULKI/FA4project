package com.infosys.infytel.userservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="seller")
public class SellerLogin {
	
	@Column(name = "seller_id", nullable = false)
	private String sellerId;
	@Id
//	@Column(name = "email_id", nullable = false)
	private String emailId;
	@Column(nullable = false, length = 50)
	private String name;
	@Column(name = "phone_no", nullable = false)
	private long phoneNo;
	@Column(nullable = false, length = 50)
	private String password;
	@Column(nullable = false)
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

	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	public SellerLogin() {
		super();
	}
}
