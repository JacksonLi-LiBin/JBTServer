package com.matrix.jbt.dao;

public interface OutTimeDao {
	/**
	 * update time out
	 * 
	 * @author JacksonLi
	 * @param time
	 * @return
	 */
	public boolean updateOutTime(String time);

	/**
	 * update change password time out
	 * 
	 * @author JacksonLi
	 * @param time
	 * @return
	 */
	public boolean updateCPWDOutTime(String time);

	/**
	 * get time out
	 * 
	 * @author JacksonLi
	 * @return
	 */
	public String getTimeOut();

	/**
	 * get change password time out
	 * 
	 * @author JacksonLi
	 * @return
	 */
	public String getCPWDTimeOut();

	/**
	 * get first get data from john bryce
	 * 
	 * @return
	 * @author JacksonLi String
	 */
	public String getFirstGetDataTime();

	/**
	 * get second get data from john bryce
	 * 
	 * @return
	 * @author JacksonLi String
	 */
	public String getSecondGetDataTime();

	/**
	 * update first get data from john bryce time
	 * 
	 * @param fTime
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean updateFirstGetDataTime(String fTime);

	/**
	 * update second get data from john bryce time
	 * 
	 * @param sTime
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean updateSecondGetDataTime(String sTime);
}
