package org.shawn.tutorials.jsf;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "user")
@RequestScoped
public class UserBean implements Serializable {
	private static final long serialVersionUID = 7943186289177219060L;
	
	private String name = "";
	private String password = "";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getGreeting() {
		if (name.length() == 0) return " ";
		return "Welcome to JSF2 + Ajax, " + name + "!";
	}
}
