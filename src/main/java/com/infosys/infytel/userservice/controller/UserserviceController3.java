package com.infosys.infytel.userservice.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.infosys.infytel.userservice.dto.ProductDTO;
import com.infosys.infytel.userservice.service.Userservice3;


@RestController
@CrossOrigin
@RequestMapping(value="api/visitor/product")
public class UserserviceController3 {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${product.uri}")
	String productUri;
	
	@Autowired
	public Userservice3 userService3;
	
//	list all seller details
	@RequestMapping(value = "/{phoneNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductDTO>> getAllProduct(@PathVariable String phoneNo) throws Exception {
		userService3.sellerState(phoneNo);
		List<ProductDTO> products=new RestTemplate().getForObject(productUri+"api/buyer/product", List.class);
		return new ResponseEntity<List<ProductDTO>>(products, HttpStatus.OK);
	}
// list product based on category
	@RequestMapping(value = "/{phoneNo}/category/{category}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductDTO>> getAllProduct2(@PathVariable String phoneNo, String category) throws Exception{
		userService3.sellerState(phoneNo);
		List<ProductDTO> products=new RestTemplate().getForObject(productUri+"api/buyer/product/"+category, List.class);
		return new ResponseEntity<List<ProductDTO>>(products, HttpStatus.OK);
	}

// list product based on product name
	@RequestMapping(value = "/{phoneNo}/{prodName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductDTO>> getAllProduct3(@PathVariable String phoneNo, String prodName) throws Exception{
		userService3.sellerState(phoneNo);
		List<ProductDTO> products=new RestTemplate().getForObject(productUri+"api/buyer/product/"+prodName, List.class);
		return new ResponseEntity<List<ProductDTO>>(products, HttpStatus.OK);
	}
}
