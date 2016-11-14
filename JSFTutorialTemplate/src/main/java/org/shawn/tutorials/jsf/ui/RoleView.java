package org.shawn.tutorials.jsf.ui;

import org.shawn.tutorials.jsf.security.UserDetailsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

/**
 * Created by jiangliang on 2016/4/29.
 */
@Component("roleView")
@ManagedBean
@ViewScoped
public class RoleView implements Serializable {
    private boolean create;
    private boolean update;
    private boolean remove;
    private static boolean disabled;
    private boolean createOrganization;
    private boolean permission;
    private static boolean editDisabled;
    private static boolean edit;
    private static boolean retirement;

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isEditDisabled() {
        return editDisabled;
    }

    public void setEditDisabled(boolean editDisabled) {
        this.editDisabled = editDisabled;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isRetirement() {
        return retirement;
    }

    public void setRetirement(boolean retirement) {
        this.retirement = retirement;
    }

    @Autowired
    private UserDetailsUtils userDetailsUtils;

    public boolean isCreate() {
        return userDetailsUtils.isAuthorized("/权限管理/角色管理/创建角色");
    }

    public boolean isUpdate() {
        return userDetailsUtils.isAuthorized("/权限管理/角色管理/修改角色名称");
    }

    public boolean isRemove() {
        return userDetailsUtils.isAuthorized("/权限管理/角色管理/删除角色");
    }

    public boolean isCreateOrganization() {
        return userDetailsUtils.isAuthorized("/权限管理/角色管理/创建机构");
    }

    public boolean isPermission() {
        return userDetailsUtils.isAuthorized("/权限管理/角色管理/为角色分配权限");
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }
}
