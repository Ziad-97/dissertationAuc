package com.dissertationauc.dissertationauc.Auction.services;

import com.dissertationauc.dissertationauc.Auction.data.*;
import com.dissertationauc.dissertationauc.Auction.exception.*;
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
    public ResponseEntity<AuctionResponse> sellItems(ItemData data) {

        String userName = ThreadContext.getThreadContextData().getUserName();

        Bidder bidder = bidderRepo.findByUserName(userName);
        Item item = itemRepo.findByNameAndUserAndId(data.getName(), bidder, data.getId()).orElse(null);
        if(item!= null && item.getUser()==bidder) {

            Optional<Auction> optionalAuction = auctionRepo.findByAuctionNameAndOpenAndAuctionItem(item.getName(), true, item);


            if(!optionalAuction.isPresent()) {


                Auction auction = new Auction();
                auction.setImgLink(data.getImgLink());
                auction.setOwner(bidder.getUserName());
                auction.setAuctionItem(item);
                auction.setAuctionName(item.getName());
                auction.setOpen(true);
                auction.setOpeningTime(LocalDateTime.now());

                auction = auctionRepo.save(auction);


                return ResponseEntity.ok().body(auctionDataMapper(auction));
            }
            else{
                throw new AuctionAlreadyExistsException("Auction cannot be created as it already exists.");
            }

            }
        else {

        }
            return ResponseEntity.notFound().build();
        }





    @Override
    public ResponseEntity<ItemResponse> addItems(ItemData data) {
        Item item = new Item();
        String userName = ThreadContext.getThreadContextData().getUserName();

      Bidder bidder = bidderRepo.findByUserName(userName);

        if(bidder!=null) {

            Optional<Item> optionalItem = itemRepo.findByNameAndUserAndId(data.getName(), bidder, data.getId());
            if(optionalItem.isEmpty()) {


                item.setPrice(data.getPrice());
                item.setName(data.getName());
                item.setId(data.getId());

                item.setUser(bidder);
                item = itemRepo.save(item);
            }

            else {
                throw new ItemAlreadyExistsException("This item already exists in the inventory.");
            }
        }

        return ResponseEntity.ok().body(itemResponseDataMapper(item));

    }

    @Override
    public ResponseEntity <List<ItemResponse>> getItems(){
        String userName = ThreadContext.getThreadContextData().getUserName();

        Bidder bidder = bidderRepo.findByUserName(userName);


        if(bidder== null){
        throw new UserNotFoundException("User not Found");

        }
        List<Item> items = itemRepo.findAllByUser(bidder);

        List<ItemResponse> itemResponses = items.stream()
                .filter(this::hasAuction)
                .map(ObjectDataMapper::itemResponseDataMapper)
                .toList();

        return ResponseEntity.ok().body(itemResponses);


    }



    private boolean hasAuction(Item item){

        Optional<Auction> optionalAuction = auctionRepo.findByAuctionItemAndOpen(item, true);



        return optionalAuction.isEmpty();


    }





}
