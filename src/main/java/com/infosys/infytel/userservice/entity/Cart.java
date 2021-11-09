package com.infosys.infytel.userservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cart {
	
	@Column(name = "buyer_id", nullable = false)
	private String buyerId;
	@Column(name = "prod_id", nullable = false)
	@Id
	private String prodId;
	@Column(nullable = false)
	private Integer quantity;
	
	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Cart() {
		super();
	}
}
