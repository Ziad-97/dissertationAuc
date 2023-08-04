package com.dissertationauc.dissertationauc.Auction.services;
///////////////////////////////////////////////////////////////
import com.dissertationauc.dissertationauc.Auction.data.AuctionResponse;
import com.dissertationauc.dissertationauc.Auction.model.Auction;
import com.dissertationauc.dissertationauc.Auction.model.Bidder;
import com.dissertationauc.dissertationauc.Auction.model.Item;
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
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class AuctionServiceImplTest {
    @InjectMocks
    private AuctionServiceImpl auctionService;

    @Mock
    private AuctionRepo auctionRepo;

    @Mock
    private BidderRepo bidderRepo;




    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Bidder user = new Bidder
                (1L,100,"userName","123456789",
                        "user@gmail.com","",new ArrayList<>(), true);
        Bidder user2 = new Bidder
                (2L,200,"userName2","123456789",
                        "user2@gmail.com","",new ArrayList<>(), true);

        Item item = new Item(1L,"item1", 20.5,"",user);
        Auction auction = new Auction(1L, item,"owner1","auctionItem", 20.5,"bidder1",
                "", LocalDateTime.now(),null, true);
        Mockito.when(auctionRepo.findById(1L)).thenReturn(Optional.of(auction));
        Mockito.when(bidderRepo.findByUserName("userName")).thenReturn(user);
        Mockito.when(bidderRepo.findByUserName("userName2")).thenReturn(user2);
        Mockito.when(auctionRepo.save(auction)).thenReturn(auction);
//        Mockito.when(itemRepo.findByUserName("userName")).thenReturn(user);
            List<Auction> auctionList = new ArrayList<>();
            auctionList.add(auction);
        Mockito.when(auctionRepo.findAllByOpen(true)).thenReturn(auctionList);
    }


    @Test
    void getCurrentBid() {
        ResponseEntity<Double> response = auctionService.getCurrentBid(1L);
        Assertions.assertEquals(response.getBody(), 20.5);

        Assertions.assertSame(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void setBid() {
        ResponseEntity <AuctionResponse> response = auctionService.setBid(1L,"userName2", 21.5);
        Assertions.assertSame(HttpStatus.OK, response.getStatusCode());

        AuctionResponse auctionResponse =  response.getBody();
        assert auctionResponse != null;
        Assertions.assertNotSame(auctionResponse, null);
        Assertions.assertEquals(auctionResponse.getBidPrice(),21.5) ;



    }

    @Test
    void getAuction() {
        ResponseEntity <Auction> response = auctionService.getAuction(1L);
        Assertions.assertSame(HttpStatus.OK, response.getStatusCode());
        Auction auction = response.getBody();
        assert auction != null;
        Assertions.assertEquals(auction.getAuctionName(),"auctionItem");
        Assertions.assertEquals(auction.getOpen(), true);
        Assertions.assertEquals(auction.getOwner(),"owner1");
        Assertions.assertEquals(auction.getId(), 1L);
        Assertions.assertEquals(auction.getBidPrice(), 20.5);
        Assertions.assertEquals(auction.getBidderName(),"bidder1");

    }

    @Test
    void getAuctions() {
        ResponseEntity <List<AuctionResponse>> response = auctionService.getAuctions();
        Assertions.assertSame(HttpStatus.OK, response.getStatusCode());
        List<AuctionResponse> auctionResponsesList = response.getBody();
        assert !Objects.requireNonNull(auctionResponsesList).isEmpty();

        Assertions.assertEquals(auctionResponsesList.size(),1);

    }
}
