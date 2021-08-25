package com.physicssite.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.physicssite.backend.course.CourseDataService;
import com.physicssite.backend.course.JSONCourseDataService;

@SpringBootApplication
@Configuration
public class PhysicsSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhysicsSiteApplication.class, args);
	}
	
	@Bean
	public Gson createDefaultGson() {
		return new GsonBuilder().setPrettyPrinting().create();
	}
	
	@Bean
	public CourseDataService courseDataService() {
		return new JSONCourseDataService();
	}

}
