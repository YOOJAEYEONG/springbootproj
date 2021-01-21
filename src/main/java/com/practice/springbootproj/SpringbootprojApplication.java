package com.practice.springbootproj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.practice.springbootproj.main", "com.practice.springbootproj.board"})
public class SpringbootprojApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootprojApplication.class, args);
	}

}
