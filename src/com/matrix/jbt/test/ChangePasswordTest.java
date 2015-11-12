package com.matrix.jbt.test;

import static org.junit.Assert.fail;

import java.util.Scanner;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.matrix.jbt.tool.GetURLUtil;
/**
 * 
 * @author JacksonLi
 * @date 2014/3/10
 */
public class ChangePasswordTest {
	Scanner input = new Scanner(System.in);

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void changePassword() {
		System.out.println("token:");
		String token = input.nextLine();
		System.out.println("userid:");
		String userid = input.nextLine();
		System.out.print("old password: ");
		String oldPwd = input.nextLine();
		System.out.print("new password: ");
		String newPwd = input.nextLine();

		if (token.trim().length() > 0 && userid.trim().length() > 0
				&& oldPwd.trim().length() > 0 && newPwd.trim().length() > 0) {
			String str = GetURLUtil.getService().path("/rest/User")
					.path(token).queryParam("userId", userid)
					.queryParam("oldPwd", oldPwd).queryParam("newPwd", newPwd)
					.accept(MediaType.TEXT_PLAIN).get(String.class);

			System.out.println("change password:" + str);
		}
	}
}
