package com.physicssite.backend.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
			return new ServerResponse(courseDataService.getError(), true);
		}
		
		return new ServerResponse("Course created!");
	}
	
	@PutMapping("/rename")
	@Secured("ROLE_ADMIN")
	public @ResponseBody ServerResponse renameCourse(@RequestParam String oldName, @RequestParam String newName) {
		if(courseDataService.renameCourse(oldName, newName)) {
			return new ServerResponse("Success!");
		}
		else {
			return new ServerResponse(courseDataService.getError(), true);
		}
	}
	
	@PutMapping("/update")
	@Secured("ROLE_ADMIN")
	public @ResponseBody ServerResponse updateCourse(@RequestBody Course course) {
		if(courseDataService.saveCourse(course)) {
			return new ServerResponse("Success!");
		}
		else {
			return new ServerResponse(courseDataService.getError(), true);
		}
	}
	
	@DeleteMapping("/delete")
	@Secured("ROLE_ADMIN")
	public @ResponseBody ServerResponse deleteCourse(@RequestParam String courseName) {
		courseDataService.deleteCourse(courseName);
		return new ServerResponse("Success!");
	}
	
}
