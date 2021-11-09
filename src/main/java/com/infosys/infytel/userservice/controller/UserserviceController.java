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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.infosys.infytel.userservice.dto.BuyerDTO;
import com.infosys.infytel.userservice.dto.CartDTO;
import com.infosys.infytel.userservice.dto.LoginDTO;
import com.infosys.infytel.userservice.dto.OrdersDTO;
import com.infosys.infytel.userservice.dto.ProductDTO;
import com.infosys.infytel.userservice.dto.WhishlistDTO;
import com.infosys.infytel.userservice.entity.Cart;
import com.infosys.infytel.userservice.service.Userservice;

@RestController
@CrossOrigin
@RequestMapping(value="api/buyer")
public class UserserviceController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public Userservice userService;
	
	@Autowired
	private Environment environment;
	
	@Value("${product.uri}")
	String productUri;

	@Value("${order.uri}")
	String orderUri;
	
////	kafka endpoints
//	@PostMapping(value="/post")
//	public void sendMessage(@RequestParam("msg") String msg) {
//		userService.publishToTopic(msg);
//	}

//	 Buyer Login
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDTO) throws Exception {
		logger.info("Login request for buyer {} with password {}", loginDTO.getEmailId(),loginDTO.getPassword());
		userService.login(loginDTO);
		return new ResponseEntity<String>("Loged In Successfully", HttpStatus.OK);
	}
