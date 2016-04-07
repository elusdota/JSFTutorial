package org.shawn.tutorials.jsf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("Home")
//@Scope("request")
public class HomeController {
	
	@Autowired
	private HomeService service;

	public String sayHello() {
		String message = "";
		if (null != service) {
			message = service.sayHello();
		} else {
			message = "Hello JSF!";
		}
		return message;
	}
}
