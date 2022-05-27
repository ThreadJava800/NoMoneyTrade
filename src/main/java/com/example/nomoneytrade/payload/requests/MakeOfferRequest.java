package com.example.nomoneytrade.payload.requests;

import com.example.nomoneytrade.utils.ConditionEnum;

public class MakeOfferRequest {
    private Long postId;
    private String city;
    private String time;
    private Long buyerId;
    private Long customerId;
    private ConditionEnum state;

    public String getCity() {
        return city;
    }

    public Long getPostId() {
        return postId;
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
