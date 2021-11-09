
package com.infosys.infytel.userservice;

import java.util.List;

import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.infosys.infytel.userservice.service.Userservice;
import com.infosys.infytel.userservice.service.Userservice2;
import com.infosys.infytel.userservice.service.Userservice3;
import com.infosys.infytel.userservice.dto.BuyerDTO;
import com.infosys.infytel.userservice.dto.LoginDTO;
import com.infosys.infytel.userservice.dto.SellerDTO;
import com.infosys.infytel.userservice.entity.BuyerLogin;
import com.infosys.infytel.userservice.repository.UserserviceRepository2;
import com.infosys.infytel.userservice.repository.UserserviceRepositoryLogin2;

import static org.junit.Assert.assertTrue;
//import com.infosys.infytel.userservice.dto.WhishlistDTO;
import static org.junit.jupiter.api.Assertions.assertThrows;


@RunWith(SpringRunner.class)
@SpringBootTest
class UserserviceApplicationTests {
	@SuppressWarnings("deprecation")
	@Rule
	public ExpectedException exc=ExpectedException.none();
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Mock
	Userservice userService=new Userservice();
	@Mock
	Userservice3 userService3=new Userservice3();
	
	@Mock
	UserserviceRepositoryLogin2 repo;
	@Mock
	UserserviceRepository2 repo2;
	
	@Mock
	Userservice2 userService2=new Userservice2();

	@Test
	public void login(){
		LoginDTO buyerDTO=new LoginDTO();
		buyerDTO.setEmailId("ajith@gmail.com");
		buyerDTO.setPassword("Ajith@99720");
		Exception exception = Assertions.assertThrows(Exception.class, () -> userService.login(buyerDTO));
		logger.info(exception.getMessage());
	}
	
	@Test
	public void sellerExist(){
		Exception exception = Assertions.assertThrows(Exception.class, () -> userService2.sellerExist("S109"));
		logger.info(exception.getMessage());
	}
	
	@Test
	public void sellerState(){
		Exception exception = Assertions.assertThrows(Exception.class, () -> userService3.sellerState("9902182145"));
		logger.info("API.INSERT_SUCCESS", exception.getMessage());
	}
	@Test
	public void isPrivileged() {
		Exception exception = Assertions.assertThrows(Exception.class, () -> userService.isPrivileged("B101"));
		logger.info(exception.getMessage());
	}
	
	@Test
	public void buyerrExist()  {
		Exception exception = assertThrows(Exception.class, () -> userService.buyerrExist("B109"),"Expected doThing() to throw, but it didn't");
		assertTrue(exception.getMessage().contains("Stuff"));
		logger.info(exception.getMessage());
	}
	
	
}
