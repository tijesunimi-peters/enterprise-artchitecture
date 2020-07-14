package com.company.exceptions;

public class NotAnEntityException extends Exception {
    public NotAnEntityException() {
        super("Class must be an Entity");
    }
}
