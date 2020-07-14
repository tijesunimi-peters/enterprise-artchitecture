package com.company.beans;

import com.company.ejbs.RoomEJB;
import com.company.ejbs.StudentEJB;
import com.company.entities.Room;
import com.company.entities.Student;
import com.company.exceptions.RoomNotVacantException;
import com.company.interceptors.Logger;
import com.company.interfaces.Messages;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named("newStudentBean")
@Interceptors({Logger.class})
public class NewStudent implements Serializable, Messages {
    @Inject
    transient private EntityManagerBean entityManagerBean;

    @Inject
    private StudentEJB studentEJB;

    @Inject
    private RoomEJB roomEJB;

    @Inject
    private MessagesBean messagesBean;

    private Student student;
    private String pageName = "New";

    private Integer studentId;

    private Integer roomId;

    @PostConstruct
    void init() {
        String rawStudentId = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("studentId");

        studentId = rawStudentId != null ? Integer.valueOf(rawStudentId) : null;

        if (studentId == null) {
            student = new Student();
        } else {
            pageName = "Edit";
            student = studentEJB.find(studentId);
            roomId = student.getId();
        }
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getPageName() {
        return pageName;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public boolean showGpa() {
        Integer id = student.getId();
        return !id.equals(0);
    }

    public List<Room> getRooms() {
        return roomEJB.getAll();
    }

    public String create() throws Exception {
        Integer id = student.getId();

        if (!id.equals(0)) {
            if (studentEJB.update(student)) {
                messagesBean.addSuccess("Student record updated successfully");
            } else {
                messagesBean.addError("Transaction error; Please try again later");
            }

            return messageQuery(messagesBean,"new-student?faces-redirect=true&studentId=" + id);
        } else {
            try {
                student.setRoom(roomEJB.find(roomId));
                if (studentEJB.persist(student)) {
                    messagesBean.addSuccess("New Student created");
                }
            } catch (RoomNotVacantException ex) {
                messagesBean.addError("Room is not vacant; Please choose another room");
                ex.printStackTrace();
            }

            return messageQuery(messagesBean,"new-student");
        }

    }
}
