package com.dissertationauc.dissertationauc.Auction.data;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionResponse {

    private ItemData auctionItem;
    private String auctionName;
    private Double bidPrice;
    private String bidderName;
    private Long id;
    private Long bid;
    private Long timeLeft;
    private String owner;
    private String imgLink;


}
