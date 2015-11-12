package com.matrix.jbt.tool;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0DBConnectionPool {
	private static ComboPooledDataSource cpds = null;

	/**
	 * initialize connection pool
	 * 
	 * @author JacksonLi void
	 */
	public static void init() {
		// create database connection pool
		String DRIVER_NAME = ReadProperties.read("jdbc", "driver"); // Driver
		String DATABASE_URL = ReadProperties.read("jdbc", "url"); // url
		String DATABASE_USER = ReadProperties.read("jdbc", "user"); // user name
		String DATABASE_PASSWORD = ReadProperties.read("jdbc", "password");// password
		int Min_PoolSize = 5;
		int Max_PoolSize = 50;
		int Acquire_Increment = 5;
		int Initial_PoolSize = 10;
		// test weather the connection is usable every 3000s
		int Idle_Test_Period = 3000;
		// check weather connection is usable when connect
		String Validate = ReadProperties.read("jdbc", "c3p0.validate");
		if (Validate.equals("")) {
			Validate = "false";
		}
		// minimum connection counts
		try {
			Min_PoolSize = Integer.parseInt(ReadProperties.read("jdbc",
					"c3p0.minPoolSize"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// increment counts
		try {
			Acquire_Increment = Integer.parseInt(ReadProperties.read("jdbc",
					"c3p0.acquireIncrement"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// maximum connection counts
		try {
			Max_PoolSize = Integer.parseInt(ReadProperties.read("jdbc",
					"c3p0.maxPoolSize"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// initialize connection counts
		try {
			Initial_PoolSize = Integer.parseInt(ReadProperties.read("jdbc",
					"c3p0.initialPoolSize"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// test weather the connection is usable every Idle_Test_Period s
		try {
			Idle_Test_Period = Integer.parseInt(ReadProperties.read("jdbc",
					"c3p0.idleConnectionTestPeriod"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			cpds = new ComboPooledDataSource();
			cpds.setDriverClass(DRIVER_NAME); // driver
			cpds.setJdbcUrl(DATABASE_URL); // url
			cpds.setUser(DATABASE_USER); // user
			cpds.setPassword(DATABASE_PASSWORD); // password
			cpds.setInitialPoolSize(Initial_PoolSize); // initialize pool size
			cpds.setMinPoolSize(Min_PoolSize); // minimum connection counts
			cpds.setMaxPoolSize(Max_PoolSize); // maximum connection counts
			cpds.setAcquireIncrement(Acquire_Increment); // increment counts
			cpds.setIdleConnectionTestPeriod(Idle_Test_Period); // check effective time interval
			cpds.setTestConnectionOnCheckout(Boolean.getBoolean(Validate)); // check weather connection is usable when connect
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * get connection
	 * @return
	 * @author JacksonLi
	 * Connection
	 */
	public static Connection getConnection() {
		Connection connection = null;
		try {// initialize only once
			if (cpds == null) {
				init();
			}
			
			// getconnection
			connection = cpds.getConnection();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return connection;
	}

	/**
	 * release connection pool
	 * 
	 * @author JacksonLi
	 * void
	 */
	public static void release() {
		try {
			if (cpds != null) {
				cpds.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
