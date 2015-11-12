package com.matrix.jbt.dao.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.matrix.jbt.dao.DomainDao;
import com.matrix.jbt.entity.Domain;
import com.matrix.jbt.tool.C3P0DBConnectionPool;
import com.matrix.jbt.tool.ReadProperties;

public class DomainDaoImpl implements DomainDao {
	private QueryRunner runner = new QueryRunner();

	@Override
	public List<Domain> getDomains() {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			List<Domain> domainList = null;
			domainList = runner.query(conn,
					ReadProperties.read("sql", "getDomains"),
					new BeanListHandler<>(Domain.class));
			if (domainList != null) {
				return domainList;
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return null;
	}

}
