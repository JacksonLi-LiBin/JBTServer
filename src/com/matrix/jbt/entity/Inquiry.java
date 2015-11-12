package com.matrix.jbt.entity;

import java.io.Serializable;

/**
 * inquiry entity
 * 
 * @author JacksonLi
 * @date 2014/3/13
 */
public class Inquiry implements Serializable {
	private static final long serialVersionUID = 1L;

	private int studentinquiryid;
	private String fullname;
	private String coursename;
	private String coursenum;
	private String cyclenum;
	private String branch;
	private String phone;
	private String email;
	private String subjectnum;
	private String details;
	private int userid;

	public Inquiry() {
		super();
	}

	public Inquiry(String fullname, String coursename, String coursenum,
			String cyclenum, String branch, String phone, String email,
			String subjectnum, String details, int userid) {
		super();
		this.fullname = fullname;
		this.coursename = coursename;
		this.coursenum = coursenum;
		this.cyclenum = cyclenum;
		this.branch = branch;
		this.phone = phone;
		this.email = email;
		this.subjectnum = subjectnum;
		this.details = details;
		this.userid = userid;
	}

	public Inquiry(int studentinquiryid, String fullname, String coursename,
			String coursenum, String cyclenum, String branch, String phone,
			String email, String subjectnum, String details, int userid) {
		super();
		this.studentinquiryid = studentinquiryid;
		this.fullname = fullname;
		this.coursename = coursename;
		this.coursenum = coursenum;
		this.cyclenum = cyclenum;
		this.branch = branch;
		this.phone = phone;
		this.email = email;
		this.subjectnum = subjectnum;
		this.details = details;
		this.userid = userid;
	}

	public int getStudentinquiryid() {
		return studentinquiryid;
	}

	public void setStudentinquiryid(int studentinquiryid) {
		this.studentinquiryid = studentinquiryid;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public String getCoursenum() {
		return coursenum;
	}

	public void setCoursenum(String coursenum) {
		this.coursenum = coursenum;
	}

	public String getCyclenum() {
		return cyclenum;
	}

	public void setCyclenum(String cyclenum) {
		this.cyclenum = cyclenum;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubjectnum() {
		return subjectnum;
	}

	public void setSubjectnum(String subjectnum) {
		this.subjectnum = subjectnum;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

}
