package org.shawn.tutorials.jsf.model;

import org.primefaces.component.tabview.Tab;

import javax.faces.application.ResourceDependencies;

/**
 * Created by jiangliang on 2016/4/27.
 */
@ResourceDependencies({})
public class TabModel extends Tab {
    private String src;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
