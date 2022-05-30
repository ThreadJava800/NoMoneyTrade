package com.example.nomoneytrade.entity;

import com.example.nomoneytrade.utils.ConditionEnum;

import javax.persistence.*;

@Entity
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerId;

    private Long buyerPostId;
    private Long customerPostId;
    private String city;
    private String time;
    private Long buyerId;
    private Long customerId;
    private ConditionEnum state;

    public Offer() {

    }

    public Offer(Long customerPostId, Long buyerPostId, String city, String time, Long buyerId, Long customerId, ConditionEnum state) {
        this.customerPostId = customerPostId;
        this.buyerPostId = buyerPostId;
        this.city = city;
        this.time = time;
        this.buyerId = buyerId;
        this.customerId = customerId;
        this.state = state;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Long getBuyerPostId() {
        return buyerPostId;
    }

    public void setBuyerPostId(Long buyerPostId) {
        this.buyerPostId = buyerPostId;
    }

    public Long getCustomerPostId() {
        return customerPostId;
    }

    public void setCustomerPostId(Long customerPostId) {
        this.customerPostId = customerPostId;
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

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public ConditionEnum getState() {
        return state;
    }

    public void setState(ConditionEnum state) {
        this.state = state;
    }
}
