package com.matrix.jbt.service.impl;

import java.util.List;

import com.matrix.jbt.dao.AdIntermissionDao;
import com.matrix.jbt.dao.impl.AdIntermissionDaoImpl;
import com.matrix.jbt.entity.AdIntermission;
import com.matrix.jbt.service.AdIntermissionService;

public class AdIntermissionServiceImpl implements AdIntermissionService {

	private AdIntermissionDao adIntermissionDao = new AdIntermissionDaoImpl();

	@Override
	public boolean addAdIntermission(AdIntermission adIntermission) {
		return adIntermissionDao.addAdIntermission(adIntermission);
	}

	@Override
	public boolean updateAdIntermission(AdIntermission adIntermission) {
		return adIntermissionDao.updateAdIntermission(adIntermission);
	}

	@Override
	public boolean deleteAdIntermission(int adIntermissionId) {
		return false;
	}

	@Override
	public AdIntermission getAdIntermission(String image_path) {
		return adIntermissionDao.getAdIntermission(image_path);
	}

	@Override
	public List<AdIntermission> getUsableAdIntermission(int type, int usable) {
		// TODO Auto-generated method stub
		return adIntermissionDao.getUsableAdIntermission(type, usable);
	}

	@Override
	public List<AdIntermission> getAdByType(int type) {
		// TODO Auto-generated method stub
		return adIntermissionDao.getAdByType(type);
	}

	@Override
	public List<AdIntermission> getAllAds() {
		// TODO Auto-generated method stub
		return adIntermissionDao.getAllAds();
	}

	@Override
	public String getShowTime(int type) {
		// TODO Auto-generated method stub
		return adIntermissionDao.getShowTime(type);
	}

}
