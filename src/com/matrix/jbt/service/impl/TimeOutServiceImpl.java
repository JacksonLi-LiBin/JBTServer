package com.matrix.jbt.service.impl;

import com.matrix.jbt.dao.OutTimeDao;
import com.matrix.jbt.dao.impl.OutTimeDaoImpl;
import com.matrix.jbt.service.TimeOutService;

public class TimeOutServiceImpl implements TimeOutService {
	private OutTimeDao outTimeDao = new OutTimeDaoImpl();

	@Override
	public boolean updateOutTime(String time) {
		// TODO Auto-generated method stub
		return outTimeDao.updateOutTime(time);
	}

	@Override
	public String getTimeOut() {
		// TODO Auto-generated method stub
		return outTimeDao.getTimeOut();
	}

	@Override
	public String getCPWDTimeOut() {
		// TODO Auto-generated method stub
		return outTimeDao.getCPWDTimeOut();
	}

	@Override
	public boolean updateCPWDOutTime(String time) {
		// TODO Auto-generated method stub
		return outTimeDao.updateCPWDOutTime(time);
	}

	@Override
	public String getFirstGetDataTime() {
		// TODO Auto-generated method stub
		return outTimeDao.getFirstGetDataTime();
	}

	@Override
	public String getSecondGetDataTime() {
		// TODO Auto-generated method stub
		return outTimeDao.getSecondGetDataTime();
	}

	@Override
	public boolean updateFirstGetDataTime(String fTime) {
		// TODO Auto-generated method stub
		return outTimeDao.updateFirstGetDataTime(fTime);
	}

	@Override
	public boolean updateSecondGetDataTime(String sTime) {
		// TODO Auto-generated method stub
		return outTimeDao.updateSecondGetDataTime(sTime);
	}

}
