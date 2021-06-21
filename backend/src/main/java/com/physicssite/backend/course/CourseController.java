package com.physicssite.backend.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/course")
public class CourseController {

	@Autowired
	private CourseRepository courseRepository;
	
	@GetMapping("/all")
	public @ResponseBody Iterable<Course> getAllCourses() {
		return courseRepository.findAll();
	}
	
	@PostMapping("/add")
	public @ResponseBody String addNewCourse(@RequestParam String name, @RequestParam String splashImageFile) {
		Course course = new Course();
		course.setName(name);
		course.setSplashImageFile(splashImageFile);
		courseRepository.save(course);
		return "Added!";
	}
	
}
