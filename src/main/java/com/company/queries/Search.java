package com.company.queries;

import com.company.entities.Author;
import com.company.entities.Category;
import com.company.entities.Post;
import com.company.entities.Student;
import com.company.enums.CategoryType;
import com.company.orm.EntityManagerFacade;

import javax.persistence.criteria.*;
import java.util.List;

public class Search {
    public static List<Post> categoryPosts(CategoryType category) {
        CriteriaBuilder criteriaBuilder = EntityManagerFacade.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
        Root<Post> postRoot = criteriaQuery.from(Post.class);
        Join<Post, Category> postCategoryJoin = postRoot.join("categories");

        Predicate catPredicate = criteriaBuilder.like(postCategoryJoin.get("name"), category.name());
        criteriaQuery.select(postRoot);
        criteriaQuery.where(catPredicate);

        return EntityManagerFacade.getEntityManager().createQuery(criteriaQuery).getResultList();
    }

    public static List<Object[]> authorsPostsCount(CategoryType category) {
        CriteriaBuilder criteriaBuilder = EntityManagerFacade.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);
        Root<Author> authorRoot = query.from(Author.class);

        Join<Author,Post> postSubquery = authorRoot.join("posts");
        Join<Post, Category> postCategoryJoin = postSubquery.join("categories");

        Predicate catJoin = criteriaBuilder.like(postCategoryJoin.get("name"), category.name());

        query.multiselect(authorRoot, criteriaBuilder.count(postSubquery))
        .groupBy(authorRoot)
        .where(catJoin);

        return EntityManagerFacade.getEntityManager().createQuery(query).getResultList();
    }

    public static List<Student> searchStudentsByName(String name) {
        CriteriaBuilder cb = EntityManagerFacade.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Student> studentQuery = cb.createQuery(Student.class);
        Root<Student> studentRoot = studentQuery.from(Student.class);

        Predicate firstNamePredicate = cb.like(studentRoot.get("firstName"), "%" + name + "%");
        Predicate lastNamePredicate = cb.like(studentRoot.get("lastName"), "%" + name + "%");

        Predicate combinedPredicate = cb.or(firstNamePredicate, lastNamePredicate);

        studentQuery.select(studentRoot).where(combinedPredicate);

        return EntityManagerFacade.getEntityManager().createQuery(studentQuery).getResultList();
    }
}
