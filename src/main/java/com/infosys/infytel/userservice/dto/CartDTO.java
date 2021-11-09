package com.infosys.infytel.userservice.dto;

//import java.util.List;

//import com.infosys.infytel.userservice.entity.Buyer;
import com.infosys.infytel.userservice.entity.Cart;
//import com.infosys.infytel.userservice.entity.Whishlist;


public class CartDTO {

	private String buyerId;
	private String prodId;
	private Integer quantity;
	private String address;
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

	@Override
	public String toString() {
		return "CartDTO [buyerId=" + buyerId + ", prodId=" + prodId +  "]";
	}

		// Converts Entity into DTO
		public static CartDTO valueOf(Cart cust) {
			CartDTO custDTO = new CartDTO();
			custDTO.setBuyerId(cust.getBuyerId());
			custDTO.setProdId(cust.getProdId());
			cust.setQuantity(cust.getQuantity());
			return custDTO;
		}

		public Cart addToCart() {
			Cart cust = new Cart();
			cust.setBuyerId(this.getBuyerId());
			cust.setProdId(this.getProdId());
			cust.setQuantity(this.getQuantity());
			return cust;
		}

		
		public static CartDTO newValueOf(Cart cust) {
			CartDTO custDTO = new CartDTO();
			custDTO.setBuyerId(cust.getBuyerId());
			custDTO.setProdId(cust.getProdId());
			cust.setQuantity(cust.getQuantity());
			return custDTO;
		}

		public String getAddress() {
			return address;
		}
		


}
