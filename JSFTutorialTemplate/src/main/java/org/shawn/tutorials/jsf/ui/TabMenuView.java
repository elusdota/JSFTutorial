package org.shawn.tutorials.jsf.ui;

import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.MenuActionEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import org.shawn.tutorials.jsf.model.TabModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Iterator;

/**
 * Created by jiangliang on 2016/4/25.
 */
//@Component("tabMenuView")
@ManagedBean
@ViewScoped
public class TabMenuView implements Serializable {
    private int activeIndex;
    private TabView tabView;

    @PostConstruct
    public void init() {
        tabView = new TabView();
    }

    public void addTab(MenuActionEvent event) {
        TabModel newTab = new TabModel();
        newTab.setId(event.getMenuItem().getValue().toString());
        newTab.setTitle(event.getMenuItem().getParams().get("name").get(0));
        newTab.setSrc(event.getMenuItem().getParams().get("src").get(0));
        if (tabView.findTab(newTab.getClientId()) == null) {
            tabView.getChildren().add(newTab);
        }
        setActiveIndex(getIndex(newTab) - 1);
//        RequestContext context = RequestContext.getCurrentInstance();
//        context.update(":form:WindowContainer");
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public TabView getTabView() {
        return tabView;
    }

    public void setTabView(TabView tabView) {
        this.tabView = tabView;
    }

    public void onTabChange(TabChangeEvent event) {
        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onTabClose(TabCloseEvent event) {
        Tab tab = new Tab();
        tab = tabView.findTab(event.getTab().getTitle());
        tabView.getChildren().remove(tab);
        FacesMessage msg = new FacesMessage("Tab Closed", "Closed tab: " + tab.getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public int getIndex(TabModel tab) {
        int index = 0;
        Iterator i$ = tabView.getChildren().iterator();
        UIComponent component;
        do {
            if (!i$.hasNext()) {
                return 0;
            }
            component = (UIComponent) i$.next();
            index++;
        } while (!component.getClientId().equals(tab.getId()));
        return index;
    }
}
