package com.dissertationauc.dissertationauc.Auction.repositories;

import com.dissertationauc.dissertationauc.Auction.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item, Long> {

   Item findByName(String name);




}
