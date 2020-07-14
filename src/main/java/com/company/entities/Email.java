package com.company.entities;

import javax.persistence.Embeddable;

@Embeddable
public class Email {
    private String email;

    public Email() {
    }

    public Email(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Email{" +
                "email='" + email + '\'' +
                '}';
    }
}
