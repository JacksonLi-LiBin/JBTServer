package com.matrix.jbt.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * job alert entity
 * 
 * @author JacksonLi
 * @date 2014/3/13
 */
@XmlRootElement
public class JobAlert implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String periodname;
	private String periodvalue;

	public JobAlert() {
		super();
	}

	public JobAlert(String periodname, String periodvalue) {
		super();
		this.periodname = periodname;
		this.periodvalue = periodvalue;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPeriodname() {
		return periodname;
	}

	public void setPeriodname(String periodname) {
		this.periodname = periodname;
	}

	public String getPeriodvalue() {
		return periodvalue;
	}

	public void setPeriodvalue(String periodvalue) {
		this.periodvalue = periodvalue;
	}

	@Override
	public String toString() {
		return "JobAlert [id=" + id + ", periodname=" + periodname
				+ ", periodvalue=" + periodvalue + "]";
	}

}
