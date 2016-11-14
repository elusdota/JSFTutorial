package org.shawn.tutorials.jsf.ui;

import com.jrtech.templates.domain.GrantedAuthorityImpl;
import com.jrtech.templates.services.GrantedAuthorityService;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.shawn.tutorials.jsf.model.PermissionsTreeNodeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

/**
 * Created by jiangliang on 2016/4/28.
 */
@Component("permissionsView")
@ManagedBean
public class PermissionsView implements Serializable {
    private TreeNode root;
    private PermissionsTreeNodeModel selected;
    @Autowired
    private GrantedAuthorityService gaService;

    @PostConstruct
    public void init() {
        root = createDocuments();
    }

    public TreeNode getRoot() {
        return root;
    }

    public PermissionsTreeNodeModel getSelected() {
        return selected;
    }

    public void setSelected(PermissionsTreeNodeModel selected) {
        this.selected = selected;
    }

    public TreeNode createDocuments() {
        List<GrantedAuthorityImpl> roots = gaService.findRoot();
        TreeNode root = new DefaultTreeNode("root", null);
        if (null == roots) {
            return null;
        } else {
            for (GrantedAuthorityImpl grantedAuthority1 : roots) {
                PermissionsTreeNodeModel permissionsTreeNodeModel = new PermissionsTreeNodeModel(grantedAuthority1, null);
                root.getChildren().add(permissionsTreeNodeModel.getTreeNode());
            }
        }
        return root;
    }

    public void remove() {
        if (getSelected() != null) {
            FacesMessage msg = new FacesMessage("reomve", "reomve permission: " + selected.getName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            gaService.delete(selected.getId());
        }
    }

    public void onNodeSelect(NodeSelectEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
