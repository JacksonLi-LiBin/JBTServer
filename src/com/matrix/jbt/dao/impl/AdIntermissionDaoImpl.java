package com.matrix.jbt.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import com.matrix.jbt.dao.AdIntermissionDao;
import com.matrix.jbt.entity.AdIntermission;
import com.matrix.jbt.tool.C3P0DBConnectionPool;
import com.matrix.jbt.tool.ReadProperties;

public class AdIntermissionDaoImpl implements AdIntermissionDao {
	private QueryRunner runner = new QueryRunner();

	@Override
	public boolean addAdIntermission(AdIntermission adIntermission) {
		// System.out.println("addAdIntermission-----" + adIntermission);
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "addAdIntermission"),
					adIntermission.getPath(), adIntermission.getShowTime(),
					adIntermission.getType(), adIntermission.getUsable());
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
	public boolean updateAdIntermission(AdIntermission adIntermission) {
		// System.out.println("updateAdIntermission-----" + adIntermission);
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "updateAdIntermission"),
					adIntermission.getPath(), adIntermission.getShowTime(),
					adIntermission.getType(), adIntermission.getUsable(),
					adIntermission.getAdId());
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
	public boolean deleteAdIntermission(int adIntermissionId) {
		return false;
	}

	@Override
	public AdIntermission getAdIntermission(String image_path) {
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		AdIntermission ad = null;
		try {
			ad = runner.query(conn,
					ReadProperties.read("sql", "getAdIntermission"),
					new BeanHandler<>(AdIntermission.class), image_path);
			if (ad != null) {
				return ad;
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
	public List<AdIntermission> getUsableAdIntermission(int type, int usable) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		List<AdIntermission> list = null;
		try {
			list = runner.query(conn,
					ReadProperties.read("sql", "getUsableAdIntermission"),
					new BeanListHandler<AdIntermission>(AdIntermission.class),
					type, usable);
			if (list != null) {
				return list;
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

	@Override
	public List<AdIntermission> getAdByType(int type) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		List<AdIntermission> list = null;
		try {
			list = runner.query(conn,
					ReadProperties.read("sql", "getAdByType"),
					new BeanListHandler<AdIntermission>(AdIntermission.class),
					type);
			if (list != null) {
				return list;
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
	public List<AdIntermission> getAllAds() {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		List<AdIntermission> list = null;
		try {
			list = runner.query(conn, ReadProperties.read("sql", "getAllAds"),
					new BeanListHandler<AdIntermission>(AdIntermission.class));
			if (list != null) {
				return list;
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
	public String getShowTime(int type) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			List<Object> columnResult = runner.query(conn,
					ReadProperties.read("sql", "getShowTime"),
					new ColumnListHandler<>("showtime"), type);
			if (columnResult != null && columnResult.size() > 0) {
				return "" + columnResult.get(0);
			} else {
				return "0";
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

}
