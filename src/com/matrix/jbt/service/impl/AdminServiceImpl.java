package com.matrix.jbt.service.impl;

import com.matrix.jbt.dao.AdminDao;
import com.matrix.jbt.dao.impl.AdminDaoImpl;
import com.matrix.jbt.service.AdminService;

public class AdminServiceImpl implements AdminService {
	private AdminDao adminDao = new AdminDaoImpl();

	@Override
	public String adminLogin(String userName, String password) {
		// TODO Auto-generated method stub
		return adminDao.adminLogin(userName, password);
	}

}
