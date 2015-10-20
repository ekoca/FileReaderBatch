package com.emrekoca.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.emrekoca.motd.MessageOfTheDayService;

public class ClientApplication {
	private static ClassPathXmlApplicationContext container;

	public static void main(String[] args) {
		container = new ClassPathXmlApplicationContext("application.xml");
		MessageOfTheDayService helloWorld = 
				container.getBean("motdService", MessageOfTheDayService.class);
		System.out.println(helloWorld.getTodaysMessage());
		// Spring clean up resources!
		container.registerShutdownHook();
	}
}
