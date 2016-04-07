package org.shawn.tutorials.jsf;

import javax.faces.bean.ManagedBean;

@ManagedBean(name="Home")
public class HomeController {

	public String sayHello() {
		return "Hello JSF!";
	}
}
