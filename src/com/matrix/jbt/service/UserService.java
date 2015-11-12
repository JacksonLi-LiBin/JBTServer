package com.matrix.jbt.service;

import java.util.List;

import com.matrix.jbt.entity.User;

public interface UserService {

	/**
	 * get user by user id
	 * 
	 * @param userId
	 * @return
	 * @author JacksonLi User
	 */
	public User getUserInDatabase(String userId);

	/**
	 * change the password of user
	 * 
	 * @param oldPwd
	 *            the old password, default is 000000
	 * @param newPwd
	 *            the password you want to change
	 * @return if success return true, else return false
	 */
	public boolean changePassword(String userId, String oldPwd, String newPwd);

	/**
	 * change the flag from 0 to 1 (never login to login once or more)
	 * 
	 * @param flag
	 * @return if success return true, else return false
	 */
	public boolean changeFlag(String userId);

	/**
	 * check the user is first login or not
	 * 
	 * @param userId
	 *            user's id
	 * @return if is first login return true, else return false
	 */
	public boolean isFirstLogin(String userId);

	/**
	 * change the email of user
	 * 
	 * @param userId
	 * @param email
	 * @return if success return true, else return false
	 */
	public boolean changeEmail(String userId, String email);

	/**
	 * forget password
	 * 
	 * @author JacksonLi
	 * @param userId
	 * @param newPwd
	 * @return
	 */
	public boolean forgetPassword(String userId, String newPwd);

	/**
	 * update user status 0 logout 1 login
	 * 
	 * @param userId
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean updateStatus(String userId, int status);

	/**
	 * get all users
	 * 
	 * @return
	 * @author JacksonLi List<User>
	 */
	public List<User> getAllUsers();

	/**
	 * get user flag 0 never login otherwise get 1
	 * 
	 * @param userId
	 * @return
	 * @author JacksonLi String
	 */
	public String getUserFlag(String userId);

	/**
	 * user login and update his status or flag
	 * 
	 * @param user
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean userLogin(User user, String registionId);

	/**
	 * init user flag when start server
	 * 
	 * @author JacksonLi void
	 */
	public void initAllUserFlag();

	/**
	 * find user
	 * 
	 * @param userID
	 * @param pwd
	 * @author JacksonLi
	 */
	public boolean findUser(String userID, String pwd);
}
