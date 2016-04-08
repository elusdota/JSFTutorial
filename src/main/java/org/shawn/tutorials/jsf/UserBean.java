package org.shawn.tutorials.jsf;

import org.springframework.stereotype.Component;

@Component("user")
public class UserBean {
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
