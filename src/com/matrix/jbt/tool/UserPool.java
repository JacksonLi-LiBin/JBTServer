package com.matrix.jbt.tool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.matrix.jbt.entity.User;
import com.matrix.jbt.servlet.ChangePWDAction;
import com.matrix.jbt.servlet.UserAction;

public class UserPool {
	//save login user
	public static Map<String, UserAction> userActions = new HashMap<>();
	public static Map<String, User> users = new HashMap<>();
	public static Map<String, String> tokens=new HashMap<>();
	//save change password user
	public static Map<String, ChangePWDAction> userPwdActions = new HashMap<>();
	public static Map<String, String> cpwdToken = new HashMap<>();
	//set timeout
	public static String timeOut = "0";
	public static String CPWDTimeOut = "0";
	//save how long the user could login if he logout abnormal
	public static Map<String, String> reLoginLeftTime=new HashMap<>();

	// check weather the user has logined
	public static boolean checkUserHasLogined(User user) {
		// TODO Auto-generated method stub
		Set<Map.Entry<String, User>> set = users.entrySet();
		for (Iterator<Map.Entry<String, User>> it = set.iterator(); it
				.hasNext();) {
			Map.Entry<String, User> entry = (Map.Entry<String, User>) it.next();
//			System.out.println(entry.getKey() + "--->" + entry.getValue());
			if (entry.getValue().getUserId() == user.getUserId()
					&& entry.getValue().getPassword()
							.equals(user.getPassword())) {
				return true;
			}
		}
		return false;
	}
}
