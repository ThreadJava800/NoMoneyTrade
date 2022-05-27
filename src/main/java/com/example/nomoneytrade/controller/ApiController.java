package com.example.nomoneytrade.controller;

import com.example.nomoneytrade.entity.Post;
import com.example.nomoneytrade.entity.PostTag;
import com.example.nomoneytrade.payload.requests.CreatePostRequest;
import com.example.nomoneytrade.payload.responses.AllPostResponse;
import com.example.nomoneytrade.payload.responses.BaseResponse;
import com.example.nomoneytrade.repository.PostRepository;
import com.example.nomoneytrade.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/new_post")
    public ResponseEntity<?> createPost(@RequestBody @Valid CreatePostRequest createPostRequest) {
        String title = createPostRequest.getTitle();
        Long user_id = createPostRequest.getUser_id();
        String description = createPostRequest.getDescription();
        List<String> tags = createPostRequest.getTags();
        List<String> tagsExchanges = createPostRequest.getTagsExchange();

        HashSet<PostTag> tagsEntities = new HashSet<>();
        for (String tag : tags) {
            PostTag postTag = new PostTag(tag);
            tagsEntities.add(postTag);
        }

        HashSet<PostTag> tagsExchangeEntities = new HashSet<>();
        for (String tag : tagsExchanges) {
            PostTag postTag = new PostTag(tag);
            tagsEntities.add(postTag);
        }

        Post post = new Post(title, user_id, description, tagsEntities, tagsExchangeEntities);

        postRepository.save(post);

        String jwtCookie = jwtUtils.getCleanJwtCookie().toString();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie).body(new BaseResponse("Offer created successfully."));
    }

    @PostMapping("/get_posts")
    public ResponseEntity<?> getPosts() {
        List<Post> posts = postRepository.findAll();

        String jwtCookie = jwtUtils.getCleanJwtCookie().toString();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie).body(new AllPostResponse(posts));
    }
}
