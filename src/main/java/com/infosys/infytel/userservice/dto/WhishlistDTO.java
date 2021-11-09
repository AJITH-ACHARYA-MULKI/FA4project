package com.infosys.infytel.userservice.dto;

//import java.util.List;

//import com.infosys.infytel.userservice.entity.Buyer;
import com.infosys.infytel.userservice.entity.Whishlist;


public class WhishlistDTO {
	
	private String buyerId;
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

	@Override
	public String toString() {
		return "WhishlistDTO [buyerId=" + buyerId + ", prodId=" + prodId +  "]";
	}

		// Converts Entity into DTO
		public static WhishlistDTO valueOf(Whishlist cust) {
			WhishlistDTO custDTO = new WhishlistDTO();
			custDTO.setBuyerId(cust.getBuyerId());
			custDTO.setProdId(cust.getProdId());
			
			return custDTO;
		}
			
		// Converts DTO into Entity
		public Whishlist addProduct() {
			Whishlist cust = new Whishlist();
			cust.setBuyerId(this.getBuyerId());
			cust.setProdId(this.getProdId());
			return cust;
		}

		


}
