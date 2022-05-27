package com.example.nomoneytrade.entity;

import javax.persistence.*;

@Entity
@Table(name = "post_tags_exchange")
public class PostTagsExchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column()
    private String tag_exchange;

    public PostTagsExchange() {

    }

    public PostTagsExchange(String tag_exchange) {
        this.tag_exchange = tag_exchange;
    }

    public String getTag_exchange() {
        return tag_exchange;
    }

    public void setTag_exchange(String tag_exchange) {
        this.tag_exchange = tag_exchange;
    }
}
