package com.example.nomoneytrade.payload.requests;

import com.example.nomoneytrade.utils.ConditionEnum;

public class SetOfferStatus {
    Long offerId;
    ConditionEnum state;

    public Long getOfferId() {
        return offerId;
    }

    public ConditionEnum getState() {
        return state;
    }
}
