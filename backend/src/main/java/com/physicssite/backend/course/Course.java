package com.physicssite.backend.course;

import java.util.ArrayList;

public class Course {
	
	private String name;
	private String description;
	private ArrayList<Unit> units;
	
	@SuppressWarnings("unused")
	private Course() {
		name = "";
		description = "";
		units = new ArrayList<>();
	}
	
	public Course(String name, String description) {
		this.name = name;
		this.description = description;
		units = new ArrayList<>();
	}

	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public ArrayList<Unit> getUnits() {
		return units;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void addUnit(Unit unit) {
		units.add(unit);
	}
	
}