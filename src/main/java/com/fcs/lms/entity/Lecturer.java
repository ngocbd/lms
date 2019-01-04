package com.fcs.lms.entity;

public class Lecturer {

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public int getLecturerTime() {
		return lecturerTime;
	}

	public void setLecturerTime(int lecturerTime) {
		this.lecturerTime = lecturerTime;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "Lecturer [name=" + name + "]";
	}

	private String name;
	private String objective;
	private String education;
	private String experience;
	private int lecturerTime;
	private String position;
}
