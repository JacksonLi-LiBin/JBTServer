package com.matrix.jbt.service.impl;

import java.util.List;

import com.matrix.jbt.dao.AreaDao;
import com.matrix.jbt.dao.impl.AreaDaoImpl;
import com.matrix.jbt.entity.Area;
import com.matrix.jbt.service.AreaService;

public class AreaServiceImpl implements AreaService {
	private AreaDao areaDao = new AreaDaoImpl();

	@Override
	public List<Area> getAreas() {
		// TODO Auto-generated method stub
		return areaDao.getAreas();
	}

}
