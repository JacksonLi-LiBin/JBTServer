package com.matrix.jbt.entity;

/**
 * email entity
 * 
 * @author JacksonLi
 * @date 2014/3/13
 */
public class Email {
	private int emailId;
	private String email;

	public int getEmailId() {
		return emailId;
	}

	public void setEmailId(int emailId) {
		this.emailId = emailId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Email() {
		super();
	}

	public Email(String email) {
		super();
		this.email = email;
	}

	public Email(int emailId, String email) {
		super();
		this.emailId = emailId;
		this.email = email;
	}

}
