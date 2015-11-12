package com.matrix.jbt.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * user entity
 * 
 * @author JacksonLi
 * @date 2014/3/13
 */
@XmlRootElement
public class User {
	private String userId;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private int flag;
	private int status;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String userId, String firstName, String lastName,
			String password, String email, int flag, int status) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.flag = flag;
		this.status = status;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", password=" + password
				+ ", email=" + email + ", flag=" + flag + ", status=" + status
				+ "]";
	}

}
