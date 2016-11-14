package org.shawn.tutorials.jsf.ui;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import javax.faces.bean.ManagedBean;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangliang on 2016/5/11.
 */
@ManagedBean(name = "test")
public class Test {

    public void viewCars() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        RequestContext.getCurrentInstance().openDialog("viewCars", options, null);
    }
}
