package org.shawn.tutorials.jsf;

import org.springframework.stereotype.Component;

@Component
//@Scope("request")
public class HomeService {
	public String sayHello() {
		return "Hello from spring bean.";
	}
}
