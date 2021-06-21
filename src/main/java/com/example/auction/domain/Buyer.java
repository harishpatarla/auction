package com.example.auction.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Buyer {
   private Double bidValue;
   private final String name;

   public void updateBidValue(Double bidValue) {
      this.bidValue = bidValue;
   }
}
