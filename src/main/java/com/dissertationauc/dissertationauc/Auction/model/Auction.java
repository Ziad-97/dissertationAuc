package com.dissertationauc.dissertationauc.Auction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Item auctionItem;
    private String owner;
    private String auctionName;
    private Double bidPrice;
    private String bidderName;
    @Lob
    @Column(name = "img_link", columnDefinition = "BLOB")
    private String imgLink;


    private LocalDateTime openingTime;
    private LocalDateTime closingTime;

    @Column(columnDefinition = "boolean default true")
    private Boolean open;



}
