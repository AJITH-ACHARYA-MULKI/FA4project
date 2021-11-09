package com.infosys.infytel.userservice.dto;

import java.time.LocalDate;
import java.util.Date;

import com.infosys.infytel.userservice.entity.Cart;

public class OrdersDTO {

	private String orderId;
	private String buyerId;
	private Float amount;
	private LocalDate date;
	private String address;
	private String status;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate localDate) {
		this.date = localDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public OrdersDTO() {
		super();
	}

	public static OrdersDTO placeOrder(CartDTO cartDTO, ProductDTO productDTO) {
		OrdersDTO ordersDTO = new OrdersDTO();
		ordersDTO.setBuyerId(cartDTO.getBuyerId());
		ordersDTO.setAmount((float)productDTO.getPrice()*cartDTO.getQuantity()+40);
		ordersDTO.setDate(java.time.LocalDate.now());
		ordersDTO.setAddress("");
		ordersDTO.setStatus("Order Placed");
		return ordersDTO;
	}
	
	
	@Override
	public String toString() {
		return "OrderDTO [buyerId=" + buyerId + ", amount=" + amount + ", date=" + date
				+ ", address=" + address + ", status=" + status + "]";
	}

}
