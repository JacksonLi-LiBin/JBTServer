package com.matrix.jbt.service;

import java.util.List;

import com.matrix.jbt.entity.AdIntermission;

public interface AdIntermissionService {
	/**
	 * add a new AdIntermission
	 * 
	 * @param adIntermission
	 *            com.matrix.jbt.entity.AdIntermission
	 * @return if success return true else return false
	 */
	public boolean addAdIntermission(AdIntermission adIntermission);

	/**
	 * update intermission information
	 * 
	 * @param adIntermission
	 *            com.matrix.jbt.entity.AdIntermission
	 * @return if success return true else return false
	 */
	public boolean updateAdIntermission(AdIntermission adIntermission);

	/**
	 * delete a AdIntermission
	 * 
	 * @param adIntermissionId
	 * @return if success return true else return flase
	 */
	public boolean deleteAdIntermission(int adIntermissionId);

	/**
	 * get an AdIntermission object by id
	 * 
	 * @param adIntermissionId
	 *            adIntermission's id
	 * @return if success return an AdIntermission object, else return null
	 */
	public AdIntermission getAdIntermission(String image_path);

	/**
	 * get the usable image by type
	 * 
	 * @author JacksonLi
	 * @param type
	 * @param usable
	 * @return
	 */
	public List<AdIntermission> getUsableAdIntermission(int type, int usable);

	/**
	 * get all ads by ad's type
	 * 
	 * @author JacksonLi
	 * @param type
	 * @return
	 */
	public List<AdIntermission> getAdByType(int type);

	/**
	 * get all ads
	 * 
	 * @author JacksonLi
	 * @return
	 */
	public List<AdIntermission> getAllAds();

	/**
	 * get showtime by type
	 * 
	 * @author JacksonLi
	 * @param type
	 * @return
	 */
	public String getShowTime(int type);
}
