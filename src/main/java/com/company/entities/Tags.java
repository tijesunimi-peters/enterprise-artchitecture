package com.company.entities;

import com.company.enums.CategoryType;

import javax.persistence.*;


@MappedSuperclass
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private CategoryType name;

    public int getId() {
        return id;
    }

    public CategoryType getName() {
        return name;
    }

    public void setName(CategoryType name) {
        this.name = name;
    }
}
