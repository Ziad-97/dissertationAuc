package com.dissertationauc.dissertationauc.Auction.services;

import com.dissertationauc.dissertationauc.Auction.data.ItemData;
import com.dissertationauc.dissertationauc.Auction.model.Item;
import org.springframework.http.ResponseEntity;

public interface ItemService {

    ResponseEntity sellItems(Double name);


    ResponseEntity addItems(ItemData name);
}
