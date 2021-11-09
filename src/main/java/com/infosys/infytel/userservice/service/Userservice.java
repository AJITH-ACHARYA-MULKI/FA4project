package com.infosys.infytel.userservice.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.infosys.infytel.userservice.dto.BuyerDTO;
import com.infosys.infytel.userservice.dto.CartDTO;
import com.infosys.infytel.userservice.dto.LoginDTO;
import com.infosys.infytel.userservice.dto.WhishlistDTO;
import com.infosys.infytel.userservice.entity.Buyer;
import com.infosys.infytel.userservice.entity.BuyerLogin;
import com.infosys.infytel.userservice.entity.Cart;
import com.infosys.infytel.userservice.entity.Whishlist;
import com.infosys.infytel.userservice.repository.UserserviceRepository;
import com.infosys.infytel.userservice.repository.UserserviceRepository2;
import com.infosys.infytel.userservice.repository.UserserviceRepository3;
import com.infosys.infytel.userservice.repository.UserserviceRepository4;
import com.infosys.infytel.userservice.repository.UserserviceRepositoryLogin2;

@Service(value="buyer")
@Transactional
public class Userservice {
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
	UserserviceRepositoryLogin2 custRepo5;
	
	
	@Autowired 
	private KafkaTemplate<String, String> kafkaTemp;
	
    public static final String topic = "mytopic";
	  
	
//	Kafka producer 
	  public void publishToTopic(String message) {
		  System.out.println("Publishing to topic "+topic);
		  this.kafkaTemp.send(topic, message);
	  }
	
//	buyer state
	public String errormsg="Buyer is Inactive. Please Login";
	public void buyerState(String buyerID) throws Exception{
		Buyer cust = custRepo.findById(buyerID).orElse(null);
		logger.info("Buyer isactive {} {}",cust.getIsactive(),buyerID);
		if(cust.getIsactive().equals("no"))
			throw new Exception("Buyer Is inactive");
	}
	
// Buyer exist
	public void buyerrExist(String buyerId) throws Exception{
		Buyer buyer=custRepo.findById(buyerId).orElse(null);
		logger.info("Buyer id is {}",buyer);
		if(buyer==null)
			throw new Exception("Buyer Id does not exist");
	}

// Buyer privileged
	public boolean isPrivileged(String buyerId) {
		Buyer buyer=custRepo.findById(buyerId).orElse(null);
		if(buyer.getIsPrivilaged().equals("yes"))
			return true;
		return false;
	}
	
// Buyer Login
	public void login(@Valid LoginDTO loginDTO) throws Exception {
		logger.info("Login request for buyer {} with password {}", loginDTO.getEmailId(),loginDTO.getPassword());
		BuyerLogin cust = custRepo5.findById(loginDTO.getEmailId()).orElse(null);
		if(cust == null)
			throw new Exception("Buyer does not exits. Invalid credential...");
		if(loginDTO.getEmailId() == null)
			throw new Exception("EmailId is Incorrect...");
		if(!cust.getPassword().equals(loginDTO.getPassword()))
			throw new Exception("Password is Incorrect ...");
		cust.setIsactive("yes");
		custRepo5.save(cust);
	}

//	checking wishlist, whether product already added to wishlist
	public void checkWhishlist(String buyerId, String prodId) throws Exception {
		Query query = entityManager.createQuery("SELECT o FROM Whishlist o WHERE o.buyerId=:buyerId AND o.prodId=:prodId");
		query.setParameter("buyerId",buyerId);
		query.setParameter("prodId",prodId);
		if(query.getResultList().isEmpty())
			throw new Exception("Product doesnot exist in whishlist...");
	}
	
//	checking cartDTO, whether product already ordered by buyer
	public void checkCart(String buyerId, String prodId) throws Exception {
		Query query = entityManager.createQuery("SELECT o FROM Cart o WHERE o.buyerId=:buyerId AND o.prodId=:prodId");
		query.setParameter("buyerId",buyerId);
		query.setParameter("prodId",prodId);
		if(!query.getResultList().isEmpty())
			throw new Exception("Product Already exist in cart...");
	}
	
	
	public String addToWhishList(WhishlistDTO whishlistDTO) throws Exception {	
		buyerrExist(whishlistDTO.getBuyerId());
		buyerState(whishlistDTO.getBuyerId());
		checkWhishlist(whishlistDTO.getBuyerId(),whishlistDTO.getProdId());
		Whishlist product = whishlistDTO.addProduct();
		custRepo3.save(product);
		return whishlistDTO.getProdId();
	}

	public void addToCart(CartDTO cartDTO) throws Exception{
		buyerrExist(cartDTO.getBuyerId());
		buyerState(cartDTO.getBuyerId());
		checkWhishlist(cartDTO.getBuyerId(),cartDTO.getProdId());
		checkCart(cartDTO.getBuyerId(),cartDTO.getProdId());
		Cart product = cartDTO.addToCart();
		logger.info("creating new cart {}",cartDTO);
		custRepo4.save(product);
		custRepo3.deleteWhishlistItem(cartDTO.getBuyerId(),cartDTO.getProdId());
	}

	public void deletProductFromWhishlist(String prodId) throws Exception{
		Cart whishlist=custRepo4.findById(prodId).orElse(null);
		if(whishlist==null)
			throw new Exception("Product does not exist");
		buyerrExist(whishlist.getBuyerId());
		buyerState(whishlist.getBuyerId());
		custRepo4.deleteById(prodId);
	}

	public String addBuyer(@Valid BuyerDTO buyerDTO) throws Exception {
		buyerrExist(buyerDTO.getBuyerId());
		Buyer newbuyer = buyerDTO.createEntity();
		custRepo.save(newbuyer);
		return buyerDTO.getBuyerId();
	}

	public void deleteBuyer(String buyerId) throws Exception{
		Buyer buyer=custRepo.findById(buyerId).orElse(null);
		buyerrExist(buyerId);
		buyerState(buyerId);
		buyer.setIsactive("no");
		custRepo.save(buyer);
	}

	
	public List<BuyerDTO> getAllBuyers() {
		List<Buyer> buyers = custRepo.findAll();
		List<BuyerDTO> buyerDTOs = new ArrayList<BuyerDTO>();
		for(Buyer buyer : buyers) {
			BuyerDTO buyerDTO = BuyerDTO.valueOf(buyer);
			buyerDTOs.add(buyerDTO);
		}
		logger.info("Buyer details : {}", buyerDTOs);
		return buyerDTOs;
	}


	public List<Cart> cartValidate() {
		List<Cart> carts = custRepo4.findAll();
		logger.info("All product details : {}", carts);
		return carts;
	}

	public void addReward(Integer reward, String buyerId) {
		Buyer buyer=custRepo.findById(buyerId).orElse(null);
		buyer.setRewardPoints(buyer.getRewardPoints()+reward);
		custRepo.save(buyer);
	}
	
//	get reward points
	public Integer getReward(String buyerId) {
		Buyer buyer=custRepo.findById(buyerId).orElse(null);
		return buyer.getRewardPoints();
	}

	public void buyerPrivilageMode() {
		List<Buyer> buyers = custRepo.findAll();
		for(Buyer buyer : buyers) {
			if(buyer.getRewardPoints()>=10000) {
				buyer.setIsPrivilaged("yes");
				buyer.setRewardPoints(buyer.getRewardPoints()-10000);
				custRepo.save(buyer);}
		}
	}
}
