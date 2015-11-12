package com.matrix.jbt.service.impl;

import java.util.List;

import com.matrix.jbt.dao.impl.UserDaoImpl;
import com.matrix.jbt.entity.User;
import com.matrix.jbt.service.UserService;

public class UserServiceImpl implements UserService {

	private UserDaoImpl daoImpl = new UserDaoImpl();

	@Override
	public boolean changePassword(String userId, String oldPwd, String newPwd) {
		if (daoImpl.changePwd(userId, oldPwd, newPwd)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean changeFlag(String userId) {
		if (daoImpl.changeFlg(userId)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isFirstLogin(String userId) {
		if (daoImpl.firstLogin(userId)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean changeEmail(String userId, String email) {
		return false;
	}

	@Override
	public User getUserInDatabase(String userId) {
		// TODO Auto-generated method stub
		User user = daoImpl.getUserByUserId(userId);
		if (user != null) {
			return user;
		}
		return null;
	}

	@Override
	public boolean forgetPassword(String userId, String newPwd) {
		// TODO Auto-generated method stub
		return daoImpl.forgetPassword(userId, newPwd);
	}

	@Override
	public boolean updateStatus(String userId, int status) {
		// TODO Auto-generated method stub
		return daoImpl.updateStatus(userId, status);
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return daoImpl.getAllUsers();
	}

	@Override
	public String getUserFlag(String userId) {
		// TODO Auto-generated method stub
		return daoImpl.getUserFlag(userId);
	}

	@Override
	public boolean userLogin(User user, String registionId) {
		// TODO Auto-generated method stub
		return daoImpl.userLogin(user, registionId);
	}

	@Override
	public void initAllUserFlag() {
		// TODO Auto-generated method stub
		daoImpl.initAllUserFlag();
	}

	@Override
	public boolean findUser(String userID, String pwd) {
		return daoImpl.findUser(userID, pwd);
	}

}
