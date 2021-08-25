package com.physicssite.backend.course;

public interface CourseDataService {
	
	/**
	 * Creates a new course json file for the given course listing.
	 * This method should <b>not</b> overrite any existing data
	 * should a file for the requested course listing already be
	 * present.
	 * @param listing the course listing to create data for
	 * @return the course object representing the newly created course; null if data could not be saved
	 */
	public Course createCourseData(CourseListing listing);
	
	/**
	 * Loads the course data for a given course listing.
	 * @param listing the course listing to load data for
	 * @return the course data
	 */
	public Course loadCourse(CourseListing listing);

	/**
	 * Saves the data for a course given the course listing.
	 * @param listing
	 * @param course course data to save
	 * @return true if the save was successful; false if error
	 */
	public boolean saveCourse(CourseListing listing, Course course);
	
}
