package com.company.interceptors;

import javax.interceptor.AroundConstruct;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.Date;

public class Logger implements Serializable {
    @AroundInvoke
    public Object log(InvocationContext context) {
        try {
            Date startTime = new Date();

            System.out.printf(
                    "-----------------------< %s started at %s >--------------\n",
                    context.getMethod().getName(),
                    startTime
            );

            Object result = context.proceed();
            Date endTime = new Date();

            System.out.printf(
                    "-----------------------< %s ended in %f seconds >--------------\n",
                    context.getMethod().getName(),
                    (endTime.getTime() - startTime.getTime()) / 1000.0
            );

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
