package com.matrix.jbt.servlet;

import java.util.Date;
import java.util.UUID;

import com.matrix.jbt.service.AdminService;
import com.matrix.jbt.service.TimeOutService;
import com.matrix.jbt.service.impl.AdminServiceImpl;
import com.matrix.jbt.service.impl.TimeOutServiceImpl;
import com.matrix.jbt.tool.AdminPool;
import com.matrix.jbt.tool.UserPool;

/**
 * administrator action
 * 
 * @author JacksonLi
 * @date 2014/3/13
 */
public class AdminAction {
	private AdminService adminManager = new AdminServiceImpl();
	private TimeOutService timeOutManager = new TimeOutServiceImpl();
	private Thread thread = null;
	private boolean flag = false;
	private Date operateT;

	/**
	 * create a token when administrator login
	 * 
	 * @return
	 */
	public String createToken() {
		return UUID.randomUUID().toString();
	}

	/**
	 * administrator login
	 * 
	 * @param adminName
	 * @param password
	 * @return
	 */
	public String login(String adminName, String password) {
		String token = "";
		if (adminName != "" && password != "") {
			if (adminManager.adminLogin(adminName, password).equals("true")) {
				token = createToken();
			}
		}
		return token;
	}

	/**
	 * time keeping administrator login
	 * 
	 * @param operationTime
	 * @param adminName
	 * @param token
	 */
	public void handleLogoutAbnormal(Date operationTime, String adminName,
			String token) {
		if (UserPool.timeOut.equals("0")) {
			UserPool.timeOut = timeOutManager.getTimeOut();
		}
		flag = true;
		operateT = operationTime;
		thread = new Thread(new MyRunnable(adminName, token));
		thread.start();
	}

	public void stopThread() {
		flag = false;
		thread = null;
		System.gc();
	}

	public void reStartThread(Date time) {
		operateT = time;
		// System.out.println("restart");
	}

	class MyRunnable implements Runnable {
		private String name;
		private String tok;

		public MyRunnable(String name, String tok) {
			super();
			this.name = name;
			this.tok = tok;
		}

		@Override
		public void run() {
			while (flag) {
				Long waitTime = new Date().getTime() - operateT.getTime();
				// System.out.println(name + "-------yijing:" + waitTime
				// + "-------" + thread.currentThread().getId());
				if (waitTime > Integer.parseInt(UserPool.timeOut)) {
					System.out.println("admin operate time out");
					AdminPool.adminMap.remove(name);
					AdminPool.adminActions.remove(tok);
					stopThread();
					break;
				}
			}
		}
	}

	/**
	 * get time out
	 * 
	 * @return
	 * @author JacksonLi String
	 */
	public String getTimeOut() {
		return timeOutManager.getTimeOut();
	}

	/**
	 * get change password time out
	 * 
	 * @return
	 * @author JacksonLi String
	 */
	public String getChangePasswordTimeOut() {
		return timeOutManager.getCPWDTimeOut();
	}

	/**
	 * get first time get data from john bryce
	 * 
	 * @return
	 * @author JacksonLi String
	 */
	public String getFirstTimeGetData() {
		return timeOutManager.getFirstGetDataTime();
	}

	/**
	 * get second time get data from john bryce
	 * 
	 * @return
	 * @author JacksonLi String
	 */
	public String getSecondTimeGetData() {
		return timeOutManager.getSecondGetDataTime();
	}
}
