package com.physicssite.backend.course;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
		COURSE_JSON_DIR = new File(COURSE_JSON_DIR_NAME);
		if(!COURSE_JSON_DIR.exists()) {
			COURSE_JSON_DIR.mkdirs();
		}
	}
	
	public Course loadRootSection(CourseListing course) throws FileNotFoundException {
		// Read the file
		File jsonFile = new File(COURSE_JSON_DIR, course.getName() + ".json");
		
		// Read
		JsonReader reader = new JsonReader(new FileReader(jsonFile));
		Course root = gson.fromJson(reader, Course.class);
		
		return root;
	}

	@Override
	public Course createCourseData(CourseListing listing) {
		// Create the file
		File jsonFile = new File(COURSE_JSON_DIR, listing.getName() + ".json");
		try {
			if(!jsonFile.createNewFile()) {
				logger.error("Error creating new course data file: data file already exists.");
				return null;
			}
		} catch (IOException e) {
			logger.error("Error creating course data file: {}", e.getMessage());
			return null;
		}
		
		// Write root to file
		Course course;
		try(JsonWriter writer = new JsonWriter(new FileWriter(jsonFile))) {
			course = new Course(listing.getName(), "This course doesn't have a description yet.");
			gson.toJson(course, Course.class, writer);
			writer.flush();
		} catch (IOException e) {
			logger.error("Error writing course data file: {}", e.getMessage());
			return null;
		}
		
		return course;
	}

	@Override
	public Course loadCourse(CourseListing listing) {
		// Find the file
		File jsonFile = new File(COURSE_JSON_DIR, listing.getName() + ".json");
		
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
	public boolean saveCourse(CourseListing listing, Course course) {
		// Create the file
		File jsonFile = new File(COURSE_JSON_DIR, listing.getName() + ".json");
		
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
	
}
