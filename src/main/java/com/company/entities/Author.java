package com.company.entities;

import com.company.annotations.CrudInterface;
import com.company.orm.IOrm;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "authors")
@DiscriminatorValue("Author")
@CrudInterface(name = "PostService", targetEntity = IOrm.class)
public class Author extends User {

    private String firstName;
    private String lastName;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Embedded
    @Column(nullable = false)
    private Email email;

    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "Author{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email=" + email +
                ", id=" + id +
                '}';
    }
}
