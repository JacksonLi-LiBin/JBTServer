package com.matrix.jbt.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.matrix.jbt.dao.EmailDao;
import com.matrix.jbt.entity.CourseJB;
import com.matrix.jbt.entity.Email;
import com.matrix.jbt.entity.User;
import com.matrix.jbt.tool.C3P0DBConnectionPool;
import com.matrix.jbt.tool.ReadProperties;

public class EmailDaoImpl implements EmailDao {
	private QueryRunner runner = new QueryRunner();

	@Override
	public boolean addEmail(Email email) {
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn, ReadProperties.read("sql", "addEmail"),
					email.getEmail());
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
	public boolean updateEmail(Email email) {
		return false;
	}

	@Override
	public boolean deleteEmail(int emailId) {
		return false;
	}

	@Override
	public Email getEmailByEmailId(int emailId) {
		return null;
	}

	@Override
	public List<Email> getAll() {
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		List<Email> list = null;
		try {
			list = runner.query(conn,
					ReadProperties.read("sql", "getAllEmail"),
					new BeanListHandler<Email>(Email.class));
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
	public boolean deleteEmail(String emailname) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "deleteEmailByName"), emailname);
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
	public boolean updateEmail(String oldEmail, String newEmail) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "updateEmail"), newEmail,
					oldEmail);
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

	@SuppressWarnings("deprecation")
	@Override
	public List<User> getAllUserEmail() {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		List<User> list = null;
		try {
			list = runner.query(conn, ReadProperties.read("sql", "getAllUser"),
					1, new BeanListHandler<User>(User.class));
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
	public boolean updateUserEmail(Integer userId, String email) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "updateUserEmail"), email,
					userId);
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
	public List<Email> getEmailsByPageNumber(Integer beginRow,
			Integer pageCounts) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		List<Email> list = null;
		try {
			list = runner.query(conn,
					ReadProperties.read("sql", "getEmailByPage"),
					new BeanListHandler<Email>(Email.class), beginRow,
					pageCounts);
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
	public List<Email> getEmailsByPageNumberWithFilter(Integer beginRow,
			Integer pageCounts, String filterStr) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		List<Email> list = null;
		try {
			list = runner.query(conn,
					ReadProperties.read("sql", "getEmailByPageAndFilter"),
					new BeanListHandler<Email>(Email.class), "%" + filterStr
							+ "%", beginRow, pageCounts);
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
	public Map<User, List<CourseJB>> getUserEmailByPage(Integer beginRow,
			Integer pageCounts) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			List<User> list = null;
			list = runner.query(conn,
					ReadProperties.read("sql", "getUserEmailByPage"),
					new BeanListHandler<>(User.class), 1, beginRow, pageCounts);
			if (list != null) {
				Map<User, List<CourseJB>> map = new HashMap<>();
				List<CourseJB> courseJBs = null;
				for (User user : list) {
					courseJBs = runner.query(conn,
							ReadProperties.read("sql", "getTwoMaslulByUser"),
							new BeanListHandler<>(CourseJB.class),
							user.getUserId(), 0, 2);

					map.put(user, courseJBs);
				}
				return map;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Map<User, List<CourseJB>> getUserEmailByPageWithFilter(
			Integer beginRow, Integer pageCounts, String filter) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			List<User> list = null;
			list = runner.query(conn,
					ReadProperties.read("sql", "getUserEmailByPageWithFilter"),
					new BeanListHandler<>(User.class), 1, "%" + filter + "%",
					beginRow, pageCounts);
			if (list != null) {
				Map<User, List<CourseJB>> map = new HashMap<>();
				List<CourseJB> courseJBs = null;
				for (User user : list) {
					courseJBs = runner.query(conn,
							ReadProperties.read("sql", "getTwoMaslulByUser"),
							new BeanListHandler<>(CourseJB.class),
							user.getUserId(), 0, 2);

					map.put(user, courseJBs);
				}
				return map;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Integer getUserEmailCount() {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		Integer counts = null;
		try {
			counts = runner.query(conn,
					ReadProperties.read("sql", "getUserEmailCount"),
					new ResultSetHandler<Integer>() {

						@Override
						public Integer handle(ResultSet arg0)
								throws SQLException {
							// TODO Auto-generated method stub
							if (arg0.next()) {
								return arg0.getInt(1);
							}
							return null;
						}
					}, 1);
			if (counts != null) {
				return counts;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Integer getUserEmailCountWithFilter(String filter) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		Integer counts = null;
		try {
			counts = runner.query(conn,
					ReadProperties.read("sql", "getUserEmailCountWithFilter"),
					new ResultSetHandler<Integer>() {

						@Override
						public Integer handle(ResultSet arg0)
								throws SQLException {
							// TODO Auto-generated method stub
							if (arg0.next()) {
								return arg0.getInt(1);
							}
							return null;
						}
					}, "%" + filter + "%", 1);
			if (counts != null) {
				return counts;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Integer getAllEmailCount() {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		Integer counts = null;
		try {
			counts = runner.query(conn,
					ReadProperties.read("sql", "getEmailCount"),
					new ResultSetHandler<Integer>() {

						@Override
						public Integer handle(ResultSet arg0)
								throws SQLException {
							// TODO Auto-generated method stub
							if (arg0.next()) {
								return arg0.getInt(1);
							}
							return null;
						}
					});
			if (counts != null) {
				return counts;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Integer getEmailCountWithFilter(String filter) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		Integer counts = null;
		try {
			counts = runner.query(conn,
					ReadProperties.read("sql", "getEmailCountWithFilter"),
					new ResultSetHandler<Integer>() {

						@Override
						public Integer handle(ResultSet arg0)
								throws SQLException {
							// TODO Auto-generated method stub
							if (arg0.next()) {
								return arg0.getInt(1);
							}
							return null;
						}
					}, "%" + filter + "%");
			if (counts != null) {
				return counts;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return null;
	}
}
