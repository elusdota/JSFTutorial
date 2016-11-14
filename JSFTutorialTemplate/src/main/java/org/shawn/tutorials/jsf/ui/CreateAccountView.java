package org.shawn.tutorials.jsf.ui;

import com.jrtech.templates.domain.Account;
import com.jrtech.templates.services.AccountService;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 * Created by jiangliang on 2016/5/12.
 */
@Component("createAccountView")
@ManagedBean
public class CreateAccountView {
    @Autowired
    private AccountService service;
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void create() {
        if (name != null && password != null) {
            service.save(new Account(name, password));
            RequestContext.getCurrentInstance().closeDialog("createAccount");
        }
    }

    public void onSubmit() {
        if (name != null && password != null) {
            FacesMessage msg = new FacesMessage("save", "save account: Success");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            name = password = null;
        } else {
            FacesMessage msg = new FacesMessage("save", "save account: error");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            name = password = null;
        }
    }
}
