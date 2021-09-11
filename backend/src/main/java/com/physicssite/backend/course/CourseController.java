package com.physicssite.backend.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.physicssite.backend.dto.ServerResponse;

@Controller
@RequestMapping(path="/course")
public class CourseController {
	
	@Autowired
	private CourseDataService courseDataService;
	
	@GetMapping("/data")
	public @ResponseBody List<Course> getAllCourses() {
		return courseDataService.listCourses();
	}
	
	@PostMapping("/create")
	@Secured("ROLE_ADMIN")
	public @ResponseBody ServerResponse createCourse(@RequestParam String name, @RequestParam String description) {
		if(!courseDataService.createCourse(name, description)) {
			return new ServerResponse("Failed to create new course file.", true);
		}
		
		return new ServerResponse("Course created!");
	}
	
}
