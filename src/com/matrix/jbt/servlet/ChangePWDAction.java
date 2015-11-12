package com.matrix.jbt.servlet;

import java.util.Date;
import java.util.UUID;

import com.matrix.jbt.service.TimeOutService;
import com.matrix.jbt.service.impl.TimeOutServiceImpl;
import com.matrix.jbt.tool.UserPool;

public class ChangePWDAction {
	private TimeOutService timeOutManager = new TimeOutServiceImpl();
	private Thread thread = null;
	private boolean flag = false;
	private Date operateT;

	public String createToken() {
		return UUID.randomUUID().toString();
	}

	/**
	 * time keeping when user click forgot button
	 * 
	 * @param operationTime
	 * @param userId
	 */
	public void handleLogoutAbnormal(Date operationTime, String userId) {
		if (UserPool.CPWDTimeOut.equals("0")) {
			UserPool.CPWDTimeOut = timeOutManager.getCPWDTimeOut();
		}
		flag = true;
		operateT = operationTime;
		thread = new Thread(new MyRunnable(userId));
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
		private String userId;

		public MyRunnable(String userId) {
			super();
			this.userId = userId;
		}

		@Override
		public void run() {
			while (flag) {
				Long waitTime = new Date().getTime() - operateT.getTime();
				// System.out.println(userId + "-------yijing:" + waitTime
				// + "-------" + thread.currentThread().getId());
				if (waitTime > Integer.parseInt(UserPool.CPWDTimeOut)) {
					System.out.println(userId + " change password time out");
					UserPool.userPwdActions.remove(userId);
					UserPool.cpwdToken.remove(userId);
					stopThread();
					break;
				}
			}
		}
	}
}
