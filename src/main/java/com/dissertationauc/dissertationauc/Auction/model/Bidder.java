package com.dissertationauc.dissertationauc.Auction.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "user")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bidder {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer funds;

    private String userName;

    private String password;

    private String email;

    private String code;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Item> items;


    private boolean verify;

}
