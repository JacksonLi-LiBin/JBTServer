package com.matrix.jbt.dao;

import com.matrix.jbt.entity.RefreshToken;

public interface RefreshTokenDao {
	/**
	 * save user's refresh
	 * 
	 * @param refreshToken
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean saveRefreshToken(RefreshToken refreshToken);

	/**
	 * delete user's refresh token according to his userid
	 * 
	 * @param userid
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean deleteRefreshToken(Integer userid);

	/**
	 * update user's refresh token where email equals to the passing value
	 * 
	 * @param refreshToken
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean updateRefreshToken(RefreshToken refreshToken);

	/**
	 * get user's refresh token
	 * 
	 * @param userid
	 * @param cloudtype
	 * @return
	 * @author JacksonLi String
	 */
	public String getRefreshToken(Integer userid, String cloudtype);

	/**
	 * get specified refreshToken according email
	 * 
	 * @param email
	 * @return
	 * @author JacksonLi String
	 */
	public String getSpecifiedRefreshToken(String email);
}
