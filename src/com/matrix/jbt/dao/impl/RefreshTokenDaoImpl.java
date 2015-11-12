package com.matrix.jbt.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import com.matrix.jbt.dao.RefreshTokenDao;
import com.matrix.jbt.entity.RefreshToken;
import com.matrix.jbt.tool.C3P0DBConnectionPool;
import com.matrix.jbt.tool.ReadProperties;

public class RefreshTokenDaoImpl implements RefreshTokenDao {
	private QueryRunner runner = new QueryRunner();

	@Override
	public boolean saveRefreshToken(RefreshToken refreshToken) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "saveRefreshToken"),
					refreshToken.getUserid(), refreshToken.getCloudtype(),
					refreshToken.getEmail(), refreshToken.getRefreshtoken());
			if (ret >= 0) {
				conn.commit();
				return true;
			} else {
				conn.rollback();
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
				return false;
			} catch (Exception e2) {
				// TODO: handle exception
			}
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
	public boolean deleteRefreshToken(Integer userid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRefreshToken(RefreshToken refreshToken) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "updateRefreshToken"),
					refreshToken.getRefreshtoken(), refreshToken.getEmail());
			if (ret >= 0) {
				conn.commit();
				return true;
			} else {
				conn.rollback();
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
				return false;
			} catch (Exception e2) {
				// TODO: handle exception
			}
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
	public String getRefreshToken(Integer userid, String cloudtype) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			List<Object> list = runner.query(conn,
					ReadProperties.read("sql", "getRefreshToken"),
					new ColumnListHandler<>("refreshtoken"), userid, cloudtype);
			if (list != null && list.size() > 0) {
				return list.get(0) + "";
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				return "";
			} catch (Exception e2) {
				// TODO: handle exception
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
		return "";
	}

	@Override
	public String getSpecifiedRefreshToken(String email) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			List<Object> list = runner.query(conn,
					ReadProperties.read("sql", "getSpecifiedRefreshToken"),
					new ColumnListHandler<>("refreshtoken"), email);
			if (list != null && list.size() > 0) {
				return list.get(0) + "";
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				return "";
			} catch (Exception e2) {
				// TODO: handle exception
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
		return "";
	}

}
