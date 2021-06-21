package com.example.auction;

import com.example.auction.domain.Auction;
import com.example.auction.domain.Buyer;
import com.example.auction.domain.Seller;
import com.example.auction.integration.AuctionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuctionApplication {

   public static void main(String[] args) {
      SpringApplication.run(AuctionApplication.class, args);
      AuctionService auctionService = new AuctionService();
      auctionService.addBuyer("buyer1");
      auctionService.addBuyer("buyer2");
      auctionService.addBuyer("buyer3");
      auctionService.addSeller("seller1");
      auctionService.createAuction(1, Double.parseDouble("10"),Double.parseDouble("50"),Double.parseDouble("1"), Seller.builder()
         .name("seller1")
         .build());
      auctionService.createBid(Buyer.builder()
         .name("buyer1")
         .build(), 1, Double.parseDouble("17"));
      auctionService.createBid(Buyer.builder()
         .name("buyer2")
         .build(), 1, Double.parseDouble("15"));
      auctionService.createBid(Buyer.builder()
         .name("buyer2")
         .build(), 1, Double.parseDouble("19"));
      auctionService.createBid(Buyer.builder()
         .name("buyer3")
         .build(),1, Double.parseDouble("19"));
      System.out.println(auctionService.closeAuction(1));
      System.out.println(auctionService.calculatePnl(1));

   }
}
