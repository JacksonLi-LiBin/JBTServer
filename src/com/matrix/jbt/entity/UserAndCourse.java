package com.matrix.jbt.entity;

import java.util.List;

public class UserAndCourse {
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private List<String> maslulNos;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getMaslulNos() {
		return maslulNos;
	}

	public void setMaslulNos(List<String> maslulNos) {
		this.maslulNos = maslulNos;
	}

	@Override
	public String toString() {
		return "UserAndCourse [userId=" + userId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email
				+ ", maslulNos=" + maslulNos + "]";
	}

}
