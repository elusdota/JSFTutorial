package org.shawn.tutorials.jsf.ui;

import com.jrtech.templates.domain.Organization;
import com.jrtech.templates.domain.Role;
import com.jrtech.templates.services.OrganizationService;
import com.jrtech.templates.services.RoleService;
import org.primefaces.context.RequestContext;
import org.shawn.tutorials.jsf.model.OrganizationTreeNodeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 * Created by jiangliang on 2016/5/18.
 */
@Component("createRoleView")
@ManagedBean
public class CreateRoleView {
    @Autowired
    private RoleService service;
    @Autowired
    private OrganizationService organizationService;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void create(OrganizationTreeNodeModel organizationTreeNodeModel) {
        Role role = new Role(name);
        role.setOrganization(organizationService.findOne(organizationTreeNodeModel.getId()));
        service.save(role);
        RequestContext.getCurrentInstance().closeDialog("createRole");
    }

    public void onSubmit() {
        if (name != null) {
            FacesMessage msg = new FacesMessage("save", "save role: Success");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            name = null;
        } else {
            FacesMessage msg = new FacesMessage("save", "save role: error");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            name = null;
        }
    }
}
