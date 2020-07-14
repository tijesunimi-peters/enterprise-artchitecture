package com.company.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String url;

    @ManyToMany(mappedBy = "images")
    private List<Gallery> posts;
}
