package com.emrekoca.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.emrekoca.ApplicationStarter;


/**
 * 
 * @author Emre Koca
 *
 */
@SpringBootApplication
public class BigFilesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigFilesApplication.class, args).getBean(ApplicationStarter.class).run();
	}
}
