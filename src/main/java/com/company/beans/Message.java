package com.company.beans;

import com.company.enums.MessageType;

import javax.ejb.Stateless;
import java.io.Serializable;

@Stateless
public class Message implements Serializable, Cloneable {
    private MessageType type;
    private String message;

    public Message() {}

    public Message(MessageType type, String message) {
        this.type = type;
        this.message = message;
    }

    public Message(Message message) {
        this.type = message.getType();
        this.message = message.getMessage();
    }

    public Message(String message) {
        type = MessageType.Info;
        this.message = message;
    }

    public MessageType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
