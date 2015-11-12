package com.matrix.jbt.service.impl;

import com.matrix.jbt.dao.RefreshTokenDao;
import com.matrix.jbt.dao.impl.RefreshTokenDaoImpl;
import com.matrix.jbt.entity.RefreshToken;
import com.matrix.jbt.service.RefreshTokenService;

public class RefreshTokenServiceImpl implements RefreshTokenService {
	private RefreshTokenDao refreshTokenDao = new RefreshTokenDaoImpl();

	@Override
	public boolean deleteRefreshToken(Integer userid) {
		// TODO Auto-generated method stub
		return refreshTokenDao.deleteRefreshToken(userid);
	}

	@Override
	public boolean saveOrUpdateRefreshToken(RefreshToken refreshToken) {
		// TODO Auto-generated method stub
		String passRToken=refreshToken.getRefreshtoken();
		if(passRToken.length()>0){
			//unusable refresh token or a new email
			String getRefreshToken = refreshTokenDao.getRefreshToken(refreshToken.getUserid(), refreshToken.getCloudtype());
			if (getRefreshToken.length() > 0) {
				//existing refresh token
				if (getRefreshToken.equals(refreshToken.getRefreshtoken())) {
					return true;
				} else {
					return refreshTokenDao.updateRefreshToken(refreshToken);
				}
			}else{
				return refreshTokenDao.saveRefreshToken(refreshToken);
			}
		}else{
			//an authorized email
			String token=refreshTokenDao.getSpecifiedRefreshToken(refreshToken.getEmail());
			if(token.length()>0){
				refreshToken.setRefreshtoken(token);
				return refreshTokenDao.saveRefreshToken(refreshToken);
			}else{
				return false;
			}
		}
	}

	@Override
	public String getRefreshToken(Integer userid, String cloudtype) {
		// TODO Auto-generated method stub
		return refreshTokenDao.getRefreshToken(userid, cloudtype);
	}

}
