package com.company.beans;


import com.company.enums.MessageType;
import com.company.interceptors.Logger;
import com.company.interceptors.MessageInterceptor;
import com.github.javafaker.Bool;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Stateless
@Named("messagesBean")
@Interceptors({Logger.class})
public class MessagesBean implements Serializable {

    private List<Message> messages;
    private boolean isRead = false;
    private String sessionId;

    public boolean isRead() {
        return isRead;
    }

    @PostConstruct
    void init() {
        sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(true);
        messages = new ArrayList();
    }

    public void addMessage(String message) {
        messages.add(new Message(MessageType.Info, message));
    }

    public void addError(String message) {
        messages.add(new Message(MessageType.Error, message));
    }

    public void addSuccess(String message) {
        messages.add(new Message(MessageType.Success, message));
    }

    public boolean shouldShow(Map<String, String> query) {
        isRead = Boolean.parseBoolean(query.get("showMsgs"));
        if (!isRead) clear();
        return isRead;
    }

    public List<Message> getMessages() {
        List<Message> output = new ArrayList();

        for (Message s : messages) {
            output.add(new Message(s));
        }

        return output;
    }

    public void clear() {
        messages.clear();
    }

    @Override
    public String toString() {
        return "MessagesBean{" +
                "messages=" + messages +
                '}';
    }
}
