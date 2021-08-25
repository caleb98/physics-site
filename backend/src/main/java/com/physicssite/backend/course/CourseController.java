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
	
	@Autowired
	private CourseDataService courseDataService;
	
	@GetMapping("/all")
	public @ResponseBody Iterable<CourseListing> getAllCourses() {
		return courseRepository.findAll();
	}
	
	@PostMapping("/add")
	public @ResponseBody String addNewCourse(@RequestParam String name, @RequestParam String description) {
		// Create the course listing
		CourseListing listing = new CourseListing();
		listing.setName(name);
		
		// Attempt to create the course data
		Course course = courseDataService.createCourseData(listing);
		if(course == null) {
			return "Unable to create course! Error creating course data file.";
		}
		
		// Setup the course data and try to save it
		course.setDescription(description);
		if(!courseDataService.saveCourse(listing, course)) {
			return "Unable to create course! Error saving course data.";
		}
		
		// Everything was successful, so add the course listing to the database.
		courseRepository.save(listing);
		
		return "Added!";
	}
	
}
