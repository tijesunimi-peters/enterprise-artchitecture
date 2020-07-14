package com.company.entities;

import javax.persistence.*;

@Entity
@Table(name = "rooms")
@NamedQueries({
        @NamedQuery(name = "Room.getAll", query = "SELECT r FROM Room r")
})
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Version
    private int version;

    @Column(name = "building_name")
    private String buildingName;

    @Column(name = "room_number")
    private Integer roomNumber;

    @Column(name = "is_vacant")
    private Boolean isVacant = true;

    @OneToOne(mappedBy = "room")
    private Student student;

    public int getId() {
        return id;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Boolean getVacant() {
        return isVacant;
    }

    public void setVacant(Boolean vacant) {
        isVacant = vacant;
    }
}
