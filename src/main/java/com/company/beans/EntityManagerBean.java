package com.company.beans;

import com.company.orm.EntityManagerFacade;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.persistence.*;
import java.io.Serializable;

@Singleton
public class EntityManagerBean implements Serializable {
    @PersistenceUnit(unitName = "testingea2")
    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext(unitName = "testingea2", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @PostConstruct
    void initialize() {
        EntityManagerFacade.getInstance(entityManager, true);
    }
}
