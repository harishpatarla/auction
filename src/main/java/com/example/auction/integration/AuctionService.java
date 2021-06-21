package com.example.auction.integration;

import com.example.auction.domain.Auction;
import com.example.auction.domain.Buyer;
import com.example.auction.domain.Seller;
import com.example.auction.exceptions.BidValueNotInRangeException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparingInt;

public class AuctionService {
   Map<Integer, List<Buyer>> auctionListMap = new HashMap<>();

//● Withdraw bid(buyer, auction)
//● Close auction and return winning bid
//   Get profit/loss(seller)

   public Buyer addBuyer(String name) {
      return Buyer.builder()
         .name(name)
         .build();
   }

   public Seller addSeller(String name) {
      return Seller.builder()
         .name(name)
         .build();
   }

   public Auction createAuction(Integer id, Double lowerLimit, Double higherLimit,
                                Double participationCost, Seller seller) {
      List<Buyer> buyers = new ArrayList<>();
      Auction auction = Auction.builder()
         .id(id)
         .lowerLimit(lowerLimit)
         .higherLimit(higherLimit)
         .participationCost(participationCost)
         .seller(seller)
         .build();
      auctionListMap.put(id, buyers);
      return auction;

   }

   public Map<Integer, List<Buyer>> createBid(Buyer buyer, Integer auctionId, Double newBidValue) {
      // check if the bid id valid - should be between lower and higher limits
      // if existing buyer then update else insert

      //throw exception if newBidValue not in range
//      if (buyers.stream().anyMatch(buyer1 -> buyer1.getName().equals(buyer.getName()))) {
//
//      } else {
//
//      }

      List<Buyer> buyersList = auctionListMap.get(auctionId);
      buyer.updateBidValue(newBidValue);
      buyersList.add(buyer);
      auctionListMap.put(auctionId, buyersList);
      return auctionListMap;
   }

   public Map<Integer, List<Buyer>> updateBid(Buyer buyer, Auction auction, Double newBidValue) {
      // check if the bid id valid - should be between lower and higher limits
      // if existing buyer then update else insert

      //throw exception if newBidValue not in range
      if (newBidValue.isNaN() || newBidValue < auction.getLowerLimit() || newBidValue > auction.getLowerLimit()) {
         throw new BidValueNotInRangeException("Bid value not in range ");
      }

      List<Buyer> buyersList = auctionListMap.get(auction.getId());
      buyersList.add(buyer);
      auctionListMap.put(auction.getId(), buyersList);
      return auctionListMap;
      //
//      if (buyers.stream().anyMatch(buyer1 -> buyer1.getName().equals(buyer.getName()))) {
//
//      }
   }

   public Map<Integer, List<Buyer>> withdrawBid(Buyer buyer, Auction auction) {
      List<Buyer> buyersList = auctionListMap.get(auction.getId());
      buyersList.removeIf(buyer1 -> buyer1.equals(buyer));
      return auctionListMap;
   }

   public Buyer closeAuction(Integer auctionId) {
      //remove duplicate values
      //get highest unique value
      List<Buyer> buyers = auctionListMap.get(auctionId);

      List<Double> newBuyerList = new ArrayList<>();
      buyers.forEach(buyer -> {
         if (!newBuyerList.contains(buyer.getBidValue())) {
            newBuyerList.add(buyer.getBidValue());
         } else {
            newBuyerList.remove(buyer.getBidValue());
         }
      });
      Double max = newBuyerList.stream().max(Comparator.comparingDouble(Double::valueOf)).get();
      return buyers.stream().filter(buyer -> max == buyer.getBidValue()).findFirst().get();
   }

   public Double calculatePnl(Integer auctionId) {
//      winning auction price + participation cost-share(no_of_bidders * 0.2 * participation
//         cost) - an average of the lowest and highest bid limits

      Double winningAuctionPrice = closeAuction(auctionId).getBidValue();
      return winningAuctionPrice * 0.20;
   }
}
