package com.matrix.jbt.service;

import java.util.List;
import java.util.Map;

import com.matrix.jbt.entity.CourseJB;
import com.matrix.jbt.entity.Email;
import com.matrix.jbt.entity.User;

public interface EmailService {
	/**
	 * add a email to database
	 * 
	 * @param email
	 *            com.matrix.jbt.entity.Email
	 * @return if success return true else return false
	 */
	public boolean addEmail(Email email);

	/**
	 * update the email to database
	 * 
	 * @param email
	 *            com.matrix.jbt.entity.Email
	 * @return if success return true else return false
	 */
	public boolean updateEmail(Email email);

	/**
	 * delete a email from database
	 * 
	 * @param emailId
	 *            email's id
	 * @return if success return true else return false
	 */
	public boolean deleteEmail(int emailId);

	/**
	 * get an email by email id
	 * 
	 * @param emailId
	 *            email's id
	 * @return if success return an Email else return null
	 */
	public Email getEmailByEmailId(int emailId);

	/**
	 * get all email from database
	 * 
	 * @return if success return a list of Email, else return null
	 */
	public List<Email> getAll();

	/**
	 * get email from database
	 * 
	 * @return if success return a list of Email, else return null
	 */
	public List<Email> getEmailsByPageNumber(Integer beginRow,
			Integer pageCounts);

	/**
	 * get email with filter character
	 * 
	 * @param beginRow
	 * @param pageCounts
	 * @param filterStr
	 * @return
	 * @author JacksonLi List<Email>
	 */
	public List<Email> getEmailsByPageNumberWithFilter(Integer beginRow,
			Integer pageCounts, String filterStr);

	/**
	 * get all email count
	 * 
	 * @return
	 * @author JacksonLi Integer
	 */
	public Integer getAllEmailCount();

	/**
	 * get email count with filter
	 * 
	 * @param filter
	 * @return
	 * @author JacksonLi Integer
	 */
	public Integer getEmailCountWithFilter(String filter);

	/**
	 * delete email by email name
	 * 
	 * @author JacksonLi
	 * @param emailname
	 * @return
	 */
	public boolean deleteEmail(String emailname);

	/**
	 * update email
	 * 
	 * @author JacksonLi
	 * @param oldEmail
	 * @param newEmail
	 * @return
	 */
	public boolean updateEmail(String oldEmail, String newEmail);

	/**
	 * get all user email
	 * 
	 * @return
	 * @author JacksonLi List<User>
	 */
	public List<User> getAllUserEmail();

	/**
	 * get user email
	 * 
	 * @param beginRow
	 * @param pageCounts
	 * @return
	 * @author JacksonLi List<User>
	 */
	public Map<User, List<CourseJB>> getUserEmailByPage(Integer beginRow,
			Integer pageCounts);

	/**
	 * 
	 * @param beginRow
	 * @param pageCounts
	 * @param filter
	 * @return
	 * @author JacksonLi Map<User,List<CourseJB>>
	 */
	public Map<User, List<CourseJB>> getUserEmailByPageWithFilter(
			Integer beginRow, Integer pageCounts, String filter);

	/**
	 * get all user counts
	 * 
	 * @return
	 * @author JacksonLi Integer
	 */
	public Integer getUserEmailCount();

	/**
	 * get user count with filter name
	 * 
	 * @return
	 * @author JacksonLi Integer
	 */
	public Integer getUserEmailCountWithFilter(String filter);

	/**
	 * edit user's email
	 * 
	 * @param userId
	 * @param email
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean updateUserEmail(Integer userId, String email);
}
