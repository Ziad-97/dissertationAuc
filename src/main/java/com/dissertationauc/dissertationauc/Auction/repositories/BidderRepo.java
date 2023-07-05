package com.dissertationauc.dissertationauc.Auction.repositories;

import com.dissertationauc.dissertationauc.Auction.model.Bidder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidderRepo extends JpaRepository<Bidder, Long> {
    Bidder findByUserName(String name);

    Bidder findByEmail(String email);

    Bidder findByCode(String code);

    Bidder findByUserNameAndEmail(String name, String email);

    List<Bidder> findByUserNameOrEmail(String name, String email);

}
