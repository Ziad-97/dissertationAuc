package com.dissertationauc.dissertationauc.Auction.utils;



import com.dissertationauc.dissertationauc.Auction.model.Auction;
import com.dissertationauc.dissertationauc.Auction.repositories.AuctionRepo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;



@Configuration
@EnableScheduling
@Slf4j
public class Scheduler {

         final AuctionRepo auctionRepo;

         @Autowired
        public Scheduler(AuctionRepo auctionRepo){
            this.auctionRepo = auctionRepo;
        }

        @Scheduled(cron = "0 */1 * ? * *" )

        public void updateAuction(){
             log.info("Scheduler is running");
            List<Auction> auctions=  auctionRepo.findAll();

                for(Auction auction : auctions)
                {
               if(auction.getOpen()){
                   if(auction.getClosingTime()!=null){
                       LocalDateTime currentTime = LocalDateTime.now();
                       if(currentTime.isAfter(auction.getClosingTime())){
                           auction.setOpen(false);
                           auctionRepo.save(auction);

                       }


                   }
               }

            }log.info("Scheduler has ended");
        }

}
