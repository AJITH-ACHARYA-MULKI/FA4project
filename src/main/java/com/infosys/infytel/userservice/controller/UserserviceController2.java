package com.infosys.infytel.userservice.controller;

import java.util.LinkedHashMap;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.infosys.infytel.userservice.dto.CartDTO;
import com.infosys.infytel.userservice.dto.OrdersDTO;
import com.infosys.infytel.userservice.dto.ProductDTO;
import com.infosys.infytel.userservice.dto.SellerDTO;
import com.infosys.infytel.userservice.dto.SellerLoginDTO2;
import com.infosys.infytel.userservice.service.Userservice;
import com.infosys.infytel.userservice.service.Userservice2;

@RestController
@CrossOrigin
@RequestMapping(value="api/seller")
public class UserserviceController2 {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public Userservice2 userService2;
	
	@Autowired
	public Userservice userService;
	
	@Autowired
	private Environment environment;
	
	@Value("${product.uri}")
	String productUri;

	@Value("${order.uri}")
	String orderUri;



//	 seller Login
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(@Valid @RequestBody SellerLoginDTO2 sellerLoginDTO2) throws Exception {
		logger.info("Login request for seller {} with password {}", sellerLoginDTO2.getEmailId(),sellerLoginDTO2.getPassword());
		userService2.login(sellerLoginDTO2);
		return new ResponseEntity<String>("successfuly loged in", HttpStatus.OK);
	}
//	seller registration
	@PostMapping(value = "/register")
	public ResponseEntity<String> register(@Valid @RequestBody SellerDTO sellerDTO) throws Exception {
		logger.info("Register request for sellerid {}",sellerDTO);
		String sellerid=userService2.addSeller(sellerDTO);
		String message = environment.getProperty("API.INSERT_SUCCESS")+sellerid;
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}
	
//	list all seller details
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SellerDTO> getAllSeller() {
		logger.info("Fetching all buyer details");
		return userService2.getAllSeller();
	}
	
	
//	Remove Seller account
	@RequestMapping(value = "/diactivate/{sellerId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteSeller(@PathVariable String sellerId) throws Exception{
		logger.info("deleting seller from {}", sellerId);
		userService2.sellerExist(sellerId);
		userService2.sellerState(sellerId);
		userService2.deleteSeller(sellerId);
		return new ResponseEntity<String>("Deleted Successfuly "+sellerId, HttpStatus.OK);
	}
	
//	add new product
	@RequestMapping(value = "/product", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addProduct(@RequestBody ProductDTO productDTO) throws Exception {
		logger.info("Adding new product {} ",productDTO);
		userService2.sellerExist(productDTO.getSellerId());
		userService2.sellerState(productDTO.getSellerId());
		List<LinkedHashMap> message=new RestTemplate().postForObject(productUri+"api/seller/product", productDTO, List.class);
		return new ResponseEntity<String>("Successfuly added..", HttpStatus.OK);
	}
	
// delete product
	@RequestMapping(value = "/product/{prodId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteProduct(@PathVariable String prodId) throws Exception {
		logger.info("Deleting product {} ",prodId);
		String sellerId= new RestTemplate().getForObject(productUri+"api/seller/product/id/"+prodId,String.class);
		userService2.sellerExist(sellerId);
		userService2.sellerState(sellerId);
		return new RestTemplate().getForObject(productUri+"api/seller/product/"+prodId,String.class);		
	}
	
//	view orders placed on their product
	@RequestMapping(value = "/product/order/{sellerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LinkedHashMap>> viewProducts(@PathVariable String sellerId) throws Exception {
		userService2.sellerExist(sellerId);
		userService2.sellerState(sellerId);
		logger.info("view orders of seller id {} ",sellerId);
		@SuppressWarnings("unchecked")
		List<LinkedHashMap> orders=new RestTemplate().getForObject(orderUri+"api/seller/product/oreders/"+sellerId, List.class);
		logger.info("list orders of seller id {} ",orders);
		return new ResponseEntity<List<LinkedHashMap>>(orders, HttpStatus.OK);
	}

	
// change order status
	@RequestMapping(value = "/order/{orderId}/{status}", method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> changeStatus(@PathVariable String orderId, String status) throws Exception {
		String orders=new RestTemplate().getForObject(orderUri+"api/seller/oreder/"+orderId+"/"+status, String.class);
		return new ResponseEntity<String>(orders, HttpStatus.OK);
	}
	
}
