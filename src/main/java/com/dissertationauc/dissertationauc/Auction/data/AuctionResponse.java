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
    private Integer bidPrice;
    private String bidderName;
}
