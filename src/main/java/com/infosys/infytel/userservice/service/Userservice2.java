package com.infosys.infytel.userservice.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.infosys.infytel.userservice.dto.CartDTO;
import com.infosys.infytel.userservice.dto.SellerDTO;
import com.infosys.infytel.userservice.dto.SellerLoginDTO2;
import com.infosys.infytel.userservice.dto.WhishlistDTO;
import com.infosys.infytel.userservice.entity.Cart;
import com.infosys.infytel.userservice.entity.Seller;
import com.infosys.infytel.userservice.entity.SellerLogin;
import com.infosys.infytel.userservice.entity.Whishlist;
import com.infosys.infytel.userservice.repository.UserserviceRepository;
import com.infosys.infytel.userservice.repository.UserserviceRepository2;
import com.infosys.infytel.userservice.repository.UserserviceRepository3;
import com.infosys.infytel.userservice.repository.UserserviceRepository4;
import com.infosys.infytel.userservice.repository.UserserviceRepositoryLogin;

@Service(value="seller")
@Transactional
public class Userservice2 {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	UserserviceRepository custRepo;
	
	@Autowired
	UserserviceRepository2 custRepo2;
	
	@Autowired
	UserserviceRepository3 custRepo3;
	
	@Autowired
	UserserviceRepository4 custRepo4;
	
	@Autowired
	UserserviceRepositoryLogin custRepo5;
	
	
//	seller state
	public String errormsg="Seller is Inactive. Please Login";
	public void sellerState(String sellerId) throws Exception{
		Seller cust = custRepo2.findById(sellerId).get();
		logger.info("isactive {} {}",cust.getIsactive(),sellerId);
		if(cust.getIsactive().equals("no"))
			throw new Exception(errormsg);
	}
	
// seller Login
	public void login(@Valid SellerLoginDTO2 sellerLoginDTO2) throws Exception{
		logger.info("Login request for seller {} with password {}", sellerLoginDTO2.getEmailId(),sellerLoginDTO2.getPassword());
		SellerLogin cust = custRepo5.findById(sellerLoginDTO2.getEmailId()).orElse(null);
		logger.info("Login request for seller {}",cust);
		if(cust == null)
			throw new Exception("Seller does not exits or Invalid credential...");
		if(!cust.getPassword().equals(sellerLoginDTO2.getPassword()))
			throw new Exception("Password is Incorrect ...");
		cust.setIsactive("yes");
		custRepo5.save(cust);		
	}
	
	public String addSeller(@Valid SellerDTO sellerDTO) throws Exception {
		Seller seller=custRepo2.findById(sellerDTO.getSellerId()).orElse(null);
		logger.info("register request for Sellerid {} with Sellerid {}", sellerDTO.getSellerId(), seller);
		if(seller!=null)
			throw new Exception("Seller ID Already exist...");
		Seller newSeller = sellerDTO.createEntity3();
		custRepo2.save(newSeller);
		return sellerDTO.getSellerId();
	}
	
	public void deleteSeller(String sellerId) {
		Seller seller=custRepo2.findById(sellerId).orElse(null);
		seller.setIsactive("no");
		custRepo2.save(seller);
	}
	
	public List<SellerDTO> getAllSeller() {
		List<Seller> sellers = custRepo2.findAll();
		List<SellerDTO> sellerDTOs = new ArrayList<SellerDTO>();
		for(Seller seller : sellers) {
			SellerDTO sellerDTO = SellerDTO.valueOf(seller);
			sellerDTOs.add(sellerDTO);
		}
		logger.info("Buyer details : {}", sellerDTOs);
		return sellerDTOs;
	}

	public void sellerExist(String sellerId) throws Exception{
		Seller seller=custRepo2.findById(sellerId).orElse(null);
		logger.info("seller id is {}",seller);
		if(seller==null)
			throw new Exception("seller is inactive");
	}
}
