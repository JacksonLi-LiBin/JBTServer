package com.matrix.jbt.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import com.matrix.jbt.dao.UserDao;
import com.matrix.jbt.entity.User;
import com.matrix.jbt.tool.C3P0DBConnectionPool;
import com.matrix.jbt.tool.ReadProperties;

public class UserDaoImpl implements UserDao {

	private QueryRunner runner = new QueryRunner();

	@Override
	public boolean updateUser(User user) {
		return false;
	}

	@Override
	public User getUserByUserId(String userId) {
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		User user = null;
		try {
			user = runner.query(conn,
					ReadProperties.read("sql", "getUserByUserId"),
					new BeanHandler<>(User.class), userId);
			if (user != null) {
				return user;
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
	public boolean findUser(String userId, String password) {
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		User user = null;
		try {
			user = runner.query(conn, ReadProperties.read("sql", "findUser"),
					new BeanHandler<>(User.class), userId, password);
			if (user != null) {
				return true;
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
		return false;
	}

	@Override
	public boolean changePwd(String userId, String oldPwd, String newPwd) {
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		if (findUser(userId, oldPwd)) {
			try {
				conn.setAutoCommit(false);
				int ret = -1;
				ret = runner
						.update(conn, ReadProperties.read("sql", "changePwd"),
								newPwd, userId);
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
		} else {
			return false;
		}
	}

	@Override
	public boolean firstLogin(String userId) {
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			User user = runner.query(conn,
					ReadProperties.read("sql", "firstLogin"),
					new BeanHandler<>(User.class), userId);
			if (user.getFlag() == 0) {
				return true;
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
		return false;
	}

	@Override
	public boolean changeFlg(String userId) {
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn, ReadProperties.read("sql", "changeFlg"),
					1, userId);
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
	public boolean forgetPassword(String userId, String newPwd) {
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "forgetPassword"), newPwd, 1,
					userId);
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
	public boolean updateStatus(String userId, int status) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "updateUserStatus"), status,
					userId);

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
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			List<User> users = runner.query(conn,
					ReadProperties.read("sql", "getAllUsers"),
					new BeanListHandler<>(User.class));
			if (users != null) {
				return users;
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

	@Override
	public String getUserFlag(String userId) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			List<Object> userFlags = null;
			userFlags = runner.query(conn,
					ReadProperties.read("sql", "getUserByUserId"),
					new ColumnListHandler<>("flag"), userId);
			if (userFlags != null) {
				return userFlags.get(0).toString();
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

	@Override
	public boolean userLogin(User user, String registionId) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			User user2 = null;
			user2 = runner.query(conn, ReadProperties.read("sql", "findUser"),
					new BeanHandler<>(User.class), user.getUserId(),
					user.getPassword());
			int ret0 = -1;
			int ret2 = -1;
			ret2 = runner.update(conn,
					ReadProperties.read("sql", "deleteRegistionIdByUser"),
					user.getUserId());
			ret0 = runner.update(conn,
					ReadProperties.read("sql", "addRegistionId"),
					user.getUserId(), registionId);
			if (user.getFlag() == 0) {
				int ret = -1;
				ret = runner.update(conn,
						ReadProperties.read("sql", "updateUserFlagAndStatus"),
						1, user.getStatus(), user.getUserId());
				if (user2 != null && ret0 >= 0 && ret >= 0 && ret2 >= 0) {
					conn.commit();
					return true;
				}
			} else {
				int ret1 = -1;
				ret1 = runner.update(conn,
						ReadProperties.read("sql", "updateUserStatus"),
						user.getStatus(), user.getUserId());
				if (user2 != null && ret0 >= 0 && ret1 >= 0 && ret2 >= 0) {
					conn.commit();
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return false;
	}

	@Override
	public void initAllUserFlag() {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			int ret = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "initUserFlag"), 0, 1);
			if (ret >= 0) {
				System.out.println("initinal user status successfully");
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
	}

}
