package com.company.entities;

import com.company.annotations.CrudInterface;
import com.company.orm.IOrm;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "post_type", discriminatorType = DiscriminatorType.STRING)
@CrudInterface(name = "PostService", targetEntity = IOrm.class)
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected int id;

    @Column(nullable = false)
    protected String title;

    protected String content = "";

    @Embedded
    protected FeaturedImage featured_image;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false)
    protected Author author;

    @Column(name = "post_type")
    protected String post_type = Post.class.getSimpleName();

    @ManyToMany
    @JoinTable(name = "post_category", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    protected List<Category> categories;

    @OneToMany(mappedBy = "post")
    protected List<Comment> comments;


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public FeaturedImage getFeatured_image() {
        return featured_image;
    }

    public void setFeatured_image(FeaturedImage featured_image) {
        this.featured_image = featured_image;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", featured_image=" + featured_image +
                ", author=" + author +
                ", post_type='" + post_type + '\'' +
                '}';
    }
}
