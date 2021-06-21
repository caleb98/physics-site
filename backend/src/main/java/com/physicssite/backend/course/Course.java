package com.physicssite.backend.course;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "courses")
public class Course {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	private String splashImageFile;
	private String jsonDataFile;
	
	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSplashImageFile() {
		return splashImageFile;
	}
	
	public void setSplashImageFile(String splashImageFile) {
		this.splashImageFile = splashImageFile;
	}
	
	public String getJsonDataFile() {
		return jsonDataFile;
	}
	
	public void setJsonDataFile(String jsonDataFile) {
		this.jsonDataFile = jsonDataFile;
	}
	
}
