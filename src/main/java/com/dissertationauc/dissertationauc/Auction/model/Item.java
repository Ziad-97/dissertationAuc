package com.dissertationauc.dissertationauc.Auction.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Bidder user;






    /*
    * instead of using a map that adds the items hard coded, use the data from the user to add items inside the users data
    * */





}