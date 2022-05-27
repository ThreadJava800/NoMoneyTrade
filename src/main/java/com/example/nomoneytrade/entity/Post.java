package com.example.nomoneytrade.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private String imagePath;

    private Long userId;

    @NotBlank
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "post_post_tags",
            joinColumns = @JoinColumn(name = "post"),
            inverseJoinColumns = @JoinColumn(name = "postTag"))
    private Set<PostTag> tags = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "post_post_tags_exchanges",
            joinColumns = @JoinColumn(name = "post"),
            inverseJoinColumns = @JoinColumn(name = "postTagExchange"))
    private Set<PostTagsExchange> tags_exchange = new HashSet<>();

    public Post() {

    }

    public Post(String title, Long userId, String description, Set<PostTag> tags, Set<PostTagsExchange> tags_exchange, String imagePath) {
        this.title = title;
        this.userId = userId;
        this.description = description;
        this.tags = tags;
        this.tags_exchange = tags_exchange;
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUser_id() {
        return userId;
    }

    public void setUser_id(Long user_id) {
        this.userId = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<PostTag> getTags() {
        return tags;
    }

    public void setTags(Set<PostTag> tags) {
        this.tags = tags;
    }

    public Set<PostTagsExchange> getTags_exchange() {
        return tags_exchange;
    }

    public void setTags_exchange(Set<PostTagsExchange> tags_exchange) {
        this.tags_exchange = tags_exchange;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
