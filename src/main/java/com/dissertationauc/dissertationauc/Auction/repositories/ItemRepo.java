package com.dissertationauc.dissertationauc.Auction.repositories;

import com.dissertationauc.dissertationauc.Auction.model.Bidder;
import com.dissertationauc.dissertationauc.Auction.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepo extends JpaRepository<Item, Long> {

   Optional<Item> findByName(String name);

   Optional<Item> findByNameAndUserAndId(String name, Bidder user, Long id);
   Optional<Item> findByNameAndUser(String name, Bidder user);



   List<Item> findAllByUser(Bidder bidder);




}
