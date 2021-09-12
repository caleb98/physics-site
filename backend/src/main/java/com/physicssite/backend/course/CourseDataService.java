package com.physicssite.backend.course;

import java.io.File;
import java.util.List;

public interface CourseDataService {
	
	public String getError();
	
	public List<Course> listCourses();
	public boolean createCourse(String courseName, String description);
	public void deleteCourse(String courseName);
	public boolean renameCourse(String oldName, String newName);
	
	public Course loadCourse(String courseName);
	public boolean saveCourse(Course course);
	
	public File getCourseFile(String courseName);
}
