package com.infosys.infytel.userservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="whishlist")
public class Whishlist {
	@Id
	@Column(name = "buyer_id", nullable = false)
	private String buyerId;
	@Column(name = "prod_id", nullable = false)
	private String prodId;
	
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

	public Whishlist() {
		super();
	}
}
