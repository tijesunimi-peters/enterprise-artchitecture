package com.company.exceptions;

public class RoomNotVacantException extends Exception {
    public RoomNotVacantException() {
        super("Room allocation failed; Room not vacant");
    }
}
