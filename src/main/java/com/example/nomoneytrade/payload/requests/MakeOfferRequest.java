package com.example.nomoneytrade.payload.requests;

public class MakeOfferRequest {
    private Long postId;
    private String city;
    private String time;
    private Long userId;

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
}
