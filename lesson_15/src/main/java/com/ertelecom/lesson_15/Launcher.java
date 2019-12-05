package com.ertelecom.lesson_15;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.ertelecom"})
@EntityScan(basePackages = {"com.ertelecom"})
public class Launcher {

	public static void main(String[] args) {
		SpringApplication.run(Launcher.class, args);
	}

}
