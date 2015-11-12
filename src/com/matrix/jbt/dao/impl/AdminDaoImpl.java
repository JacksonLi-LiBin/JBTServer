package com.matrix.jbt.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.matrix.jbt.dao.AdminDao;
import com.matrix.jbt.entity.Administrator;
import com.matrix.jbt.tool.C3P0DBConnectionPool;
import com.matrix.jbt.tool.ReadProperties;

public class AdminDaoImpl implements AdminDao {
	private QueryRunner runner = new QueryRunner();

	@Override
	public String adminLogin(String userName, String password) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		Administrator administrator = null;
		try {
			administrator = runner.query(conn, ReadProperties.read("sql",
					"getAdmin"), new BeanHandler<>(Administrator.class),
					userName, password);
			if (administrator != null) {
				return "true";
			} else {
				return "false";
			}
		} catch (Exception e) {
			// TODO: handle exception
			return "false";
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
