package com.example.nomoneytrade.payload.responses;

import com.example.nomoneytrade.entity.Offer;

import java.util.List;

public class AllOfferResponse {
    List<Offer> offers;

    public AllOfferResponse(List<Offer> offers) {
        this.offers = offers;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
