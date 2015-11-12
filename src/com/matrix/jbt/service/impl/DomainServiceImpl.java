package com.matrix.jbt.service.impl;

import java.util.List;

import com.matrix.jbt.dao.DomainDao;
import com.matrix.jbt.dao.impl.DomainDaoImpl;
import com.matrix.jbt.entity.Domain;
import com.matrix.jbt.service.DomainService;

public class DomainServiceImpl implements DomainService {
	private DomainDao domainDao = new DomainDaoImpl();

	@Override
	public List<Domain> getDomains() {
		// TODO Auto-generated method stub
		return domainDao.getDomains();
	}

}
