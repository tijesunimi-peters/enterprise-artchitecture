package com.company.ejbs;


import com.company.entities.Room;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RoomEJB {
    @PersistenceContext(unitName = "testingea2")
    EntityManager entityManager;

    public void save(Room room) {
        entityManager.persist(room);
    }

    public List<Room> getAll() {
        List<Room> rooms = entityManager.createNamedQuery("Room.getAll").getResultList();
        return rooms;
    }

    public Room find(Integer id) {
        return entityManager.find(Room.class, id);
    }

    public void update(Room room) {
        entityManager.merge(room);
    }
}
