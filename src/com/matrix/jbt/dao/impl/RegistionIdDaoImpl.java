package com.matrix.jbt.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.matrix.jbt.dao.RegistionIdDao;
import com.matrix.jbt.entity.RegistionId;
import com.matrix.jbt.tool.C3P0DBConnectionPool;
import com.matrix.jbt.tool.ReadProperties;

public class RegistionIdDaoImpl implements RegistionIdDao {
	private QueryRunner runner = new QueryRunner();

	@Override
	public boolean addRegistionId(int userid, String registionId) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "addRegistionId"), userid,
					registionId);
			if (ret >= 0) {
				conn.commit();
				return true;
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean deleteRegistionId(int userid) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "deleteRegistionIdByUser"),
					userid);
			if (ret >= 0) {
				conn.commit();
				return true;
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean updateRegistionId(int userid, String registionId) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "updateRegistionId"),
					registionId, userid);
			if (ret >= 0) {
				conn.commit();
				return true;
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public String getRegistionId(int userid) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		RegistionId registionId = null;
		try {
			registionId = runner.query(conn,
					ReadProperties.read("sql", "getRegistionIdByUser"),
					new BeanHandler<>(RegistionId.class), userid);
			if (registionId != null) {
				return registionId.getRegistion_id();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Map<Integer, String> getAllRegistionId() {
		// TODO Auto-generated method stub
		Map<Integer, String> map = new HashMap<>();
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		List<RegistionId> list = null;
		try {
			list = runner.query(conn,
					ReadProperties.read("sql", "getAllRegistionIds"),
					new BeanListHandler<RegistionId>(RegistionId.class));
			if (list != null) {
				for (RegistionId registionId : list) {
					map.put(registionId.getUserid(),
							registionId.getRegistion_id());
				}
				return map;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
