package com.matrix.jbt.dao;

import java.util.List;

import com.matrix.jbt.entity.User;

public interface UserDao {

	/**
	 * update user information (change password, flag and email)
	 * 
	 * @param user
	 *            com.matrix.jbt.entity.User
	 * @return if success return true else return false
	 */
	public boolean updateUser(User user);

	/**
	 * get a User object by user's id
	 * 
	 * @param userId
	 *            user's id
	 * @return if success return a User object, else return null
	 */
	public User getUserByUserId(String userId);

	/**
	 * get a User object by user's id and password
	 * 
	 * @author JacksonLi
	 * @param userId
	 * @param password
	 * @return
	 */
	public boolean findUser(String userId, String password);

	/**
	 * change the user's password
	 * 
	 * @author JacksonLi
	 * @param userId
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	public boolean changePwd(String userId, String oldPwd, String newPwd);

	/**
	 * judge weather the user is the first time login
	 * 
	 * @author JacksonLi
	 * @param userId
	 * @return
	 */
	public boolean firstLogin(String userId);

	public boolean changeFlg(String userId);

	/**
	 * user forget password
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
}
