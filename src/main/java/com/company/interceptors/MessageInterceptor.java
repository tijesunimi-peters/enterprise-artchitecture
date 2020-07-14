package com.company.interceptors;

import com.company.beans.Message;
import com.company.beans.MessagesBean;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MessageInterceptor {
    @AroundInvoke
    public Object clearMessages(InvocationContext context) throws Exception {
        MessagesBean messagesBean = (MessagesBean) context.getTarget();
        List<Message> result = (List<Message>) context.proceed();
        List<Message> output = result.stream().collect(Collectors.toList());
//        System.err.println(output);
//        System.err.println("------------------------< I called the getMessages >----------------------- " + messagesBean.isRead());
//        messagesBean.clear();
        return output;
    }
}
