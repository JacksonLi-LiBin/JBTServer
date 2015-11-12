package com.matrix.jbt.dao;

import java.util.List;

import com.matrix.jbt.entity.Domain;

/**
 * 
 * @author JacksonLi
 * @date 2014/5/23
 */
public interface DomainDao {
	/**
	 * get all domains
	 * 
	 * @return
	 * @author JacksonLi List<Domain>
	 */
	public List<Domain> getDomains();
}
