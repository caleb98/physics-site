package com.physicssite.backend.course;

import java.util.List;

public interface CourseDataService {
	
	public List<Course> listCourses();
	public boolean createCourse(String courseName, String description);
	public void deleteCourse(String courseName);
	
	public Course loadCourse(String courseName);
	public boolean saveCourse(Course course);
	
}
