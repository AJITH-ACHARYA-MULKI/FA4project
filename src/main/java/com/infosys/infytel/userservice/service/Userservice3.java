package com.infosys.infytel.userservice.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.infosys.infytel.userservice.repository.UserserviceRepository;
import com.infosys.infytel.userservice.repository.UserserviceRepository2;



@Service(value="visitor")
@Transactional
public class Userservice3 {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	UserserviceRepository custRepo;
	
	@Autowired
	UserserviceRepository2 custRepo2;

//	visitor validation by phoneNo.
	public void sellerState(String phoneNo) throws Exception{
		Query query = entityManager.createQuery("SELECT b FROM Buyer b WHERE b.phoneNo=:phoneNo");
		query.setParameter("phoneNo",phoneNo);
		if(!query.getResultList().isEmpty())
			throw new Exception("Phone Number Already Exist in Buyer Details");
		Query query2 = entityManager.createQuery("SELECT o FROM Seller o WHERE o.phoneNo=:phoneNo");
		query2.setParameter("phoneNo",phoneNo);
		if(!query2.getResultList().isEmpty())
			throw new Exception("Phone Number Already Exist in seller Details");		
	}
}
