package org.shawn.tutorials.jsf.model;

import com.jrtech.templates.domain.Account;
import com.jrtech.templates.domain.Role;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * Created by jiangliang on 2016/4/28.
 */
public class RoleTreeNodeModel extends Role {
    private boolean accountHas;
    private TreeNode treeNode;
    private String id;

    public TreeNode getTreeNode() {
        return treeNode;
    }

    public void setTreeNode(TreeNode treeNode) {
        this.treeNode = treeNode;
    }

    public RoleTreeNodeModel(Role role, boolean accountRole) {
        this.id=role.getId();
        this.setAccessType(role.getAccessType());
        this.setName(role.getName());
        this.setOrganization(role.getOrganization());
        this.accountHas = accountRole;
        this.treeNode = new DefaultTreeNode(this);
        this.treeNode.setExpanded(true);
        this.treeNode.setParent(new DefaultTreeNode(role.getOrganization()));
    }

    public boolean isAccountHas() {
        return accountHas;
    }

    public void setAccountHas(boolean accountHas) {
        this.accountHas = accountHas;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
