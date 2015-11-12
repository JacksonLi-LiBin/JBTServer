package com.matrix.jbt.service;

import com.matrix.jbt.entity.RefreshToken;

public interface RefreshTokenService {

	/**
	 * delete user's refresh token according to his userid
	 * 
	 * @param userid
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean deleteRefreshToken(Integer userid);

	/**
	 * save or update user's refresh token if user's refresh token in database
	 * is unusable then update it if user's refresh token does not exist in
	 * database then save it(existing email>>1.get the refresh token and save
	 * your own record;new email>>1.save it)
	 * 
	 * @param refreshToken
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean saveOrUpdateRefreshToken(RefreshToken refreshToken);

	/**
	 * get user's refresh token
	 * 
	 * @param userid
	 * @param cloudtype
	 * @return
	 * @author JacksonLi String
	 */
	public String getRefreshToken(Integer userid, String cloudtype);
}
