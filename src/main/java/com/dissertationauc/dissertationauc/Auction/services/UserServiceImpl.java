package com.dissertationauc.dissertationauc.Auction.services;

import com.dissertationauc.dissertationauc.Auction.data.*;
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

import static com.dissertationauc.dissertationauc.Auction.utils.ObjectDataMapper.bidderDataMapper;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private  BCryptPasswordEncoder passwordEncoder;
    @Autowired
    BidderRepo bidderRepo;
    @Autowired
    ItemRepo itemRepo;

    @Override
    public ResponseEntity loginService(String userName, String password) {

        Bidder bid1 = bidderRepo.findByUserName(userName);


        if (bid1 != null && passwordEncoder.matches(password, bid1.getPassword())) {

            return ResponseEntity.ok().body(bidderDataMapper(bid1));
        } else {
            log.info("USER NOT FOUND");
            return ResponseEntity.notFound().build();
        }
    }



    @Override
    public ResponseEntity logoutService(String userName){

        Bidder bid1 = bidderRepo.findByUserName(userName);

        if(bid1!= null){
            return ResponseEntity.ok().body(bidderDataMapper(bid1));

        } else {
            log.info("Log out successful ");
            return ResponseEntity.ok().body("User logged out");
        }

    }

    @Override
    public ResponseEntity registerService(BidderData user) {

       List<Bidder> bid1 = bidderRepo.findByUserNameOrEmail(user.getUserName(), user.getEmail());



        if(!bid1.isEmpty()){

            throw new UserAlreadyExistsException("User already exists");

        }

        else{
            Bidder newBidder = new Bidder();

            newBidder.setUserName(user.getUserName());
            newBidder.setFunds(2000);
            newBidder.setEmail(user.getEmail());
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            newBidder.setPassword(encodedPassword);        //Security issue need to encode rather than provide a simple method.

           log.info("USER HAS BEEN CREATED");

            bidderRepo.save(newBidder);

            return ResponseEntity.ok().body(bidderDataMapper(newBidder));
        }



    }

    @Override
    public ResponseEntity addFunds(AddFundsData data) {

        Bidder bidder =bidderRepo.findByUserName(data.getUserName());

        bidder.setFunds(data.getFunds()+data.getFunds());

        bidderRepo.save(bidder);

        return ResponseEntity.ok().body(bidderDataMapper(bidder));

    }






    @Override
    public ResponseEntity getUserByUsername(String userName) {
        Bidder user = bidderRepo.findByUserName(userName);
        if (user != null) {

            return ResponseEntity.ok().body(bidderDataMapper(user));
        }

        else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public ResponseEntity getUserByEmail(String email) {
        Bidder user = bidderRepo.findByEmail(email);
        if (user != null ) {


            return ResponseEntity.ok().body(bidderDataMapper(user));
        }

        else {
           throw new UserNotFoundException("Email not found");   //Not found in the current build
        }
    }






    private void ValidateForCreate(BidderData usr){
        if(usr.getUserName()== null || usr.getUserName().equals("")){
            throw new InvalidUserNameException("Username was not entered");
        }

        if(usr.getEmail() == null || usr.getEmail().equals("")){
            throw new InvalidEmailException("Email was not entered");
        }
    }
}
