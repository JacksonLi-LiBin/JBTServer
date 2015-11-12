package com.matrix.jbt.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * maslul entity
 * 
 * @author JacksonLi
 * @date 2014/3/13
 */
@XmlRootElement
public class Maslul {
	private int maslulId;
	private String maslulNo;
	private int userId;

	public int getMaslulId() {
		return maslulId;
	}

	public void setMaslulId(int maslulId) {
		this.maslulId = maslulId;
	}

	public String getMaslulNo() {
		return maslulNo;
	}

	public void setMaslulNo(String maslulNo) {
		this.maslulNo = maslulNo;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Maslul() {

	}

	public Maslul(String maslulNo, int userId) {
		super();
		this.maslulNo = maslulNo;
		this.userId = userId;
	}

	public Maslul(int maslulId, String maslulNo, int userId) {
		super();
		this.maslulId = maslulId;
		this.maslulNo = maslulNo;
		this.userId = userId;
	}

}
