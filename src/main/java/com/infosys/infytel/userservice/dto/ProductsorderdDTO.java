package com.infosys.infytel.userservice.dto;

public class ProductsorderdDTO {
	
	private String sellerId;
	private String buyerId;
	private Integer quantity;
	private String prodId;

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	
	public ProductsorderdDTO() {
		super();
	}

	
	public static ProductsorderdDTO placeOrder(CartDTO cartDTO, ProductDTO productDTO) {
		ProductsorderdDTO ordersDTO = new ProductsorderdDTO();
		ordersDTO.setBuyerId(cartDTO.getBuyerId());
		ordersDTO.setSellerId(productDTO.getSellerId());
		ordersDTO.setProdId(productDTO.getProdId());
		ordersDTO.setQuantity(cartDTO.getQuantity());
		return ordersDTO;
	}
	
	@Override
	public String toString() {
		return "ProductsorderdDTO [sellerId=" + sellerId + ", buyerId=" + buyerId
				+ ", quantity=" + quantity + ", prodId=" + prodId + "]";
	}
}
