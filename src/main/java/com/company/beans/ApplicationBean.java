package com.company.beans;


import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.Map;

@ApplicationScoped
@Named("applicationBean")
public class ApplicationBean {
    public Map<String, String> getSomething() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    }
}

