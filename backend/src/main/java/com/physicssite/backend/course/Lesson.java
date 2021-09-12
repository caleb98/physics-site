package com.physicssite.backend.course;

public class Lesson {
	
	private String title;
	
	@SuppressWarnings("unused")
	private Lesson() {
		title = "";
	}
	
	public Lesson(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
