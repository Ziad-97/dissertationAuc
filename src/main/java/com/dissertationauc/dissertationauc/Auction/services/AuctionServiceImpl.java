package com.dissertationauc.dissertationauc.Auction.services;

import com.dissertationauc.dissertationauc.Auction.model.Auction;
import com.dissertationauc.dissertationauc.Auction.model.Bidder;
import com.dissertationauc.dissertationauc.Auction.repositories.AuctionRepo;
import com.dissertationauc.dissertationauc.Auction.repositories.BidderRepo;
import com.dissertationauc.dissertationauc.Auction.utils.ObjectDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepo auctionRepo;
    @Autowired
    public AuctionServiceImpl( AuctionRepo auctionRepo){
        this.auctionRepo = auctionRepo;
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
    public synchronized ResponseEntity setBid(Long id, String userName, Double amount) {

        Auction auction = getAuctionbyId(id); // Got the id r

        if (auction != null) {


            if (auction.getOpen()) {



                Double price = auction.getAuctionItem().getPrice();  // Got the price
                Double bidPrice = auction.getBidPrice();  // Got the bid price

                if(bidPrice !=null){
                    price=bidPrice;
                }

                if (amount < price) {
                    log.info("Current price is more than the present bid");
                    return ResponseEntity.ok().body("Item minimum price is more than the present bid");
                }
                if(amount > price){

                    log.info("Your bid has been accepted");
                    auction.setBidderName(userName);
                    auction.setBidPrice(amount);
                    LocalDateTime close = auction.getClosingTime() == null ? LocalDateTime.now().plusMinutes(15) : auction.getClosingTime();
                    auction.setClosingTime(close.plusMinutes(5));
                }
                if (bidPrice == null) {
                    auction.setOpeningTime(LocalDateTime.now());
                    auction.setClosingTime(LocalDateTime.now().plusMinutes(20));
                    auction.setBidderName(userName);
                    auction.setBidPrice(amount);
                }


                log.info("Any other Bidders");





                Auction savedAuction = auctionRepo.save(auction);



                return ResponseEntity.ok().body(ObjectDataMapper.auctionDataMapper(savedAuction));

            } else {
                return ResponseEntity.ok().body("Auction is already closed");
            }

        }
      return null;
    }



    /*
    Add selling functionality inside this class or the user service class(still unsure.))
     */






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
    public ResponseEntity<List<Auction>> getAuctions() {
        List<Auction> auctions = auctionRepo.findAllByOpen(true);

        return ResponseEntity.ok().body(auctions);
    }

}

