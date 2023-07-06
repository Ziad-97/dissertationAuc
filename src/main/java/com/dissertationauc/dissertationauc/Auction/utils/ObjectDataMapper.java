package com.dissertationauc.dissertationauc.Auction.utils;

import com.dissertationauc.dissertationauc.Auction.data.AuctionResponse;
import com.dissertationauc.dissertationauc.Auction.data.BidderResponse;
import com.dissertationauc.dissertationauc.Auction.data.ItemData;
import com.dissertationauc.dissertationauc.Auction.data.ItemResponse;
import com.dissertationauc.dissertationauc.Auction.model.Auction;
import com.dissertationauc.dissertationauc.Auction.model.Bidder;
import com.dissertationauc.dissertationauc.Auction.model.Item;

public class ObjectDataMapper {

    public static BidderResponse bidderDataMapper(Bidder user) {


        BidderResponse data = new BidderResponse();
        data.setUserName(user.getUserName());
        data.setEmail(user.getEmail());
        data.setFunds(user.getFunds());
        data.setId(user.getId());

        return data;

    }


    public static ItemResponse itemResponseDataMapper(Item item){
        ItemResponse itemData = new ItemResponse();

        itemData.setId(item.getId());
        itemData.setPrice(item.getPrice());
        itemData.setName(item.getName());


        return itemData;
    }

    public static AuctionResponse auctionDataMapper(Auction auction){

        AuctionResponse  auctionData = new AuctionResponse();


        auctionData.setAuctionItem(itemDataMapper(auction.getAuctionItem()));
        auctionData.setAuctionName(auction.getAuctionName());

        return auctionData;

    }


    public static ItemData itemDataMapper(Item item){
        ItemData itemData = new ItemData();

        itemData.setId(item.getId());
        itemData.setPrice(item.getPrice());
        itemData.setName(item.getName());


        return itemData;
    }
}
