package com.dissertationauc.dissertationauc.Auction.services;

import com.dissertationauc.dissertationauc.Auction.data.BidderData;
import com.dissertationauc.dissertationauc.Auction.data.BidderResponse;
import com.dissertationauc.dissertationauc.Auction.data.ItemData;
import com.dissertationauc.dissertationauc.Auction.data.ItemResponse;
import com.dissertationauc.dissertationauc.Auction.exception.InvalidEmailException;
import com.dissertationauc.dissertationauc.Auction.exception.InvalidUserNameException;
import com.dissertationauc.dissertationauc.Auction.exception.UserAlreadyExistsException;
import com.dissertationauc.dissertationauc.Auction.exception.UserNotFoundException;
import com.dissertationauc.dissertationauc.Auction.model.Bidder;
import com.dissertationauc.dissertationauc.Auction.model.Item;
import com.dissertationauc.dissertationauc.Auction.repositories.ItemRepo;
import com.dissertationauc.dissertationauc.Auction.repositories.BidderRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepo itemRepo;
    @Override
    public ResponseEntity sellItems(Double price) {
        Item item = itemRepo.findByItem(price);

        if(item!= null) {
            return ResponseEntity.ok().body(itemDataMapper(item));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    private ItemResponse itemDataMapper(Item item){
        ItemResponse itemData = new ItemResponse();

        itemData.setId(item.getId());
        itemData.setPrice(item.getPrice());
        itemData.setName(item.getName());


        return itemData;
    }
    @Override
    public ResponseEntity addItems(ItemData data) {
        Item item = new Item();

        item.setPrice(data.getPrice());
        item.setName(data.getName());
        itemRepo.save(item);

        return ResponseEntity.ok().body(itemDataMapper(item));




    }

}
