package com.example.nomoneytrade.payload.requests;

import com.example.nomoneytrade.utils.ConditionEnum;

public class MakeOfferRequest {
    private Long postId;
    private String city;
    private String time;
    private Long userId;
    private ConditionEnum state;

    public String getCity() {
        return city;
    }

    public Long getPostId() {
        return postId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTime() {
        return time;
    }

    public ConditionEnum getState() {
        return state;
    }
}
