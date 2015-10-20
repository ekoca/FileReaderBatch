package com.emrekoca.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.emrekoca.autowired.Hello;

public class ClientApplication {
	private static ClassPathXmlApplicationContext container;

	public static void main(String[] args) {
		container = new ClassPathXmlApplicationContext("application.xml");
		Hello helloWorld = container.getBean("hello", Hello.class);
		System.out.println(helloWorld);
		// Spring clean up resources!
		container.registerShutdownHook();
	}
}
