package com.company.beans;


import com.company.ejbs.StudentEJB;
import com.company.entities.Student;
import com.company.interceptors.Logger;
import com.company.orm.ORM;

import javax.annotation.PostConstruct;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Named("studentsBean")
@ViewScoped
@Interceptors({Logger.class})
public class Students implements Serializable {
    @Inject
    transient private EntityManagerBean entityManagerBean;

    @Inject
    private MessagesBean messagesBean;

    @Inject
    private StudentEJB studentEJB;

    private ORM<Student> studentsORM;

    private String filterName;

    private List<Student> students;

    @PostConstruct
    void init() {
        students = new ArrayList();
    }

    public List<Student> getStudents() {
        if(filterName != null) {
            students = studentEJB.searchStudentsByName(filterName);
        } else {
//            students = studentsORM.getService().getAll();
//            students = studentEJB.get
        }

        return students;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public void filterStudents(AjaxBehaviorEvent event) {
        String name = (String) ((UIOutput) event.getSource()).getValue();
        filterName = name;
    }

    public String remove(Integer studentId) {
        studentEJB.remove(studentId);
        return null;
    }
}
