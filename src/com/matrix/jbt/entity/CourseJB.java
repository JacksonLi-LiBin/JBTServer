package com.matrix.jbt.entity;

public class CourseJB {
	private Integer id;
	private String courseName;
	private String courseNum;
	private String cycleNum;
	private String cycleOpenDate;
	private String domainNumber;
	private String domainName;
	private String userId;

	public CourseJB() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CourseJB(Integer id, String courseName, String courseNum,
			String cycleNum, String cycleOpenDate, String domainNumber,
			String domainName, String userId) {
		super();
		this.id = id;
		this.courseName = courseName;
		this.courseNum = courseNum;
		this.cycleNum = cycleNum;
		this.cycleOpenDate = cycleOpenDate;
		this.domainNumber = domainNumber;
		this.domainName = domainName;
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(String courseNum) {
		this.courseNum = courseNum;
	}

	public String getCycleNum() {
		return cycleNum;
	}

	public void setCycleNum(String cycleNum) {
		this.cycleNum = cycleNum;
	}

	public String getCycleOpenDate() {
		return cycleOpenDate;
	}

	public void setCycleOpenDate(String cycleOpenDate) {
		this.cycleOpenDate = cycleOpenDate;
	}

	public String getDomainNumber() {
		return domainNumber;
	}

	public void setDomainNumber(String domainNumber) {
		this.domainNumber = domainNumber;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "CourseJB [id=" + id + ", courseName=" + courseName
				+ ", courseNum=" + courseNum + ", cycleNum=" + cycleNum
				+ ", cycleOpenDate=" + cycleOpenDate + ", domainNumber="
				+ domainNumber + ", domainName=" + domainName + ", userId="
				+ userId + "]";
	}

}
