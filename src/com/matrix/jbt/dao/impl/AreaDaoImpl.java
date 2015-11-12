package com.matrix.jbt.dao.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.matrix.jbt.dao.AreaDao;
import com.matrix.jbt.entity.Area;
import com.matrix.jbt.tool.C3P0DBConnectionPool;
import com.matrix.jbt.tool.ReadProperties;

public class AreaDaoImpl implements AreaDao {
	private QueryRunner runner = new QueryRunner();

	@Override
	public List<Area> getAreas() {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			List<Area> areaList = null;
			areaList = runner.query(conn,
					ReadProperties.read("sql", "getAreas"),
					new BeanListHandler<>(Area.class));
			if (areaList != null) {
				return areaList;
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
