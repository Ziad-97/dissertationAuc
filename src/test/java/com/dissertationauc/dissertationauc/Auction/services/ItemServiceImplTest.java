package com.dissertationauc.dissertationauc.Auction.services;

import com.dissertationauc.dissertationauc.Auction.data.*;
import com.dissertationauc.dissertationauc.Auction.model.Auction;
import com.dissertationauc.dissertationauc.Auction.model.Bidder;
import com.dissertationauc.dissertationauc.Auction.model.Item;
import com.dissertationauc.dissertationauc.Auction.repositories.AuctionRepo;
import com.dissertationauc.dissertationauc.Auction.repositories.BidderRepo;
import com.dissertationauc.dissertationauc.Auction.repositories.ItemRepo;
import com.dissertationauc.dissertationauc.Auction.utils.ObjectDataMapper;
import com.dissertationauc.dissertationauc.Auction.utils.ThreadContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dissertationauc.dissertationauc.Auction.utils.ObjectDataMapper.auctionDataMapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ItemServiceImplTest {
    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private UserLifeCycleData userLifeCycleData;
    @Mock
    private ObjectDataMapper objectDataMapper;

    @Mock
    private ItemRepo itemRepo;

    @Mock
    private AuctionRepo auctionRepo;
    @Mock
    private BidderRepo bidderRepo;

    @Mock
    private ItemData itemData;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        ThreadContext.setThreadContextData(new UserLifeCycleData("userName"));

        Bidder user = new Bidder
                (1L,100,"userName","123456789",
                        "user@gmail.com","",new ArrayList<>(), true);
        Bidder user2 = new Bidder
                (2L,200,"userName2","123456789",
                        "user2@gmail.com","",new ArrayList<>(), true);

        Item item = new Item(1L,"item1", 20.5,"",user);
        Auction auction = new Auction(1L, item,"owner1","auctionItem", 20.5,"bidder1",
                "", LocalDateTime.now(),null, true);


        when(bidderRepo.findByUserName(anyString())).thenReturn(user);
        when(itemRepo.findByNameAndUserAndId(
                        "item1", user, 1L)).thenReturn
                (Optional.of(item));
        when(itemRepo.save(any())).thenReturn(item);


        when(auctionRepo.findByAuctionNameAndOpenAndAuctionItem(
                "auctionItem",true,new Item()
        )).thenReturn(Optional.of(new Auction()));
        when(auctionRepo.save(any())).thenReturn(auction);

        when(itemRepo.findAllByUser(user)).thenReturn(List.of(item));


    }




    @Test
    void sellItems() {
        ItemData data = new ItemData("item1",1L,20.5,"");
        ResponseEntity<AuctionResponse> response = itemService.sellItems(data);
        AuctionResponse auctionResponse =  response.getBody();
        assert auctionResponse != null;
        Assertions.assertEquals(auctionResponse.getAuctionName(), "auctionItem");
        Assertions.assertEquals(auctionResponse.getImgLink(), "");
        Assertions.assertEquals(auctionResponse.getBidPrice(), 20.5);
        Assertions.assertEquals(auctionResponse.getOwner(), "owner1");
        Assertions.assertEquals(auctionResponse.getAuctionItem().getName(), "item1" );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());




    }




    @Test
    void addItems() {

        ItemData data = new ItemData("item2",1L,20.5,"");
        ResponseEntity<ItemResponse> response = itemService.addItems(data);
        ItemResponse itemResponse =  response.getBody();
        assert itemResponse != null;
        Assertions.assertEquals(itemResponse.getPrice(), 20.5);
        Assertions.assertEquals(itemResponse.getName(), "item1");
        Assertions.assertEquals(itemResponse.getId(), 1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }



    @Test
    void getItems() {
        ResponseEntity <List<ItemResponse>> response = itemService.getItems();

        List<ItemResponse> listItemResponses = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(listItemResponses.size(), 1);
        ItemResponse itemResponses = listItemResponses.get(0);

        Assertions.assertEquals(itemResponses.getPrice(), 20.5);
        Assertions.assertEquals(itemResponses.getName(), "item1");
        Assertions.assertEquals(itemResponses.getId(), 1L);


    }
}