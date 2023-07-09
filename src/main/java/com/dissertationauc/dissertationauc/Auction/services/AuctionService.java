package com.dissertationauc.dissertationauc.Auction.services;
import com.dissertationauc.dissertationauc.Auction.model.Bidder;
import org.springframework.http.ResponseEntity;

public interface AuctionService {

     ResponseEntity getCurrentBid(Long id);

     ResponseEntity setBid(Long id,String userName, Double amount);


     ResponseEntity getAuction(Long id);

     ResponseEntity getAuctions();








}
