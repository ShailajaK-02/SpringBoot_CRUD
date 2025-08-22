package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyFirstCrudOperationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyFirstCrudOperationApplication.class, args);
		System.out.println("This project is created by Shailaja ");
	}

}
