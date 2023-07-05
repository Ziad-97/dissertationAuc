package com.dissertationauc.dissertationauc.Auction.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;



    /*
    * instead of using a map that adds the items hard coded, use the data from the user to add items inside the users data
    * */





}