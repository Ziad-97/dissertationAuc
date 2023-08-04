package com.dissertationauc.dissertationauc.Auction.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemData {
    private String name;
    private Long id;
    private Double price;
    private String imgLink;




}
