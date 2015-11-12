package com.matrix.jbt.dao;

import java.util.Map;

public interface RegistionIdDao {
	/**
	 * save user's registion id into database
	 * 
	 * @param userid
	 * @param registionId
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean addRegistionId(int userid, String registionId);

	/**
	 * delete registion id depend on user id
	 * 
	 * @param userid
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean deleteRegistionId(int userid);

	/**
	 * update registion id depend on user id
	 * 
	 * @param userid
	 * @param registionId
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean updateRegistionId(int userid, String registionId);

	/**
	 * get registion id according to user id
	 * 
	 * @param userid
	 * @return
	 * @author JacksonLi String
	 */
	public String getRegistionId(int userid);

	/**
	 * get all registion id from database
	 * 
	 * @return
	 * @author JacksonLi Map<Integer,String>
	 */
	public Map<Integer, String> getAllRegistionId();
}
