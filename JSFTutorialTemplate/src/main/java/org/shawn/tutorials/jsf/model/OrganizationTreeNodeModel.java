package org.shawn.tutorials.jsf.model;

import com.jrtech.templates.domain.Account;
import com.jrtech.templates.domain.Organization;
import com.jrtech.templates.domain.Role;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * Created by jiangliang on 2016/4/28.
 */
public class OrganizationTreeNodeModel extends Organization {
    private boolean accountHas;
    private TreeNode treeNode;
    private String id;

    public TreeNode getTreeNode() {
        return treeNode;
    }

    public void setTreeNode(TreeNode treeNode) {
        this.treeNode = treeNode;
    }

    public OrganizationTreeNodeModel(Organization organization, Account account) {
        this.id=organization.getId();
        this.setName(organization.getName());
        this.setParent(organization.getParent());
        this.setAccessType(organization.getAccessType());
        this.accountHas = false;
        this.treeNode = new DefaultTreeNode(this);
        this.treeNode.setExpanded(true);
        this.treeNode.setParent(new DefaultTreeNode(this.getParent()));
        if (organization.getChildren().size() < 1) {
            for (Role role : organization.getRoles()) {
                if (account != null) {
                    this.treeNode.getChildren().add(buildTree(role, account.getRoles().contains(role)));
                } else {
                    this.treeNode.getChildren().add(buildTree(role, false));
                }
            }
        } else {
            for (Organization organization1 : organization.getChildren()) {
                this.treeNode.getChildren().add(buildTree(organization1, account));
            }
        }
    }

    public boolean isAccountHas() {
        return accountHas;
    }

    public void setAccountHas(boolean accountHas) {
        this.accountHas = accountHas;
    }

    public TreeNode buildTree(Role role, boolean accountHas) {
        TreeNode treeNode = new RoleTreeNodeModel(role, accountHas).getTreeNode();
        return treeNode;
    }

    public TreeNode buildTree(Organization organization, Account account) {
        TreeNode treeNode = new OrganizationTreeNodeModel(organization, account).getTreeNode();
        return treeNode;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
