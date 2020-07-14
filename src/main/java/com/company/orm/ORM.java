package com.company.orm;

import com.company.annotations.CrudInterface;
import com.company.exceptions.NotAnEntityException;

import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public final class ORM<T> {
    private T instance;
    private String tableName;

    private Service<?> service;


    private boolean checkIfIsEntity(Class<T> o) throws NotAnEntityException {
        List<Class> annotationClasses = new ArrayList<>() {{
            add(Entity.class);
        }};

        boolean found = false;

        for (Annotation annotation : o.getAnnotations()) {
            if (annotationClasses.contains(annotation.annotationType())) {
                found = true;
                break;
            }
        }

        if (!found) throw new NotAnEntityException();

        return found;
    }

    public void set(String prop, Object value) throws Exception {
        invokeSetter(String.format("set%s", prop), value);
    }

    public <S> S get(Object prop) throws Exception {
        String method = String.format("get%s", prop);
        S result = (S) invokeGetter(method);
        return result;
    }

    private Set<String> getAllFields() {
        Set<String> fields = new HashSet<>();
        Class<?> current = instance.getClass();

        while (current.getSuperclass() != null && current.getSimpleName() != "Object") {
            fields.addAll(Arrays.asList(current.getDeclaredFields())
                    .stream()
                    .filter(x -> !x.getName().startsWith("_"))
                    .filter(x -> !x.getName().contains("VersionUID"))
                    .map(x -> x.getName()).collect(Collectors.toList()));
            current = current.getSuperclass();
        }

        return fields;
    }

    private List<Method> getAllMethods() {
        List<Method> methods = new ArrayList<>();
        Class<?> current = instance.getClass();

        while (current.getSuperclass() != null && current.getSimpleName() != "Object") {
            methods.addAll(Arrays.asList(current.getMethods()));
            current = current.getSuperclass();
        }

        return methods;
    }

    private Object invokeGetter(String prop) throws Exception {
        for (Method method : getAllMethods()) {
            if (method.getName().toLowerCase().equals(prop.toLowerCase())) {
                return method.invoke(instance);
            }
        }

        return null;
    }

    private void invokeSetter(String prop, Object value) throws Exception {
        Object valueToStore = value;

        if (valueToStore instanceof ORM) {
            valueToStore = ((ORM) value).getInstance();
        }

        for (Method method : getAllMethods()) {
            if (method.getName().toLowerCase().equals(prop.toLowerCase())) {
                try {
                    method.invoke(instance, valueToStore);
                } catch (IllegalArgumentException ex) {
                    throw new Exception(method.getName() + " requires " + method + " but " + valueToStore.getClass() + " was supplied");
                }
                break;
            }
        }
    }

    public ORM(Class<T> o) {
        try {
            if (checkIfIsEntity(o)) {
                instance = o.getConstructor().newInstance();
                setService(o);
            } else {
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ORM(T o) {
        try {
            if (checkIfIsEntity((Class<T>) o.getClass())) {
                instance = o;
                setService((Class<T>) o.getClass());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setService(Class<T> clazz) throws Exception {
        for (Annotation annotation : getAllAnnotations(clazz)) {
            if (annotation.annotationType().equals(CrudInterface.class)) {
                service = new Service<>(clazz, getAllFields());
                break;
            }
        }
    }

    public Service getService() {
        return service;
    }

    private List<Annotation> getAllAnnotations(Class<T> clazz) {
        List<Annotation> annotations = new ArrayList<>();
        Class<?> current = clazz;
        while (current.getSuperclass() != null) {
            annotations.addAll(Arrays.asList(clazz.getAnnotations()));
            current = current.getSuperclass();
        }

        return annotations;
    }

    public void save() throws Exception {
        EntityManagerFacade.entityExecutor.apply((entityManager) -> {
            entityManager.persist(instance);
            return null;
        });
    }

    public void update() throws Exception {
        EntityManagerFacade.entityExecutor.apply((entityManager) -> {
            entityManager.merge(instance);
            return null;
        });
    }

    public void update(String prop, Object value) throws Exception {
        EntityManagerFacade.entityExecutor.apply((entityManager) -> {
            try {
                invokeSetter(prop, value);
                entityManager.merge(instance);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        });
    }

    public void remove() {
        EntityManagerFacade.entityExecutor.apply(entityManager -> {
            if(!entityManager.contains(instance)) {
                entityManager.merge(instance);
            }

            System.err.println("This is good " + entityManager.contains(instance));
            entityManager.remove(instance);
            return null;
        });
    }

    public T getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return instance.toString();
    }

    public class Service<S> implements IOrm<S> {
        final S clazz;

        public Service(Class<S> clazz, Set<String> fields) {
            this.clazz = (S) clazz;
            setNamedQueries(clazz, fields);
        }

        private void setNamedQueries(Class<S> klazz, Set<String> fields) {
            System.err.println(fields);
            String qString = String.format("SELECT s FROM %s s", klazz.getSimpleName());
            TypedQuery<S> q = EntityManagerFacade.getEntityManager().createQuery(qString, klazz);
            EntityManagerFacade.getFactory().addNamedQuery(klazz.getSimpleName() + ".findAll", q);

            for (String field : fields) {
                qString = String.format("SELECT s FROM %s s WHERE s.%s = :param", klazz.getSimpleName(), field);
                q = EntityManagerFacade.getEntityManager().createQuery(qString, klazz);
                EntityManagerFacade.getFactory().addNamedQuery(klazz.getSimpleName() + ".findBy." + field, q);
            }

            qString = String.format("DELETE FROM %s s", klazz.getSimpleName());
            Query query = EntityManagerFacade.getEntityManager().createQuery(qString);
            EntityManagerFacade.getFactory().addNamedQuery(klazz.getSimpleName() + ".deleteAll", query);
        }

        @Override
        public ORM<S> findById(Object id) {
            S result = EntityManagerFacade.getEntityManager().find(((Class<S>) clazz), id);
            if(result == null) {
                return null;
            } else {
                return new ORM<>(result);
            }
        }

        private Class<S> getKlass() {
            return (Class<S>) clazz;
        }

        public List<S> getAll() {
            return EntityManagerFacade
                    .getEntityManager()
                    .createNamedQuery(getKlass().getSimpleName() + ".findAll", getKlass())
                    .getResultList();
        }

        public void deleteAll() {
            EntityManagerFacade
                    .getEntityManager()
                    .createNamedQuery(getKlass().getSimpleName() + ".deleteAll", getKlass())
                    .getResultList();
        }

        public List<S> findBy(String fieldname, Object param) {
            return EntityManagerFacade
                    .getEntityManager()
                    .createNamedQuery(getKlass().getSimpleName() + ".findBy." + fieldname, getKlass())
                    .setParameter("param", param)
                    .getResultList();
        }

        public TypedQuery<S> query() {
//            return EntityManagerFacade.getEntityManager().createQuery(clazz);
            return null;
        }
    }
}
