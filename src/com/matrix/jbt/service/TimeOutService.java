package com.matrix.jbt.service;

public interface TimeOutService {
	/**
	 * update time out
	 * 
	 * @author JacksonLi
	 * @param time
	 * @return
	 */
	public boolean updateOutTime(String time);

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
	 * update change password time out
	 * 
	 * @author JacksonLi
	 * @param time
	 * @return
	 */
	public boolean updateCPWDOutTime(String time);

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
