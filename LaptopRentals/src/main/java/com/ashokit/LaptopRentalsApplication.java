package com.ashokit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.ashokit.model") 
@EnableJpaRepositories(basePackages = "com.ashokit.repository")
public class LaptopRentalsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaptopRentalsApplication.class, args);
	}

}
