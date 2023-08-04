package com.dissertationauc.dissertationauc.Auction.services;

import com.dissertationauc.dissertationauc.Auction.data.BidderResponse;
import com.dissertationauc.dissertationauc.Auction.model.Bidder;
import com.dissertationauc.dissertationauc.Auction.repositories.AuctionRepo;
import com.dissertationauc.dissertationauc.Auction.repositories.BidderRepo;
import com.dissertationauc.dissertationauc.Auction.repositories.ItemRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.when;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private AuctionRepo auctionRepo;
    @Mock
    private BidderRepo bidderRepo;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        Bidder user = new Bidder
                (1L,100,"userName","$2a$10$fWakXh6Gixu7.8cjUV3uoum0ZhrTFJMWgkRwBok1i/HBilX40Hm4W",
                        "user@gmail.com","",new ArrayList<>(), true);
        Bidder user2 = new Bidder
                (2L,200,"userName2","$2a$10$8sc3w2YOaWfp9ivvd8SNIuovS9N1qS9B80M4RmsPlc7S/57yshLha",
                        "user2@gmail.com","",new ArrayList<>(), true);
        when(bidderRepo.findByUserName("userName")).thenReturn(user);
        when(passwordEncoder.matches(
                "123456","$2a$10$fWakXh6Gixu7.8cjUV3uoum0ZhrTFJMWgkRwBok1i/HBilX40Hm4W"))
                .thenReturn(true);



    }

    @Test
    void loginService() {
        ResponseEntity < BidderResponse> response = userService.loginService
                ("userName","123456");
        BidderResponse bidderResponse = response.getBody();
        Assertions.assertSame(bidderResponse.getId(), 1L);
        Assertions.assertSame(bidderResponse.getFunds(), 100);
        Assertions.assertSame(bidderResponse.getUserName(), "userName");
        Assertions.assertSame(bidderResponse.getEmail(), "user@gmail.com");


        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

    @Test
    void logoutService() {

    }

    @Test
    void registerService() {
    }

    @Test
    void addFunds() {
    }

    @Test
    void getAccountDetails() {
    }

    @Test
    void getUserByUsername() {
    }

    @Test
    void getUserByEmail() {
    }
}