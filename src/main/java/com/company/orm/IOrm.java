package com.company.orm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.function.Function;

public interface IOrm<T> {
//    Reflections reflections = new Reflections("com.company");
//    Set<Class<? extends ORM>> modules = reflections.getSubTypesOf(ORM.class);
//        for (Class<? extends ORM> mod : modules) {
//        try {
//            Class<?> a = mod;
////                mod.
//            findSomething.apply(mod);
////                instance = a;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

//    public static final Function<Class, Void> findSomething = (clazz) -> {
//        try {
//            Field field = clazz.getSuperclass().getDeclaredField("instance");
//            field.setAccessible(true);
//            System.out.println("------ "+clazz.getName());
//            field.set(field, clazz.getName());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    };


//    public static <T> T remove(Object id) {
//        executor.apply(entityManagerFactory, entityManager, (Void v) -> {
//            try {
//
//                Class<T> clazz = (Class<T>) instance.getClass();
//                System.out.println(Class.forName(clazz.getCanonicalName()));
//                T result = entityManager.find(clazz, id);
//                entityManager.detach(result);
//                entityManager.merge(result);
//                System.out.printf("\n----\n %s \n %s \n------\n", entityManager.contains(result), result.getClass().getSimpleName());
//                entityManager.remove(result);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//            return null;
//        });
//
//        return null;
//    }

    <T> T findById(Object id);
}
