package com.example.nomoneytrade.controller;


import com.example.nomoneytrade.entity.Post;
import com.example.nomoneytrade.entity.PostTag;
import com.example.nomoneytrade.entity.Role;
import com.example.nomoneytrade.entity.User;
import com.example.nomoneytrade.payload.requests.CreatePostRequest;
import com.example.nomoneytrade.repository.PostRepository;
import com.example.nomoneytrade.repository.UserRepository;
import com.example.nomoneytrade.utils.CategoryEnum;
import com.example.nomoneytrade.utils.JwtUtils;
import com.example.nomoneytrade.utils.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
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
        //CategoryEnum category = CategoryEnum.valueOf(createPostRequest.getCategory());
        Long user_id = createPostRequest.getUser_id();
        String description = createPostRequest.getDescription();
        List<String> tags = createPostRequest.getTags();

        HashSet<PostTag> tagsEntities = new HashSet<>();
        for(String tag : tags) {
            PostTag postTag = new PostTag(tag);
            tagsEntities.add(postTag);
        }

        Post post = new Post(title, user_id, description, tagsEntities);

        postRepository.save(post);

        return ResponseEntity.ok("Post has been created.");
    }

    @PostMapping("/get_posts")
    public ResponseEntity<?> getPosts() {

        return ResponseEntity.ok("Post has been created.");
    }
}
