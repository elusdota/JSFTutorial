package org.shawn.tutorials.jsf.ui;

import com.jrtech.templates.domain.Account;
import com.jrtech.templates.domain.Organization;
import com.jrtech.templates.domain.Role;
import com.jrtech.templates.services.AccountService;
import com.jrtech.templates.services.RoleService;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.shawn.tutorials.jsf.model.OrganizationTreeNodeModel;
import org.shawn.tutorials.jsf.security.UserDetailsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.faces.bean.ViewScoped;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangliang on 2016/4/28.
 */
@Component("accountView")
@ManagedBean
@ViewScoped
public class AccountView implements Serializable {
    @Autowired
    private UserDetailsUtils userDetailsUtils;
    @Autowired
    private AccountService service;
    @Autowired
    private RoleService roleService;
    private List<Account> accounts;
    private Account selected;
    private TreeNode root;
    private boolean create;
    private boolean update;
    private boolean remove;
    private boolean updateRole;
    private boolean retirement;
    private boolean edit;
    private boolean refresh;

    public Iterable<Account> getAccounts() {
        return service.findAll();
    }

    public Account getSelected() {
        return selected;
    }

    public void setSelected(Account selected) {
        this.selected = selected;
    }

    @PostConstruct
    public void init() {
        root = new DefaultTreeNode("root", null);
    }

    public TreeNode getRoot() {
        if (selected != null && refresh) {
            root.getChildren().clear();
            for (Organization organization : service.getOrganizations(userDetailsUtils.getCurrent().getUsername())) {
                OrganizationTreeNodeModel organizationTreeNodeModel = new OrganizationTreeNodeModel(organization, selected);
                root.getChildren().add(organizationTreeNodeModel.getTreeNode());
            }
            refresh = false;
        }
        return root;
    }

    public void onRowSelect(SelectEvent event) {
        setSelected(((Account) event.getObject()));
        refresh = true;
    }

    public void remove() {
        try {
            if (selected != null) {
                service.delete(selected.getId());
                FacesMessage msg = new FacesMessage("reomve", "reomve account: " + selected.getName());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                selected = null;
            } else {
                FacesMessage msg = new FacesMessage("reomve", "reomve account: no data");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (RuntimeException ex) {
            FacesMessage msg = new FacesMessage("delete", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowUnselect(UnselectEvent event) {
    }

    public boolean isCreate() {
        return userDetailsUtils.isAuthorized("/权限管理/帐户管理/创建帐户");
    }

    public boolean isUpdate() {
        return userDetailsUtils.isAuthorized("/权限管理/帐户管理/更改帐户密码");
    }

    public boolean isRemove() {
        return userDetailsUtils.isAuthorized("/权限管理/帐户管理/注销帐户");
    }

    public boolean isUpdateRole() {
        return userDetailsUtils.isAuthorized("/权限管理/帐户管理/为帐户分配角色");
    }

    public boolean isRetirement() {
        return retirement;
    }

    public void setRetirement(boolean retirement) {
        this.retirement = retirement;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isRefresh() {
        return refresh;
    }

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }

    public void viewCreate() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("modal", true);
        options.put("header", "header");
        options.put("showEffect", "fade");
        options.put("immediate", true);
        options.put("appendTo", "@(body)");
        RequestContext.getCurrentInstance().openDialog("createAccount", options, null);
    }

    public void edit() {
        setRetirement(true);
        setEdit(true);
    }

    public void retirement() {
        setEdit(false);
        setRetirement(false);
    }

    public void setRole(String id, boolean check) {
        try {
            if (selected != null) {
                Role role = roleService.findOne(id);
                if (!check) {
                    selected.getRoles().remove(role);
                } else {
                    selected.getRoles().add(role);
                }
                service.save(selected);
            } else {
                FacesMessage msg = new FacesMessage("save", "save role: error");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (RuntimeException ex) {
            FacesMessage msg = new FacesMessage("save", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
