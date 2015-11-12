package com.matrix.jbt.dao;

public interface AdminDao {
	/**
	 * admin login
	 * 
	 * @author JacksonLi
	 * @param userName
	 * @param password
	 * @return
	 */
	public String adminLogin(String userName, String password);
}
