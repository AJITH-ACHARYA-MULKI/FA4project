package com.infosys.infytel.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.infosys.infytel.userservice.entity.Whishlist;

public interface UserserviceRepository3 extends JpaRepository<Whishlist, String> {
	@Modifying
    @Query("DELETE FROM Whishlist o WHERE o.buyerId=:buyerId AND o.prodId=:prodId")
    public int deleteWhishlistItem(@Param("buyerId")String buyerId, @Param("prodId")String prodId);

}

