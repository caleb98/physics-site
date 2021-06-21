package com.physicssite.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configuration
public class PhysicsSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhysicsSiteApplication.class, args);
	}

}
