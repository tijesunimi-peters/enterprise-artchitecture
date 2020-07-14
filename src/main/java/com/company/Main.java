package com.company;

import com.company.data.Generator;
import com.company.enums.CategoryType;
import com.company.orm.EntityManagerFacade;
import com.company.queries.Search;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        EntityManagerFacade.getInstance();
//        Generator.generate();
//        ORM<Post> post = new ORM<>(Post.class);
//        ORM<Author> author = new ORM<>(Author.class);
//        ORM<Author> author2 = new ORM<>(Author.class);
//        author.set("email", new Email("test@testing.com"));
//        author.set("username", "john");
//        author.set("password", "password");
//
//        post.set("title", "This is the thing that we are talking abuot");
//        author.save();
//        author2.set("email", new Email("testing2@testing.com"));
//        author2.set("username", "test2");
//        author2.set("password", "test2");
//        author2.save();
//
//        post.set("author", author);
//        post.save();
//        System.out.println(post.getService().findById(1));
//        System.out.println(author.getService().findById(1));
//        System.out.println(author.getService().findBy("username", "john"));
//        System.out.println(Search.categoryPosts(CategoryType.Science_Fiction));
        System.out.println(Search.authorsPostsCount(CategoryType.Science_Fiction).stream().map(x -> Arrays.toString(x)).collect(Collectors.toList()));
    }
}
