package com.example.auction.exceptions;

public class BidValueNotInRangeException extends RuntimeException {
   public BidValueNotInRangeException(String message) {
      super(message);
   }

}
