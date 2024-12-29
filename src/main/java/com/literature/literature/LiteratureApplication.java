package com.literature.literature;

import com.literature.literature.controller.ConsoleController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LiteratureApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(LiteratureApplication.class, args);
		ConsoleController consoleController = context.getBean(ConsoleController.class);
		consoleController.start();
	}
}
