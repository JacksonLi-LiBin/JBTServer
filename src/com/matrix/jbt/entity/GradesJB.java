package com.matrix.jbt.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GradesJB {
	private String id;
	private Integer studentId;
	private String courseNumber;
	private String cycleNumber;
	private String subject;
	private String points;

	public GradesJB() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GradesJB(String id, Integer studentId, String courseNumber,
			String cycleNumber, String subject, String points) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.courseNumber = courseNumber;
		this.cycleNumber = cycleNumber;
		this.subject = subject;
		this.points = points;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public String getCycleNumber() {
		return cycleNumber;
	}

	public void setCycleNumber(String cycleNumber) {
		this.cycleNumber = cycleNumber;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "GradeJB [id=" + id + ", studentId=" + studentId
				+ ", courseNumber=" + courseNumber + ", cycleNumber="
				+ cycleNumber + ", subject=" + subject + ", points=" + points
				+ "]";
	}

}
