package com.example.nomoneytrade.payload.requests;

import java.util.List;

public class CreatePostRequest {
    private String title;
    private Long user_id;
    private String description;
    private List<String> tags;
    private List<String> tagsExchange;

    public void setTagsExchange(List<String> tagsExchange) {
        this.tagsExchange = tagsExchange;
    }

    public List<String> getTagsExchange() {
        return tagsExchange;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
