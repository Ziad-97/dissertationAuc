package com.dissertationauc.dissertationauc.Auction.services;

import com.dissertationauc.dissertationauc.Auction.data.AuctionResponse;
import com.dissertationauc.dissertationauc.Auction.exception.AuctionClosedException;
import com.dissertationauc.dissertationauc.Auction.exception.InsufficentFundsExcepion;
import com.dissertationauc.dissertationauc.Auction.exception.InvalidBidderException;
import com.dissertationauc.dissertationauc.Auction.exception.InvalidItemException;
import com.dissertationauc.dissertationauc.Auction.model.Auction;
import com.dissertationauc.dissertationauc.Auction.model.Bidder;
import com.dissertationauc.dissertationauc.Auction.model.Item;
import com.dissertationauc.dissertationauc.Auction.repositories.AuctionRepo;
import com.dissertationauc.dissertationauc.Auction.repositories.BidderRepo;
import com.dissertationauc.dissertationauc.Auction.repositories.ItemRepo;
import com.dissertationauc.dissertationauc.Auction.utils.ObjectDataMapper;
import com.dissertationauc.dissertationauc.Auction.utils.ThreadContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepo auctionRepo;

    private final BidderRepo bidderRepo;

    private final ItemRepo itemRepo;


    @Autowired
    public AuctionServiceImpl(AuctionRepo auctionRepo, BidderRepo bidderRepo, ItemRepo itemRepo){
        this.auctionRepo = auctionRepo;
        this.bidderRepo = bidderRepo;
        this.itemRepo = itemRepo;
    }




    @Override
    public ResponseEntity<Double> getCurrentBid(Long id) {
       Auction auction = getAuctionbyId(id);
       if(auction!=null){
           if(auction.getBidPrice()!=null){
               return ResponseEntity.ok().body(auction.getBidPrice());

           }
           else {
               return ResponseEntity.ok().body(auction.getAuctionItem().getPrice());
           }

       }
        return null;


    }






    /*
    *
    * */




    @Override
    public synchronized ResponseEntity<AuctionResponse> setBid(Long id, String userName, Double amount) {
        Auction auction = getAuctionbyId(id); // Got the id r

        if (auction != null) {
            Bidder bidder =bidderRepo.findByUserName(userName);
            if(bidder == auction.getAuctionItem().getUser()){
                throw new InvalidBidderException("Owner cannot set a bid on an item that they own.");
            }
            if(bidder.getFunds() <= amount) {

                throw new InsufficentFundsExcepion("You currently don't have enough funds. ");
            }

            if (auction.getOpen()) {



                Double price = auction.getAuctionItem().getPrice();  // Got the price
                Double bidPrice = auction.getBidPrice();  // Got the bid price

                if(bidPrice !=null){
                    price=bidPrice;
                }

                if (amount < price) {
                    log.info("Current price is more than the present bid");
                    throw new InvalidItemException("Item minimum price is more than the present bid");
                }
                if(amount > price){

                    log.info("Your bid has been accepted");
                    auction.setBidderName(userName);
                    auction.setBidPrice(amount);
                    LocalDateTime close = auction.getClosingTime() == null ? LocalDateTime.now().plusMinutes(2) : auction.getClosingTime();
                    auction.setClosingTime(close.plusMinutes(2));
                }
                if (bidPrice == null) {
                    auction.setOpeningTime(LocalDateTime.now());
                    auction.setClosingTime(LocalDateTime.now().plusMinutes(2));
                    auction.setBidderName(userName);
                    auction.setBidPrice(amount);
                }


                log.info("Any other Bidders");


                Auction savedAuction = auctionRepo.save(auction);

                return ResponseEntity.ok().body(ObjectDataMapper.auctionDataMapper(savedAuction));


            } else {
                throw new AuctionClosedException("The Auction is already closed.");
            }

        }
      return null;
    }





    private Auction getAuctionbyId(Long id){
        Optional<Auction> optionalAuction= auctionRepo.findById(id);
        return optionalAuction.orElse(null);

    }

    @Override
    public ResponseEntity<Auction> getAuction(Long id) {
        Optional<Auction> optionalAuction = auctionRepo.findById(id);
        return optionalAuction.map(auction -> ResponseEntity.ok().body(auction)).orElse(null);
    }

    @Override
    public ResponseEntity<List<AuctionResponse>> getAuctions() {
        List<Auction> auctions = auctionRepo.findAllByOpen(true);
        List<AuctionResponse> auctionDataList = auctions.stream()
                .map(ObjectDataMapper :: auctionDataMapper).collect(Collectors.toList());
        return ResponseEntity.ok().body(auctionDataList);
    }

    //Was the object data mapper created for the sole reason of saving multiple auctions and getting them and so are all the methods used in the objectdatamapper
    //to get all items as well and user for user details?
}

