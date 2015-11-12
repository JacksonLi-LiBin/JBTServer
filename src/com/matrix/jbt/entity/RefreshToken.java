package com.matrix.jbt.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Refresh token entity
 * 
 * @author JacksonLi
 * @date 2014/3/21
 */
@XmlRootElement
public class RefreshToken {
	private int userid;
	private String cloudtype;
	private String email;
	private String refreshtoken;

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getCloudtype() {
		return cloudtype;
	}

	public void setCloudtype(String cloudtype) {
		this.cloudtype = cloudtype;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRefreshtoken() {
		return refreshtoken;
	}

	public void setRefreshtoken(String refreshtoken) {
		this.refreshtoken = refreshtoken;
	}

	@Override
	public String toString() {
		return "RefreshToken [userid=" + userid + ", cloudtype=" + cloudtype
				+ ", email=" + email + ", refreshtoken=" + refreshtoken + "]";
	}

}
