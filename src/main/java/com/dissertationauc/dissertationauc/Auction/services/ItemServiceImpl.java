package com.dissertationauc.dissertationauc.Auction.services;

import com.dissertationauc.dissertationauc.Auction.data.BidderData;
import com.dissertationauc.dissertationauc.Auction.data.BidderResponse;
import com.dissertationauc.dissertationauc.Auction.data.ItemData;
import com.dissertationauc.dissertationauc.Auction.data.ItemResponse;
import com.dissertationauc.dissertationauc.Auction.exception.InvalidEmailException;
import com.dissertationauc.dissertationauc.Auction.exception.InvalidUserNameException;
import com.dissertationauc.dissertationauc.Auction.exception.UserAlreadyExistsException;
import com.dissertationauc.dissertationauc.Auction.exception.UserNotFoundException;
import com.dissertationauc.dissertationauc.Auction.model.Auction;
import com.dissertationauc.dissertationauc.Auction.model.Bidder;
import com.dissertationauc.dissertationauc.Auction.model.Item;
import com.dissertationauc.dissertationauc.Auction.repositories.AuctionRepo;
import com.dissertationauc.dissertationauc.Auction.repositories.ItemRepo;
import com.dissertationauc.dissertationauc.Auction.repositories.BidderRepo;
import com.dissertationauc.dissertationauc.Auction.utils.ObjectDataMapper;
import com.dissertationauc.dissertationauc.Auction.utils.ThreadContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dissertationauc.dissertationauc.Auction.utils.ObjectDataMapper.*;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepo itemRepo;

    @Autowired
    AuctionRepo auctionRepo;

    @Autowired
    BidderRepo bidderRepo;

    @Override
    public ResponseEntity sellItems(ItemData data) {
        Item item = itemRepo.findByName(data.getName());
        String userName = ThreadContext.getThreadContextData().getUserName();

        Bidder bidder = bidderRepo.findByUserName(userName);

        if(item!= null && item.getUser()==bidder) {

            Auction auction = new Auction();
            auction.setAuctionItem(item);
            auction.setAuctionName(item.getName());
            auction.setOpen(true);
            auction.setOpeningTime(LocalDateTime.now());

            auction= auctionRepo.save(auction);


            return ResponseEntity.ok().body(auctionDataMapper(auction));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }



    @Override
    public ResponseEntity addItems(ItemData data) {
        Item item = new Item();
        String userName = ThreadContext.getThreadContextData().getUserName();

      Bidder bidder = bidderRepo.findByUserName(userName);

        if(bidder!=null) {


            item.setPrice(data.getPrice());
            item.setName(data.getName());
            item.setId(data.getId());

            item.setUser(bidder);
            item = itemRepo.save(item);

        }

        return ResponseEntity.ok().body(itemResponseDataMapper(item));

    }

    @Override
    public ResponseEntity getItems(){
        String userName = ThreadContext.getThreadContextData().getUserName();

        Bidder bidder = bidderRepo.findByUserName(userName);

        if(bidder== null){
        throw new UserNotFoundException("User not Found");

        }
        List<Item> items = itemRepo.findAllByUser(bidder);
        List<ItemResponse> itemResponses = items.stream()
                .map(ObjectDataMapper::itemResponseDataMapper)
                .toList();

        return ResponseEntity.ok().body(itemResponses);


    }





}
