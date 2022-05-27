package com.example.nomoneytrade.payload.responses;

import com.example.nomoneytrade.entity.Post;

import java.util.List;

public class AllPostResponse {
    List<Post> posts;

    public AllPostResponse(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
