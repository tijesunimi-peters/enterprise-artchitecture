package com.company.data;

import com.company.entities.Author;
import com.company.entities.Category;
import com.company.entities.Email;
import com.company.entities.Post;
import com.company.enums.CategoryType;
import com.company.orm.EntityManagerFacade;
import com.company.orm.ORM;
import com.github.javafaker.Faker;

import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
    private static Faker faker = new Faker();
    private static List<Author> authors;
    private static List<Category> categories;
    private static Random random = new Random();

    public static void generate() {
        try {
            EntityManagerFacade.getInstance();
            generateAuthors();
            generateCategories();
            generatePosts(100);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void generateCategories() throws Exception {
        if(categories == null) {
            categories = new ArrayList<Category>();
        }

        if(categories.size() >= 10) return;

        EntityTransaction tx = EntityManagerFacade.getEntityManager().getTransaction();
        tx.begin();
        for(int i = 0; i < CategoryType.values().length; i++) {
            ORM<Category> category = new ORM<Category>(Category.class);
            category.set("name", CategoryType.values()[i]);
            EntityManagerFacade.getEntityManager().persist(category.getInstance());
            categories.add(category.getInstance());
        }
        tx.commit();
    }

    private static void generatePosts(int limit) throws Exception {
        EntityTransaction tx = EntityManagerFacade.getEntityManager().getTransaction();
        tx.begin();
        for (int i = 0; i < limit; i++) {
            ORM<Post> post = new ORM<Post>(Post.class);
            post.set("title", faker.book().title());
            post.set("content", String.join(". ", faker.lorem().sentences(20)).substring(0, 255));
            post.set("author", authors.get(random.nextInt(authors.size())));
            List<Category> cats = categories.subList(0, random.nextInt(categories.size()));
            post.set("categories", cats);
//            post.save();
            EntityManagerFacade.getEntityManager().persist(post.getInstance());
        }
        tx.commit();
    }

    private static void generateAuthors() throws Exception {
        if(authors == null) {
            authors = new ArrayList<Author>();
        }

        if(authors.size() == 10) return;

        EntityTransaction tx = EntityManagerFacade.getEntityManager().getTransaction();
        tx.begin();
        for(int i = 0; i < 10; i++) {
            ORM<Author> author = new ORM<Author>(Author.class);
            Email email = new Email(faker.internet().emailAddress());
            author.set("email", email);
            author.set("username", faker.name().username());
            author.set("firstName", faker.name().firstName());
            author.set("lastName", faker.name().lastName());
            author.set("password", faker.internet().password());
//            author.save();
            EntityManagerFacade.getEntityManager().persist(author.getInstance());
            authors.add(author.getInstance());
        }
        tx.commit();
    }
}
