package com.physicssite.backend.course;

import java.util.ArrayList;

public class Unit {

	private String title;
	private String description;
	private ArrayList<Lesson> lessons;
	
	public Unit(String title, String description) {
		this.title = title;
		this.description =description;
		lessons = new ArrayList<>();
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public ArrayList<Lesson> getLessons() {
		return lessons;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void addLesson(Lesson lesson) {
		lessons.add(lesson);
	}
	
}
