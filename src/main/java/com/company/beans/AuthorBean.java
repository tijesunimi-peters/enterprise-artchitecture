package com.company.beans;

import com.company.entities.Author;
import com.company.entities.Email;
import com.company.orm.ORM;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "authorBean")
@RequestScoped
public class AuthorBean {
    @Inject
    EntityManagerBean entityManagerBean;

    @Inject
    private Author author;

    private String email;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String create() throws Exception {
        author.setEmail(new Email(email));
        ORM<Author> authorORM = new ORM(author);
        authorORM.save();
        return "success";
    }
}
