package com.example.nomoneytrade.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "offers")
public class Offer {
    private Long postId;
    private String city;
    private String time;
    private Long userId;

    public Offer(Long postId, String city, String time, Long userId) {
        this.postId = postId;
        this.city = city;
        this.time = time;
        this.userId = userId;
    }
}
