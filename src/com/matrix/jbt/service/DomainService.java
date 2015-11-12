package com.matrix.jbt.service;

import java.util.List;

import com.matrix.jbt.entity.Domain;

/**
 * 
 * @author JacksonLi
 * @date 2014/5/23
 */
public interface DomainService {
	/**
	 * get all domains
	 * 
	 * @return
	 * @author JacksonLi List<Domain>
	 */
	public List<Domain> getDomains();
}
