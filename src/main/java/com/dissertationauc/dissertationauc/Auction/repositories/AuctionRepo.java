package com.dissertationauc.dissertationauc.Auction.repositories;

import com.dissertationauc.dissertationauc.Auction.model.Auction;
import com.dissertationauc.dissertationauc.Auction.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuctionRepo extends JpaRepository<Auction, Long> {


    List<Auction> findAllByOpen(Boolean isOpen);

    List<Auction> findAllByBidderNameAndOpen(String bidderName, Boolean open);

    Optional<Auction> findByAuctionNameAndOpenAndAuctionItem(String auctionName, Boolean open, Item auctionItem);

    Optional<Auction> findByAuctionItemAndOpen(Item auctionItem, Boolean open);


    /*
    *
    * change something in the JPA respoitory, to suit the auction based of the user.*/

}
