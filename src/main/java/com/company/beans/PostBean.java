package com.company.beans;

import com.company.entities.Post;
import com.company.orm.EntityManagerFacade;
import com.company.orm.ORM;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named(value="postBean")
@RequestScoped
public class PostBean {
    private String title;
    private String content;

    public PostBean() {
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

    public String create() throws Exception {
//        entityManagerBean.getEntityExecutor();
        Post post = new Post();
        post.setTitle(title);
        post.setTitle(content);
        ORM<Post> postORM = new ORM<Post>(post);
        postORM.save();

        return "success";
    }
}
