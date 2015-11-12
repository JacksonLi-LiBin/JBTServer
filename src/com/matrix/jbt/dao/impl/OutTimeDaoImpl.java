package com.matrix.jbt.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import com.matrix.jbt.dao.OutTimeDao;
import com.matrix.jbt.tool.C3P0DBConnectionPool;
import com.matrix.jbt.tool.ReadProperties;

public class OutTimeDaoImpl implements OutTimeDao {
	private QueryRunner runner = new QueryRunner();

	@Override
	public boolean updateOutTime(String time) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "updateTimeOut"), time,
					"timeout");
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
	public boolean updateCPWDOutTime(String time) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "updateCPWDTimeOut"), time,
					"cpwdtimeout");
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
	public String getTimeOut() {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			List<Object> list = runner.query(conn,
					ReadProperties.read("sql", "getTimeOut"),
					new ColumnListHandler<>("commonvalue"), "timeout");
			if (list != null && list.size() > 0) {
				return list.get(0) + "";
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
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
	public String getCPWDTimeOut() {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			List<Object> list = runner.query(conn,
					ReadProperties.read("sql", "getCPWDTimeOut"),
					new ColumnListHandler<>("commonvalue"), "cpwdtimeout");
			if (list != null && list.size() > 0) {
				return list.get(0) + "";
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
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
	public String getFirstGetDataTime() {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			List<Object> list = runner.query(conn,
					ReadProperties.read("sql", "getFirstGetDataFromJB"),
					new ColumnListHandler<>("commonvalue"), "firstgetdatatime");
			if (list != null && list.size() > 0) {
				return list.get(0) + "";
			}
		} catch (Exception e) {
			// TODO: handle exception
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
	public String getSecondGetDataTime() {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			List<Object> list = runner
					.query(conn, ReadProperties.read("sql",
							"getSecondGetDataFromJB"), new ColumnListHandler<>(
							"commonvalue"), "secondgetdatatime");
			if (list != null && list.size() > 0) {
				return list.get(0) + "";
			}
		} catch (Exception e) {
			// TODO: handle exception
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
	public boolean updateFirstGetDataTime(String fTime) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "updateFirstGetTime"), fTime,
					"firstgetdatatime");
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
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
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
	}

	@Override
	public boolean updateSecondGetDataTime(String sTime) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "updateSecondGetTime"), sTime,
					"secondgetdatatime");
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
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
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
	}
}
