package com.example.auction.domain;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Seller {
   private final String name;
}
