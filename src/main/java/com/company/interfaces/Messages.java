package com.company.interfaces;

import com.company.beans.MessagesBean;

public interface Messages {
    default String messageQuery(MessagesBean messagesBean, String url) {
        if(messagesBean.getMessages().size() > 0) {
            String showMsgs = "showMsgs=true";
            return url.contains("?") ? String.format("%s&%s", url, showMsgs) : String.format("%s?%s", url, showMsgs);
        }

        return url;
    }
}
