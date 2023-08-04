package com.dissertationauc.dissertationauc.Auction.utils;



import com.dissertationauc.dissertationauc.Auction.model.Auction;
import com.dissertationauc.dissertationauc.Auction.model.Bidder;
import com.dissertationauc.dissertationauc.Auction.model.Item;
import com.dissertationauc.dissertationauc.Auction.repositories.AuctionRepo;
import com.dissertationauc.dissertationauc.Auction.repositories.BidderRepo;
import com.dissertationauc.dissertationauc.Auction.repositories.ItemRepo;
import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


import java.time.LocalDateTime;
import java.util.List;



@Configuration
@EnableScheduling
@Slf4j
public class Scheduler {

    final AuctionRepo auctionRepo;

    final BidderRepo bidderRepo;

    final ItemRepo itemRepo;

    @Autowired
    public Scheduler(AuctionRepo auctionRepo, BidderRepo bidderRepo, ItemRepo itemRepo) {
        this.auctionRepo = auctionRepo;
        this.bidderRepo = bidderRepo;
        this.itemRepo = itemRepo;
    }

    @Scheduled(cron = "0 */1 * ? * *")

    public void updateAuction() {
        log.info("Scheduler is running");
        List<Auction> auctions = auctionRepo.findAll();


        for (Auction auction : auctions) {
            if (auction.getOpen()) {
                if (auction.getClosingTime() != null) {
                    LocalDateTime currentTime = LocalDateTime.now();
                    if (currentTime.isAfter(auction.getClosingTime())) {
                        auction.setOpen(false);

                        addFunds(auction);
                        auctionRepo.save(auction);



                    }


                }
            }

        }
        log.info("Scheduler has ended");
    }


    private void addFunds(Auction auction) {

        Bidder owner = auction.getAuctionItem().getUser();

        Bidder bidder = bidderRepo.findByUserName(auction.getBidderName());



        owner.setFunds((int) Math.round(auction.getBidPrice() + owner.getFunds()));
        bidder.setFunds((int) Math.round(bidder.getFunds() - auction.getBidPrice()));

        bidderRepo.save(bidder);
        bidderRepo.save(owner);

        Item item = auction.getAuctionItem();
        item.setUser(bidder);
        item.setPrice(auction.getBidPrice());
        itemRepo.save(item);




    }


}


