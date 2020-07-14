package com.company.ejbs;

import com.company.beans.MessagesBean;
import com.company.entities.Student;
import com.company.exceptions.RoomNotVacantException;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class StudentEJB implements Serializable {
    @PersistenceContext(unitName = "testingea2")
    EntityManager entityManager;

    @Inject
    MessagesBean messagesBean;

    @Inject
    RoomEJB roomEJB;

    private List<Student> students;

    @PostConstruct
    void init() {
        students = new ArrayList<Student>();
    }

    public Student find(Integer id) {
        for (Student s : students) {
            if (Integer.valueOf(s.getId()).equals(id)) {
                return s;
            }
        }

        Student s = entityManager.find(Student.class, id);
        students.add(s);
        return s;
    }

    public boolean persist(Student student) throws Exception {
        if(student.getRoom().getVacant()) {
            entityManager.persist(student);
            student.getRoom().setVacant(false);
            roomEJB.update(student.getRoom());
            return true;
        } else {
            throw new RoomNotVacantException();
        }
    }

    public void persist(Student student, Integer roomId) {
        entityManager.persist(student);
    }

    public boolean update(Student student) {
        boolean success = false;

        try {
            entityManager.merge(student);
            success = true;
        } catch (OptimisticLockException ex) {
            ex.printStackTrace();
        }

        return success;
    }

    public void remove(Integer id) {
        Student student1 = entityManager.find(Student.class, id);
        entityManager.remove(student1);
    }

    public List<Student> searchStudentsByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> studentQuery = cb.createQuery(Student.class);
        Root<Student> studentRoot = studentQuery.from(Student.class);

        Predicate firstNamePredicate = cb.like(studentRoot.<String>get("firstName"), "%" + name + "%");
        Predicate lastNamePredicate = cb.like(studentRoot.<String>get("lastName"), "%" + name + "%");

        Predicate combinedPredicate = cb.or(firstNamePredicate, lastNamePredicate);

        studentQuery.select(studentRoot).where(combinedPredicate);

        students = entityManager.createQuery(studentQuery).setMaxResults(3).getResultList();
        return students;
    }

//    @Schedule(hour = "*", minute = "*", persistent = false)
    public void printStudentCount() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> studentQuery = cb.createQuery(Long.class);
        Root<Student> studentRoot = studentQuery.from(Student.class);

        studentQuery.select(cb.count(studentRoot));

        System.out.printf(
                "\n>-------------\n Student count : %s \n>-----------------\n",
                entityManager.createQuery(studentQuery).getResultList().get(0)
        );
    }
}
