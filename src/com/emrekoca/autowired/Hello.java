package com.emrekoca.autowired;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Hello {
	static Logger logger = Logger.getLogger(Hello.class);
	@Override
	public String toString() {
		logger.info("Hello log :");
		return "Hello world";
	}
}
