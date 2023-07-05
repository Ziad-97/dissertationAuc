package com.dissertationauc.dissertationauc.Auction.controller;

import com.dissertationauc.dissertationauc.Auction.services.AuctionService;
import com.dissertationauc.dissertationauc.Auction.utils.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("auction")
@RestController
public class AuctionController {
    private final AuctionService auctionService;
    private final JWT jwtUtil;

    @Autowired
    public AuctionController(AuctionService auctionService, JWT jwtUtil) {
        this.auctionService = auctionService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("health")
    public ResponseEntity getHealth(){

        return ResponseEntity.ok().body(Map.of("status","running"));

    }

    @GetMapping("currentbid/{id}")
    public ResponseEntity getCurrentBid(@PathVariable Long id){
        return auctionService.getCurrentBid(id);

    }

    @PostMapping("setbid/{id}")
    public ResponseEntity<?> setBid(@PathVariable Long id, @RequestParam Double amount, @RequestHeader("Authorization") String token) {
        String username = extractUsernameFromToken(token);

        if (username != null) {

            return auctionService.setBid(id, username, amount);

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to set bid");
        }
    }




    private String extractUsernameFromToken(String token) {

        try {

            return jwtUtil.extractUsername(token);
        } catch (Exception e) {

            return null;
        }
    }
}


