package com.matrix.jbt.entity;

public class Domain {
	private int id;
	private String domain;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String toString() {
		return "Domain [id=" + id + ", domain=" + domain + "]";
	}
}
