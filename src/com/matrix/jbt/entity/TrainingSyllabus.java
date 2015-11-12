package com.matrix.jbt.entity;

/**
 * entity TrainingSyllabus
 * 
 * @author JacksonLi
 * @date 2014/4/28
 */
public class TrainingSyllabus {
	private int cycleNumber, CourseNumber;
	private String CourseName;

	public TrainingSyllabus(int cycleNumber, int courseNumber, String courseName) {
		super();
		this.cycleNumber = cycleNumber;
		CourseNumber = courseNumber;
		CourseName = courseName;
	}

	public TrainingSyllabus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getCycleNumber() {
		return cycleNumber;
	}

	public void setCycleNumber(int cycleNumber) {
		this.cycleNumber = cycleNumber;
	}

	public int getCourseNumber() {
		return CourseNumber;
	}

	public void setCourseNumber(int courseNumber) {
		CourseNumber = courseNumber;
	}

	public String getCourseName() {
		return CourseName;
	}

	public void setCourseName(String courseName) {
		CourseName = courseName;
	}

	@Override
	public String toString() {
		return "TrainingSyllabus [cycleNumber=" + cycleNumber
				+ ", CourseNumber=" + CourseNumber + ", CourseName="
				+ CourseName + "]";
	}

}
