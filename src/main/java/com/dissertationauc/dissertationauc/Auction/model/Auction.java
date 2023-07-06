package com.dissertationauc.dissertationauc.Auction.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter

public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Item auctionItem;

    private String auctionName;
    private Double bidPrice;
    private String bidderName;


    private LocalDateTime openingTime;
    private LocalDateTime closingTime;

    @Column(columnDefinition = "boolean default true")
    private Boolean open;



}