//	buyer registration
	@PostMapping(value = "/register")
	public ResponseEntity<String> register(@Valid @RequestBody BuyerDTO buyerDTO) throws Exception {
		logger.info("Register request for buy {}",buyerDTO);
		String buyerid=userService.addBuyer(buyerDTO);
		String message = environment.getProperty("API.INSERT_SUCCESS")+buyerid;
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}
	
	
//	add products from product details page to whishlist
	@RequestMapping(value = "/product/whishlist", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addToWhishList(@RequestBody WhishlistDTO whishlistDTO) throws Exception {
		logger.info("Adding new product {} to WhishList ",whishlistDTO);
		ProductDTO product=new RestTemplate().getForObject(productUri+"api/buyer/product/"+whishlistDTO.getProdId(),ProductDTO.class);
		String message=userService.addToWhishList(whishlistDTO);
		return new ResponseEntity<String>("Product id "+message+" added to whishlist..", HttpStatus.CREATED);
	}
	
//	remove product from wishlist and add to cart
	@RequestMapping(value = "/whishlist/cart", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addWishlistToCart(@RequestBody CartDTO cartDTO) throws Exception{
		logger.info("whishlist item {} moved to cart ",cartDTO);
		userService.addToCart(cartDTO);
		ProductDTO product=new RestTemplate().getForObject(productUri+"api/buyer/product/"+cartDTO.getProdId()+"/quantity/"+cartDTO.getQuantity(),ProductDTO.class);
		float price=cartDTO.getQuantity()*product.getPrice();
		if(userService.isPrivileged(cartDTO.getBuyerId()))
			price=price-40-(userService.getReward(cartDTO.getBuyerId())/4);
		userService.addReward((int)Math.floor(price/100),cartDTO.getBuyerId());
		userService.publishToTopic(cartDTO.getBuyerId()+"/"+cartDTO.getQuantity()+"/"+cartDTO.getProdId()+"/"+product.getSellerId()+"/"+Float.toString(price)+"/"+cartDTO.getAddress());
		return new ResponseEntity<String>("Successfuly placed an order...", HttpStatus.CREATED);	
	}
	
//	add product fromproduct page to cart
	@RequestMapping(value = "/product/cart", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addToCart(@RequestBody CartDTO cartDTO) throws Exception{
		logger.info("Adding new product {} to cart ",cartDTO);
		userService.addToCart(cartDTO);
		ProductDTO product=new RestTemplate().getForObject(productUri+"api/buyer/product/"+cartDTO.getProdId()+"/quantity/"+cartDTO.getQuantity(),ProductDTO.class);
		float price=cartDTO.getQuantity()*product.getPrice();
		if(userService.isPrivileged(cartDTO.getBuyerId()))
			price=price-40-(userService.getReward(cartDTO.getBuyerId())/4);
		userService.addReward((int)Math.floor(price/100),cartDTO.getBuyerId());
		userService.publishToTopic(cartDTO.getBuyerId()+"/"+cartDTO.getQuantity()+"/"+cartDTO.getProdId()+"/"+product.getSellerId()+"/"+Float.toString(price)+"/"+cartDTO.getAddress());
		return new ResponseEntity<String>("Successfuly placed an order...", HttpStatus.CREATED);
	}
	
//	Remove product from whishlist
	@RequestMapping(value = "/cart/{prodId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deletProductFromWhishlist(@PathVariable String prodId) throws Exception{
		logger.info("deleting product from whishlist add to cart {}", prodId);
		userService.deletProductFromWhishlist(prodId);
		return new ResponseEntity<String>("Product removed succesfully", HttpStatus.CREATED);
	}
	
//	Remove buyer account
	@RequestMapping(value = "/diactivate/{buyerId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteBuyer(@PathVariable String buyerId) throws Exception {
		logger.info("deleting buyer {}", buyerId);
		userService.deleteBuyer(buyerId);
		return new ResponseEntity<String>("buyer is in active", HttpStatus.OK);
	}
	
//	list all buyer details
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BuyerDTO> getAllBuyyer() {
		logger.info("Fetching all buyer details");
		return userService.getAllBuyers();
	}
	
// Fetches product ordered of a specific buyer
	@RequestMapping(value = "/productsordered/{buyerID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<LinkedHashMap> getCustomerProfile(@PathVariable String buyerID) {	
		logger.info("product ordered by buyer id {}", buyerID);
		@SuppressWarnings("unchecked")
		List<LinkedHashMap> ob=new RestTemplate().getForObject(orderUri+"api/buyer/productsordered/"+buyerID, List.class);
		logger.info("Fetching all order details{}",ob);		
		return ob;
//	
	}
	
//	cart quantity and address validation
	@RequestMapping(value = "/cart/validate/{buyerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> cartValidate(@PathVariable String buyerID) throws Exception{
		userService.buyerrExist(buyerID);
		userService.buyerState(buyerID);
		List<Cart> carts=userService.cartValidate();
		for(Cart cart : carts) {
			CartDTO cartDTO = CartDTO.valueOf(cart);
			Integer stock=new RestTemplate().getForObject(productUri+"api/buyer/stock/"+cartDTO.getBuyerId(),Integer.class);
			String address=new RestTemplate().getForObject(orderUri+"api/buyer/address/"+cartDTO.getBuyerId(),String.class);
			logger.info("stock {} address{}",stock,address);
			if(cartDTO.getQuantity()>stock)
				throw new Exception("Out of stock for product id"+cartDTO.getProdId());
			if(!address.matches("^.{1,100}$"))
				throw new Exception("Buyer address is invalid for :"+cartDTO.getBuyerId());
		}
		return new ResponseEntity<String>("all cart details are valid", HttpStatus.OK);
	}
	
// reordering product which is already ordered by buyer
	@RequestMapping(value = "/product/reorder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> reOrder(@PathVariable String buyerID, String prodId) throws Exception{
		userService.buyerrExist(buyerID);
		userService.buyerState(buyerID);
		String message=new RestTemplate().getForObject(orderUri+"api/buyer/reorder/"+buyerID+"/"+prodId,String.class);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
 
// buyer opt-in privileged mode, if buyer has 10000 reward points
	@RequestMapping(value = "/privilageMode", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> buyerPrivilageMode() throws Exception{
		 userService.buyerPrivilageMode();
		return new ResponseEntity<String>("Buyer data Updated...", HttpStatus.OK);
	}
}
