package com.dissertationauc.dissertationauc.Auction.repositories;

import com.dissertationauc.dissertationauc.Auction.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionRepo extends JpaRepository<Auction, Long> {


    List<Auction> findAllByOpen(Boolean isOpen);


    /*
    *
    * change something in the JPA respoitory, to suit the auction based of the user.*/

}
