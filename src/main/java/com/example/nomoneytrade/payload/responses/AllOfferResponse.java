package com.example.nomoneytrade.payload.responses;

import java.util.List;

public class AllOfferResponse {
    List<OfferResponse> offers;

    public AllOfferResponse(List<OfferResponse> offers) {
        this.offers = offers;
    }

    public List<OfferResponse> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferResponse> offers) {
        this.offers = offers;
    }
}
