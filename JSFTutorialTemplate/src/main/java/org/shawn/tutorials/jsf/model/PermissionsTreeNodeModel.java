package org.shawn.tutorials.jsf.model;

import com.jrtech.templates.domain.Account;
import com.jrtech.templates.domain.GrantedAuthorityImpl;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.TreeNodeChildren;

import java.util.Collection;
import java.util.List;

/**
 * Created by jiangliang on 2016/4/28.
 */
public class PermissionsTreeNodeModel extends GrantedAuthorityImpl {

    public PermissionsTreeNodeModel(GrantedAuthorityImpl grantedAuthority, Collection<GrantedAuthorityImpl> authorities) {
        this.id = grantedAuthority.getId();
        this.setName(grantedAuthority.getName());
        this.setParent(grantedAuthority.getParent());
        this.setSrc(grantedAuthority.getSrc());
        this.setAccessType(grantedAuthority.getAccessType());
        if (authorities == null) {
            this.accountHas = false;
        }else {
            this.accountHas = authorities.contains(grantedAuthority);
        }
        this.treeNode = new DefaultTreeNode(this);
        this.treeNode.setParent(new DefaultTreeNode(this.getParent()));
        this.treeNode.setExpanded(true);
        for (GrantedAuthorityImpl grantedAuthority1 : grantedAuthority.getChildren()) {
            if (authorities == null) {
                this.treeNode.getChildren().add(buildTree(grantedAuthority1, null));
            } else {
                this.treeNode.getChildren().add(buildTree(grantedAuthority1, authorities));
            }
        }
    }

    private boolean accountHas;
    private TreeNode treeNode;
    private String id;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TreeNode getTreeNode() {
        return treeNode;
    }

    public void setTreeNode(TreeNode treeNode) {
        this.treeNode = treeNode;
    }

    public TreeNode buildTree(GrantedAuthorityImpl grantedAuthority, Collection<GrantedAuthorityImpl> authorities) {
        TreeNode treeNode = new PermissionsTreeNodeModel(grantedAuthority, authorities).getTreeNode();
        return treeNode;
    }

    public boolean isAccountHas() {
        return accountHas;
    }

    public void setAccountHas(boolean accountHas) {
        this.accountHas = accountHas;
    }
}
