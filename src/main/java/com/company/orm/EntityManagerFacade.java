package com.company.orm;

import com.company.EntityExecutor;

import javax.persistence.*;
import java.io.InputStream;
import java.util.Properties;


public class EntityManagerFacade {
    private static EntityManagerFactory entityManagerFactory;
    private static Boolean jta = false;
    private static EntityManager entityManager;

    private EntityManagerFacade() {
    }

    public static EntityExecutor getInstance() {
        setPersistenceUnitName(findPersistenceUnitName());
        return entityExecutor;
    }

    public static EntityExecutor getInstance(EntityManagerFactory emf) {
        if(entityManager == null) {
            setPersistenceUnitName(emf);
        }
        return entityExecutor;
    }

    public static EntityExecutor getInstance(EntityManagerFactory emf, boolean isJta) {
        if(entityManager == null) {
            setPersistenceUnitName(emf);
            jta = isJta;
        }

        return entityExecutor;
    }

    //    EntityManagerFactory throws exception if name is null
    private static void setPersistenceUnitName(EntityManagerFactory emf) {
        entityManagerFactory = emf;
        entityManager = entityManagerFactory.createEntityManager();
    }

    public static EntityExecutor getInstance(EntityManager em, boolean isJta) {
        if(entityManager == null) {
            entityManagerFactory = em.getEntityManagerFactory();
            entityManager = em;
            jta = isJta;
        }

        return entityExecutor;
    }


    private static void setPersistenceUnitName(String name) {
        entityManagerFactory = Persistence.createEntityManagerFactory(name);
        entityManager = entityManagerFactory.createEntityManager();
    }

    private static String findPersistenceUnitName() {
        if(entityManagerFactory == null) {
            try {
                InputStream file = EntityManagerFacade.class.getResourceAsStream("../application.properties");
                Properties properties = new Properties();
                properties.load(file);
                file.close();
                return (String) properties.get("persistenceUnitName");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static EntityManagerFactory getFactory() {
        return entityManagerFactory;
    }

    public static void close() {
        entityManager.close();
        entityManagerFactory.close();
    }

    public static final EntityExecutor entityExecutor = (lambda) -> {
        if(!jta) {
            EntityTransaction tx = entityManager.getTransaction();
            tx.begin();
            lambda.apply(entityManager);
            tx.commit();
        } else {
            lambda.apply(entityManager);
        }

        return null;
    };


}
