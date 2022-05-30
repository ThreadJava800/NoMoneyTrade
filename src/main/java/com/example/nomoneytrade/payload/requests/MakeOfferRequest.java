package com.example.nomoneytrade.payload.requests;

import com.example.nomoneytrade.utils.ConditionEnum;

public class MakeOfferRequest {
    private Long customerPostId;
    private Long buyerPostId;
    private String city;
    private String time;
    private Long buyerId;
    private Long customerId;
    private ConditionEnum state;

    public Long getCustomerPostId() {
        return customerPostId;
    }

    public Long getBuyerPostId() {
        return buyerPostId;
    }

    public String getCity() {
        return city;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getTime() {
        return time;
    }

    public ConditionEnum getState() {
        return state;
    }
}
