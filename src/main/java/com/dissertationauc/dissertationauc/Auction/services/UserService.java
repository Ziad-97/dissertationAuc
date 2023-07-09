package com.dissertationauc.dissertationauc.Auction.services;

import com.dissertationauc.dissertationauc.Auction.data.AddFundsData;
import com.dissertationauc.dissertationauc.Auction.data.BidderData;
import com.dissertationauc.dissertationauc.Auction.data.ItemData;
import com.dissertationauc.dissertationauc.Auction.model.Bidder;
import com.dissertationauc.dissertationauc.Auction.model.Item;
import org.springframework.http.ResponseEntity;

public interface UserService  {
    ResponseEntity loginService(String name, String password);

    ResponseEntity logoutService(String usrname);

    ResponseEntity registerService(BidderData user);

    ResponseEntity getUserByUsername(String uname);

    ResponseEntity getUserByEmail(String email);

    ResponseEntity addFunds(AddFundsData data);







}
