package com.matrix.jbt.service.impl;

import java.util.Map;

import com.matrix.jbt.dao.RegistionIdDao;
import com.matrix.jbt.dao.impl.RegistionIdDaoImpl;
import com.matrix.jbt.service.RegistionIdService;

public class RegistionIdServiceImpl implements RegistionIdService {
	private RegistionIdDao registionIdDao = new RegistionIdDaoImpl();

	@Override
	public boolean addRegistionId(int userid, String registionId) {
		// TODO Auto-generated method stub
		return registionIdDao.addRegistionId(userid, registionId);
	}

	@Override
	public boolean deleteRegistionId(int userid) {
		// TODO Auto-generated method stub
		return registionIdDao.deleteRegistionId(userid);
	}

	@Override
	public boolean updateRegistionId(int userid, String registionId) {
		// TODO Auto-generated method stub
		return registionIdDao.updateRegistionId(userid, registionId);
	}

	@Override
	public String getRegistionId(int userid) {
		// TODO Auto-generated method stub
		return registionIdDao.getRegistionId(userid);
	}

	@Override
	public Map<Integer, String> getAllRegistionId() {
		// TODO Auto-generated method stub
		return registionIdDao.getAllRegistionId();
	}

}
