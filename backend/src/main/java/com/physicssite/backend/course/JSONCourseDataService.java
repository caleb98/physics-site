package com.physicssite.backend.course;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

@Service
public class JSONCourseDataService implements CourseDataService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${PHYSICS_SITE_DATA_DIR}")
	private String COURSE_JSON_DIR_NAME;
	private File COURSE_JSON_DIR;
	
	@Autowired
	private Gson gson;
	
	@PostConstruct
	private void postConstruct() {
		COURSE_JSON_DIR = new File(COURSE_JSON_DIR_NAME + "/courses");
		if(!COURSE_JSON_DIR.exists()) {
			COURSE_JSON_DIR.mkdirs();
		}
	}

	public List<Course> listCourses() {
		// List course files
		File[] jsonFiles = COURSE_JSON_DIR.listFiles((f)->{
			return f.getName().endsWith(".json");
		});
		
		// Loop through and add them
		ArrayList<Course> courses = new ArrayList<>();
		for(File cfile : jsonFiles) {
			try(JsonReader reader = new JsonReader(new FileReader(cfile))) {
				Course course = gson.fromJson(reader, Course.class);
				courses.add(course);
			} catch (IOException e) {
				logger.error("Error reading course data file: {}", e.getMessage());
			}
		}
		
		return courses;
	}
	
	@Override
	public boolean createCourse(String courseName, String description) {
		// Create the file
		File jsonFile = new File(COURSE_JSON_DIR, getFileName(courseName));
		try {
			if(!jsonFile.createNewFile()) {
				logger.error("Error creating new course data file: data file already exists.");
				return false;
			}
		} catch (IOException e) {
			logger.error("Error creating course data file: {}", e.getMessage());
			return false;
		}
		
		// Write root to file
		Course course;
		try(JsonWriter writer = new JsonWriter(new FileWriter(jsonFile))) {
			course = new Course(courseName, description);
			gson.toJson(course, Course.class, writer);
			writer.flush();
		} catch (IOException e) {
			logger.error("Error writing course data file: {}", e.getMessage());
			return false;
		}
		
		return true;
	}
	
	@Override
	public void deleteCourse(String courseName) {
		// Create the file
		File jsonFile = new File(COURSE_JSON_DIR, getFileName(courseName));
		jsonFile.delete();
	}

	@Override
	public Course loadCourse(String courseName) {
		// Find the file
		File jsonFile = new File(COURSE_JSON_DIR, getFileName(courseName));
		
		// Read
		try(JsonReader reader = new JsonReader(new FileReader(jsonFile))) {
			Course course = gson.fromJson(reader, Course.class);
			return course;
		} catch (IOException e) {
			logger.error("Error reading course data file: {}", e.getMessage());
			return null;
		}
	}

	@Override
	public boolean saveCourse(Course course) {
		// Create the file
		File jsonFile = new File(COURSE_JSON_DIR, getFileName(course.getName()));
		
		// Write root to file
		try(JsonWriter writer = new JsonWriter(new FileWriter(jsonFile))) {
			gson.toJson(course, Course.class, writer);
			writer.flush();
		} catch (IOException e) {
			logger.error("Error writing course data file: {}", e.getMessage());
			return false;
		}
		
		return true;
	}
	
	private String getFileName(String courseName) {
		String fname = courseName.toLowerCase().replace(" ", "_");
		return fname + ".json";
	}
	
}
