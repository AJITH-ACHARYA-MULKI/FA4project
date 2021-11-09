package com.infosys.infytel.userservice.repository;


//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infosys.infytel.userservice.entity.Buyer;

public interface UserserviceRepository extends JpaRepository<Buyer, String> {

}

