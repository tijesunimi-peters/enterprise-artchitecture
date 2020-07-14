package com.company.listeners;

import com.company.beans.EntityManagerBean;
import com.company.beans.MessagesBean;
import com.company.entities.Student;
import com.company.orm.EntityManagerFacade;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.PostPersist;
import javax.persistence.PreRemove;

@Stateful
public class StudentListener {
    @Inject
    private MessagesBean messagesBean;

    @Inject
    private EntityManagerBean entityManagerBean;

    @PreRemove
    void preDestroy(Student student) {
        messagesBean.addSuccess("Student has been deleted successfully");
    }

    @PostPersist
    void postPersist(Student student) {
        if(!EntityManagerFacade.getEntityManager().contains(student)) {
//            messagesBean.addError("Student not created for " + student);
        }
    }
}
