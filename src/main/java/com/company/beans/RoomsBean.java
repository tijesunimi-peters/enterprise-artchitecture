package com.company.beans;

import com.company.ejbs.RoomEJB;
import com.company.entities.Room;
import com.company.interceptors.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named("roomsBean")
@Interceptors({Logger.class})
public class RoomsBean implements Serializable {
    @EJB
    RoomEJB roomEJB;

    private Room room;

    @PostConstruct
    void init() {
        room = new Room();
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Room> getRooms() {
        return roomEJB.getAll();
    }

    public String create() {
        roomEJB.save(room);
        return "rooms?faces-redirect=true";
    }
}
