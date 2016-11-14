package org.shawn.tutorials.jsf.ui;

import com.jrtech.templates.domain.GrantedAuthorityImpl;
import com.jrtech.templates.services.GrantedAuthorityService;
import org.primefaces.model.menu.*;
import org.shawn.tutorials.jsf.security.UserDetailsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by jiangliang on 2016/4/26.
 */
@Component("menuView")
@ManagedBean
public class MenuView {
    private MenuModel model;
    @Autowired
    private GrantedAuthorityService service;
    @Autowired
    private UserDetailsUtils userDetailsUtils;
    private List<GrantedAuthorityImpl> grantedAuthorities;

    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();
        grantedAuthorities = service.findRoot();
        grantedAuthorities.forEach(grantedAuthority1 -> {
            DefaultSubMenu submenu = new DefaultSubMenu();
            submenu.setId(grantedAuthority1.getId());
            submenu.setLabel(grantedAuthority1.getName());
            model.addElement(bulidDefaultMenuItem(grantedAuthority1.getChildren(), submenu));
        });
    }

    public MenuModel getModel() {
        grantedAuthorities.forEach(grantedAuthority1 -> {
            model.getElements().forEach(menuElement -> {
                DefaultSubMenu subMenu = (DefaultSubMenu) menuElement;
                if (subMenu.getLabel().equals(grantedAuthority1.getName())) {
                    if (!userDetailsUtils.isAuthorized(grantedAuthority1.getAuthority())) {
                        subMenu.setRendered(false);
                    } else {
                        if (grantedAuthority1.getChildren().size() > 0) {
                            grantedAuthority1.getChildren().forEach(grantedAuthority -> {
                                subMenu.getElements().forEach(menuElement1 -> {
                                    DefaultMenuItem item = (DefaultMenuItem) menuElement1;
                                    if (item.getValue().equals(grantedAuthority.getName())) {
                                        item.setRendered(userDetailsUtils.isAuthorized(grantedAuthority.getAuthority()));
                                    }
                                });
                            });
                        }
                        subMenu.setRendered(true);
                    }
                }
            });

        });
        return model;
    }

    public DefaultSubMenu bulidDefaultMenuItem(Collection<GrantedAuthorityImpl> grantedAuthorities1, DefaultSubMenu submenu) {
        for (GrantedAuthorityImpl grantedAuthority : grantedAuthorities1) {
//            if (userDetailsUtils.isAuthorized(grantedAuthority.getAuthority())) {
            String src = grantedAuthority.getSrc();
            DefaultMenuItem item = new DefaultMenuItem();
            item.setValue(grantedAuthority.getName());
            item.setParam("name", grantedAuthority.getName());
            if (src != null) {
                item.setParam("src", src);
            } else {
                item.setParam("src", "/jsf/home.xhtml");
            }
            item.setUpdate(":WindowContainer");
            item.setImmediate(true);
            item.setCommand("#{tabMenuView.addTab}");
            item.setIcon("ui-icon-home");
            submenu.addElement(item);
//            }
        }
        return submenu;
    }

    public String getUsername() {
        return userDetailsUtils.getCurrent().getUsername();
    }
}
