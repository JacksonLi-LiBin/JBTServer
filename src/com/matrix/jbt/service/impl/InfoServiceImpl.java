package com.matrix.jbt.service.impl;

import com.matrix.jbt.dao.InfoDao;
import com.matrix.jbt.dao.impl.InfoDaoImpl;
import com.matrix.jbt.service.InfoService;

public class InfoServiceImpl implements InfoService {
	private InfoDao infoDao = new InfoDaoImpl();

	@Override
	public String getInfos(String userId) {
		// TODO Auto-generated method stub
		return infoDao.getInfos(userId);
	}

}
