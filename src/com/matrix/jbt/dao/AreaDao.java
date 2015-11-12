package com.matrix.jbt.dao;

import java.util.List;

import com.matrix.jbt.entity.Area;

/**
 * 
 * @author JacksonLi
 * @date 2014/5/23
 */
public interface AreaDao {
	/**
	 * get all areas
	 * 
	 * @return
	 * @author JacksonLi List<Area>
	 */
	public List<Area> getAreas();
}
