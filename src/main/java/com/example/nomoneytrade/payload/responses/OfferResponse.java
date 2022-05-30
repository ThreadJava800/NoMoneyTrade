package com.example.nomoneytrade.payload.responses;

import com.example.nomoneytrade.entity.Post;
import com.example.nomoneytrade.entity.User;
import com.example.nomoneytrade.utils.ConditionEnum;

public class OfferResponse {
    private Post buyerPost;
    private Post customerPost;
    private String city;
    private String time;
    private User buyer;
    private User customer;
    private ConditionEnum state;

    public OfferResponse(Post buyerPost, Post customerPost, String city, String time, User buyer, User customer, ConditionEnum state) {
        this.buyerPost = buyerPost;
        this.customerPost = customerPost;
        this.city = city;
        this.time = time;
        this.buyer = buyer;
        this.customer = customer;
        this.state = state;
    }

    public Post getBuyerPost() {
        return buyerPost;
    }

    public void setBuyerPost(Post buyerPost) {
        this.buyerPost = buyerPost;
    }

    public Post getCustomerPost() {
        return customerPost;
    }

    public void setCustomerPost(Post customerPost) {
        this.customerPost = customerPost;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public ConditionEnum getState() {
        return state;
    }

    public void setState(ConditionEnum state) {
        this.state = state;
    }
}
