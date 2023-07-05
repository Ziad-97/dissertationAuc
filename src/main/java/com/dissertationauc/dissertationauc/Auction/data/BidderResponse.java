package com.dissertationauc.dissertationauc.Auction.data;

import com.dissertationauc.dissertationauc.Auction.model.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidderResponse {

    private Long id;

    private String userName;

    private String email;

    private int funds;














}

