package com.matrix.jbt.entity;

public class BranchJB {
	private Integer id;
	private String branch;
	private String userId;

	public BranchJB() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BranchJB(Integer id, String branch, String userId) {
		super();
		this.id = id;
		this.branch = branch;
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Branch [id=" + id + ", branch=" + branch + ", userId=" + userId
				+ "]";
	}

}
