package com.dissertationauc.dissertationauc.Auction.controller;


import com.dissertationauc.dissertationauc.Auction.data.BidderData;
import com.dissertationauc.dissertationauc.Auction.data.ItemData;
import com.dissertationauc.dissertationauc.Auction.model.Bidder;
import com.dissertationauc.dissertationauc.Auction.model.Item;
import com.dissertationauc.dissertationauc.Auction.services.EmailService;
import com.dissertationauc.dissertationauc.Auction.services.ItemService;
import com.dissertationauc.dissertationauc.Auction.services.UserService;
import com.dissertationauc.dissertationauc.Auction.utils.JWT;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    //private final EmailService emailService;


    private final JWT jwt;
    @Autowired
    private UserService userService;


    @Autowired
    private ItemService itemService;



    @Autowired
    public UserController(JWT jwt){
        this.jwt = jwt;
    }

    @PostMapping("/login")
    public ResponseEntity loginController(@RequestBody BidderData usr){


            ResponseEntity response = userService.loginService(usr.getUserName(), usr.getPassword());

            if(response.getStatusCode().is2xxSuccessful()) {
                String token = jwt.generateToken(usr.getUserName(), false);
                return ResponseEntity.ok().body(token);
            }

            else {
                return response;
            }


        }

        @PostMapping("/logout")
        public ResponseEntity logoutController(@RequestBody BidderData usr) {

            ResponseEntity response = userService.logoutService(usr.getUserName());

            if (response.getStatusCode().is2xxSuccessful()) {
                String token = jwt.generateToken(usr.getUserName(), true);
                return ResponseEntity.ok().body(token + "Logout successful");
            }

                       return response;
        }


    @PostMapping("/register")
    public ResponseEntity registerController(@RequestBody BidderData newUser) {


            ResponseEntity registerResponse = userService.registerService(newUser);




                return registerResponse;
            }

    @PostMapping("/additem")
    public ResponseEntity addItemController(@RequestBody ItemData data) {

        ResponseEntity addingItemResponse = itemService.addItems(data);


        return addingItemResponse;

    }


    @PostMapping("/sellitem")
    public ResponseEntity sellItemController(@RequestBody ItemData data) {

        ResponseEntity sellingItemResponse = itemService.sellItems(data);


        return sellingItemResponse;

    }




    @GetMapping("/searchby-username")
    public ResponseEntity getUserByUserName(@RequestParam String userName) {

        ResponseEntity response = userService.getUserByUsername(userName);
        return response;
    }



    @GetMapping("/searchby-email")
    public ResponseEntity getUserByEmail(@RequestParam String email) {

        ResponseEntity response = userService.getUserByEmail(email);  //Not sure if this is correct because email service interface was not used.
        return response;
    }
}
