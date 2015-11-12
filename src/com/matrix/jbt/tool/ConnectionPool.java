package com.matrix.jbt.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * jdbc connection pool ( single instance)
 * 
 */
public class ConnectionPool {

	private static final ConnectionPool cp = new ConnectionPool();
	private static List<Connection> pool = Collections
			.synchronizedList(new ArrayList<Connection>());
	private static final int MAX_COON_NUM = Integer.parseInt(ReadProperties
			.read("jdbc", "maxConNum"));
	private static String driver = ReadProperties.read("jdbc", "driver");
	private static String username = ReadProperties.read("jdbc", "user");

	private static String password = ReadProperties.read("jdbc", "password");
	private static String url = ReadProperties.read("jdbc", "url");

	private ConnectionPool() {

	}

	static {
		try {
			/*
			 * ResourceBundle rb = ResourceBundle.getBundle("jdbc"); driver =
			 * rb.getString("driver"); url = rb.getString("url"); username =
			 * rb.getString("username"); password = rb.getString("password");
			 */
			Class.forName(driver).newInstance();
			for (int i = 0; i < MAX_COON_NUM; i++) {
				pool.add(DriverManager.getConnection(url, username, password));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ConnectionPool getInstance() {
		return cp;
	}

	public synchronized Connection getConnection() {
		while (pool.size() == 0) {
			try {
				wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pool.remove(pool.size() - 1);
	}

	public synchronized void freeConnection(Connection conn) {
		pool.add(conn);
		notify();
	}
}
