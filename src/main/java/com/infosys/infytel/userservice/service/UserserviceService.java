package com.infosys.infytel.userservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosys.infytel.userservice.dto.CartDTO;
//import com.infosys.infytel.userservice.dto.BuyerDTO;
import com.infosys.infytel.userservice.dto.LoginDTO;
import com.infosys.infytel.userservice.dto.SellerLoginDTO2;
import com.infosys.infytel.userservice.dto.WhishlistDTO;
import com.infosys.infytel.userservice.entity.Buyer;
import com.infosys.infytel.userservice.entity.Cart;
import com.infosys.infytel.userservice.entity.Seller;
import com.infosys.infytel.userservice.entity.Whishlist;
import com.infosys.infytel.userservice.repository.UserserviceRepository;
import com.infosys.infytel.userservice.repository.UserserviceRepository2;
import com.infosys.infytel.userservice.repository.UserserviceRepository3;
import com.infosys.infytel.userservice.repository.UserserviceRepository4;

@Service
public class UserserviceService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserserviceRepository custRepo;
	
	@Autowired
	UserserviceRepository2 custRepo2;
	
	@Autowired
	UserserviceRepository3 custRepo3;
	
	@Autowired
	UserserviceRepository4 custRepo4;
	
//	public void createCustomer(BuyerDTO custDTO) {
//		logger.info("Creation request for customer {}", custDTO);
//		Buyer cust = custDTO.createEntity();
//		custRepo.save(cust);
//	}

	// Login
	
	public boolean login(LoginDTO loginDTO) {
		logger.info("Login request for buyer {} with password {}", loginDTO.getEmailId(),loginDTO.getPassword());
		Buyer cust = custRepo.findById(loginDTO.getEmailId()).get();
		if (cust != null && cust.getPassword().equals(loginDTO.getPassword())) {
			return true;
		}
		return false;
	}
	
	public boolean login2(SellerLoginDTO2 loginDTO) {
		logger.info("Login request for seller {} with password {}", loginDTO.getEmailId(),loginDTO.getPassword());
		Seller cust = custRepo2.findById(loginDTO.getEmailId()).get();
		if (cust != null && cust.getPassword().equals(loginDTO.getPassword())) {
			return true;
		}
		return false;
	}

	public void addToWhishList(WhishlistDTO whishlistDTO) {
		logger.info("creating new whishlist {}",whishlistDTO);
		Whishlist product = whishlistDTO.addProduct();
		logger.info("product whishlist {}",product);
		custRepo3.save(product);
	}

	public void addToCart(CartDTO cartDTO) {
		logger.info("creating new cart {}",cartDTO);
		Cart product = cartDTO.addToCart();
		logger.info("whishlist to cart {}",product);
		String product2=product.getProdId();
		custRepo4.save(product);
		custRepo3.deleteById(product2);
	}

	public void addProductToCart(CartDTO cartDTO) {
		logger.info("creating new cart {}",cartDTO);
		Cart product = cartDTO.addToCart();
		logger.info("product cart {}",product);
		custRepo4.save(product);
	}

	public void deletProductFromWhishlist(String prodId) {
		custRepo3.deleteById(prodId);		
	}
	
//	
//	public BuyerDTO getCustomerProfile(Long phoneNo) {
//
//		logger.info("Profile request for customer {}", phoneNo);
//		Buyer cust = custRepo.findById(phoneNo).get();
//		BuyerDTO custDTO = BuyerDTO.valueOf(cust);
//
//		logger.info("Profile for customer : {}", custDTO);
//		return custDTO;
//	}


}
