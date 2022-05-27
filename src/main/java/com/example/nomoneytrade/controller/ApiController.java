package com.example.nomoneytrade.controller;

import com.example.nomoneytrade.entity.Post;
import com.example.nomoneytrade.entity.PostTag;
import com.example.nomoneytrade.entity.PostTagsExchange;
import com.example.nomoneytrade.imageStorage.StorageService;
import com.example.nomoneytrade.payload.requests.CreatePostRequest;
import com.example.nomoneytrade.payload.requests.PostByUser;
import com.example.nomoneytrade.payload.responses.AllPostResponse;
import com.example.nomoneytrade.payload.responses.BaseResponse;
import com.example.nomoneytrade.repository.PostRepository;
import com.example.nomoneytrade.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;

import static com.example.nomoneytrade.utils.Constants.IMAGE_HOST_URI;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    JwtUtils jwtUtils;

    private final StorageService storageService;

    public ApiController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/new_post")
    public ResponseEntity<?> createPost(@RequestPart("post_content") CreatePostRequest createPostRequest, @RequestParam(required = false) MultipartFile file) {
        String title = createPostRequest.getTitle();
        Long user_id = createPostRequest.getUser_id();
        String description = createPostRequest.getDescription();
        List<String> tags = createPostRequest.getTags();
        List<String> tagsExchanges = createPostRequest.getTagsExchange();
        String imagePath;

        HashSet<PostTag> tagsEntities = new HashSet<>();
        for (String tag : tags) {
            PostTag postTag = new PostTag(tag);
            tagsEntities.add(postTag);
        }

        HashSet<PostTagsExchange> tagsExchangeEntities = new HashSet<>();
        for (String tag : tagsExchanges) {
            PostTagsExchange postTagsExchange = new PostTagsExchange(tag);
            tagsExchangeEntities.add(postTagsExchange);
        }

        if (file != null) {
            imagePath = IMAGE_HOST_URI + "post_" + user_id.toString() + '_' + title + ".png";
            storageService.store(file, "post_" + user_id.toString() + '_' + title + ".png");
        }
        else {
            imagePath = "";
        }

        Post post = new Post(title, user_id, description, tagsEntities, tagsExchangeEntities, imagePath);

        postRepository.save(post);

        String jwtCookie = jwtUtils.getCleanJwtCookie().toString();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie).body(new BaseResponse("Post created successfully."));
    }

    @PostMapping("/get_posts")
    public ResponseEntity<?> getPosts() {
        List<Post> posts = postRepository.findAll();

        String jwtCookie = jwtUtils.getCleanJwtCookie().toString();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie).body(new AllPostResponse(posts));
    }

    @PostMapping("/get_my_posts")
    public ResponseEntity<?> getMyPosts(@RequestBody PostByUser postByUser) {
        List<Post> posts;
        try {
            posts = postRepository.findByUserId(postByUser.getUserId()).orElseThrow(() -> new RuntimeException("User doesnt has posts"));
        } catch (RuntimeException e) {
            return new ResponseEntity<String>("Not found", HttpStatus.NOT_FOUND);
        }

        String jwtCookie = jwtUtils.getCleanJwtCookie().toString();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie).body(new AllPostResponse(posts));
    }
}
