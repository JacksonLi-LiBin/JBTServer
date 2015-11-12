package com.matrix.jbt.entity;

/**
 * 
 * @author JacksonLi
 * @date 2014/4/25
 */
public class RegistionId {
	private int userid;
	private String registion_id;

	public RegistionId(int userid, String registion_id) {
		super();
		this.userid = userid;
		this.registion_id = registion_id;
	}

	public RegistionId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getRegistion_id() {
		return registion_id;
	}

	public void setRegistion_id(String registion_id) {
		this.registion_id = registion_id;
	}

	@Override
	public String toString() {
		return "RegistionId [userid=" + userid + ", registion_id="
				+ registion_id + "]";
	}

}
