package com.company.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends Tags {
    @ManyToMany(mappedBy = "categories")
    private List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
