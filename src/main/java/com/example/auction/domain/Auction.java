package com.example.auction.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Auction {
   private Integer id;
   private Double lowerLimit;
   private Double higherLimit;
   private Double participationCost;
   private Seller seller;
}
