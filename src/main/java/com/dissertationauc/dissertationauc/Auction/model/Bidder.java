package com.dissertationauc.dissertationauc.Auction.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "user")
@Entity
@Getter
@Setter
public class Bidder {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int funds;

    private String userName;

    private String password;

    private String email;

    private String code;

    @OneToMany
    private List<Item> items;



    private boolean verify;

}
