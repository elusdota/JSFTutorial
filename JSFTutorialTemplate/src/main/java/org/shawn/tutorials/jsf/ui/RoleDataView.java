package org.shawn.tutorials.jsf.ui;

import com.jrtech.templates.domain.GrantedAuthorityImpl;
import com.jrtech.templates.domain.Organization;
import com.jrtech.templates.domain.Role;
import com.jrtech.templates.services.AccountService;
import com.jrtech.templates.services.GrantedAuthorityService;
import com.jrtech.templates.services.OrganizationService;
import com.jrtech.templates.services.RoleService;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.shawn.tutorials.jsf.model.OrganizationTreeNodeModel;
import org.shawn.tutorials.jsf.model.PermissionsTreeNodeModel;
import org.shawn.tutorials.jsf.model.RoleTreeNodeModel;
import org.shawn.tutorials.jsf.security.UserDetailsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;

/**
 * Created by jiangliang on 2016/5/18.
 */
@Component("roleDataView")
@ManagedBean
@ViewScoped
public class RoleDataView implements Serializable {
    private TreeNode root1;
    private TreeNode root2;
    private List<GrantedAuthorityImpl> roots;
    private RoleTreeNodeModel selected;
    private OrganizationTreeNodeModel selected1;
    @Autowired
    private GrantedAuthorityService gaService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserDetailsUtils userDetailsUtils;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleView roleView;
    @Autowired
    private CreateRoleView createRoleView;
    private boolean refresh;

    @PostConstruct
    public void init() {
        root1 = new DefaultTreeNode("root", null);
        root2 = new DefaultTreeNode("root", null);
        roots = gaService.findRoot();
        for (GrantedAuthorityImpl grantedAuthority1 : roots) {
            PermissionsTreeNodeModel permissionsTreeNodeModel = new PermissionsTreeNodeModel(grantedAuthority1, null);
            root2.getChildren().add(permissionsTreeNodeModel.getTreeNode());
        }
    }

    public TreeNode getRoot1() {
        root1.getChildren().clear();
        for (Organization organization : accountService.getOrganizations(userDetailsUtils.getCurrent().getUsername())) {
            OrganizationTreeNodeModel organizationTreeNodeModel = new OrganizationTreeNodeModel(organization, null);
            root1.getChildren().add(organizationTreeNodeModel.getTreeNode());
        }
        return root1;
    }

    public void setRoot1(TreeNode root1) {
        this.root1 = root1;
    }

    public TreeNode getRoot2() {
        if (selected != null && refresh) {
            Collection<GrantedAuthorityImpl> authorities = new TreeSet<>();
            root2.getChildren().clear();
            authorities = roleService.findOne(selected.getId()).getAuthorities();
            final Collection<GrantedAuthorityImpl> finalAuthorities = authorities;
            roots.forEach(anth -> {
                PermissionsTreeNodeModel permissionsTreeNodeModel = new PermissionsTreeNodeModel(anth, finalAuthorities);
                root2.getChildren().add(permissionsTreeNodeModel.getTreeNode());
            });
            refresh = false;
        }
        return root2;
    }

    public void onNodeSelect(NodeSelectEvent event) {
        refresh = true;
        try {
            this.selected = (RoleTreeNodeModel) event.getTreeNode().getData();
            roleView.setDisabled(false);
            roleView.setEditDisabled(true);
            FacesMessage msg = new FacesMessage("select", "select role: " + selected.getName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            this.selected1 = null;
        } catch (RuntimeException ex) {
            this.selected1 = (OrganizationTreeNodeModel) event.getTreeNode().getData();
            roleView.setDisabled(true);
            roleView.setEditDisabled(false);
            FacesMessage msg = new FacesMessage("select", "select organization: " + selected1.getName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            this.selected = null;
        }
    }

    public void setRoot2(TreeNode root2) {
        this.root2 = root2;
    }

    public List<GrantedAuthorityImpl> getRoots() {
        return roots;
    }

    public void viewCreate() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("modal", true);
        options.put("header", "header");
        options.put("showEffect", "fade");
        options.put("immediate", true);
        options.put("appendTo", "@(body)");
        RequestContext.getCurrentInstance().openDialog("createRole", options, null);
    }

    public void create() {
        if (selected1 != null) {
            createRoleView.create(selected1);
        }
    }

    public void edit() {
        roleView.setEditDisabled(false);
        roleView.setEdit(true);
        roleView.setRetirement(true);
    }

    public void setPermission(String id, boolean check) {
        Collection<GrantedAuthorityImpl> authorities = new TreeSet<>();
        try {
            if (selected != null) {
                Role role = roleService.findOne(selected.getId());
                GrantedAuthorityImpl grantedAuthority = gaService.findOne(id);
                authorities.addAll(role.getAuthorities());
                role.getAuthorities().clear();
                if (!check) {
                    authorities.remove(grantedAuthority);
                } else {
                    authorities.add(grantedAuthority);
                }
                role.getAuthorities().addAll(authorities);
                roleService.save(role);
                FacesMessage msg = new FacesMessage("save", "save premission: ok!" + grantedAuthority.getAuthority());
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesMessage msg = new FacesMessage("save", "save premission: error");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (RuntimeException ex) {
            FacesMessage msg = new FacesMessage("save", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void retirement() {
        roleView.setEdit(false);
        roleView.setRetirement(false);
    }
}
